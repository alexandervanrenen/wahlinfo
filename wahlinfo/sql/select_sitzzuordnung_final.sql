---------------------------------------------------------------
-- Schritt 1 - Berechne die Sitzplatzverteilung im Bundestag --
---------------------------------------------------------------

-- Anzahl der Zweitstimmen pro Bundesland pro Partei
with AnzZweitstimmenBundesland as (
        select k.bundesland_id, s.partei_id, count(*) as Anzahl
        from stimme s, wahlkreis k
        where s.partei_id <> 0
        and s.wahlkreis_id = k.id
        group by k.bundesland_id, s.partei_id
)

-- Anteil der Zweitstimmen pro Partei in % (Gesamtdeutschland)
, AntZweitstimmen as (
        select z.partei_id, (sum(z.Anzahl)*100.0)/(select sum(z2.Anzahl) from AnzZweitstimmenBundesland z2) as Anteil
        from AnzZweitstimmenBundesland z
        group by z.partei_id
)

-- Anzahl der Stimmen pro Direktkandidat pro Wahlkreis
, DirektkandidatenAnzStimmen as (
        select s.kandidat_id, k.partei_id, s.wahlkreis_id, count(*) as Anzahl
        from stimme s, kandidat k
        where s.kandidat_id = k.id
        and s.kandidat_id <> 0
        group by s.kandidat_id, k.partei_id, s.wahlkreis_id
)

-- Filtert die View 'DirektkandidatenAnzStimmen' nach den Direktkandidaten, die in ihrem Wahlkreis die meisten Stimmen erhalten haben
-- Damit erhalten wir fuer jeden wahlkreis einen Gewinner
-- TODO: Beruecksichtigen, dass zwei Kandidaten auch gleich viele Stimmen erhalten haben koennen
, Direktmandate as (
        select *
        from DirektkandidatenAnzStimmen d
        where not exists (
                select * from DirektkandidatenAnzStimmen d2 where d2.Anzahl > d.Anzahl and d2.kandidat_id <> d.kandidat_id and d2.wahlkreis_id = d.wahlkreis_id)
)

-- Zaehlt die Anzahl der Direktmandate pro Bundesland pro Partei
, AnzDirektmandateBundeslandPartei as (
        select w.bundesland_id, d.partei_id, count(*) as Anzahl
        from Direktmandate d, wahlkreis w
        where d.wahlkreis_id = w.id
        group by w.bundesland_id, d.partei_id
)

-- Zaehlt die Anzahl der Direktmandate pro Partei
, AnzDirektmandatePartei as (
        select a.partei_id, sum(Anzahl) as Anzahl
        from AnzDirektmandateBundeslandPartei a
        group by a.partei_id
)

-- Findet die Parteien die in den Bundestag gelangen
-- Anteil der Zweitstimmen Partei unter Beruecksichtigung der Sperrklausel (mind. 5% oder drei Direktmandate)
, AntZweitstimmenSperrklausel as (
        select *
        from AntZweitstimmen a
        where a.Anteil >= 5
        or 3 <= (select ad.Anzahl from AnzDirektmandatePartei ad where ad.partei_id = a.partei_id)
)

-- Skaliere die %-Werte der Parteien, die in den bundestag kommen, auf 100% skalieren
, ProzentualeSitzverteilungImBundestag as (
        select a.partei_id, (select 1/(sum(b.Anteil)/100) from AntZweitstimmenSperrklausel b)*a.Anteil as Anteil
        from AntZweitstimmenSperrklausel a
)

-------------------------------------------------------
-- Schritt 2 - Verteile die Sitzplaetze nach d'Hondt --
-------------------------------------------------------

-- Anzahl der Direktmandate ohne Partei im Bundestag haben (schließt auch parteilose Kandidaten mit ein)
, AnzDirektmandateOhneBundestagspartei as (
        select count(*) as Anzahl
        from Direktmandate d
        where d.partei_id not in
                (select a.partei_id from ProzentualeSitzverteilungImBundestag a)
)

-- Verfahren nach d'Hondt: Proportionale Verteilung der Zweitstimmen auf die Sitze im Bundestag (ganze Sitze und Rest-Anteile)
-- Verteile die ganzzahligen Sitze sofort
-- Die restlichen n Sitzplaetze werden dann den Parteien mit den hoechsten Nachkommastellen zugewiesen
, PropVerteilungSitzeBundestag as (
        select partei_id, anteil, cast(anteil*(598-(select Anzahl from AnzDirektmandateOhneBundestagspartei)) as integer)/100 as sitze, (((anteil*598)%100)/100) as rest
        from ProzentualeSitzverteilungImBundestag
)

-- Berechne die Anzahl der noch zu vergebenden Sitze
, AnzZuVergebendeSitze as (
        select 598-sum(sitze) as Anzahl
        from PropVerteilungSitzeBundestag
)

-- Eine RowId hinzufuegen um die obersten n Rest anteile zu finden
, PropVerteilungSitzeBundestagRowId as (
        select partei_id, anteil, sitze, rest, ROW_NUMBER () OVER (ORDER BY rest DESC) AS rowNum
        from PropVerteilungSitzeBundestag
)
-- Verbleibende Sitze entsprechend der Rest-Anteile vergeben
, ParteienBeguenstigt as (        
        select v.partei_id as partei_id2
        from PropVerteilungSitzeBundestagRowId v
        where v.rowNum <= (select a.Anzahl from AnzZuVergebendeSitze a)
)

-- Endgueltige Sitzverteilung (beguenstigte Parteien erhalten einen zusaetzlichen Sitz)
-- Ordnet jeder Partei die Anzahl von Sitzen zu
, SitzVerteilung as (
        select v.partei_id, (case when b.partei_id2 is null then v.sitze else v.sitze+1 end) as sitze -- TODO: geht nicht, jede partei bekommt einen sitz dazu
        from PropVerteilungSitzeBundestag v
        left outer join ParteienBeguenstigt b on v.partei_id = b.partei_id2
)

-- check point =)
-- hier sind die sitze jeder partei auf die bundeslaender verteilt
-- select sum(sitze) from SitzVerteilung; -- == 601

/*
select * from PropVerteilungSitzeBundestag;
select * from ParteienBeguenstigt;
select * from ParteienBeguenstigt b right outer join PropVerteilungSitzeBundestag v on v.partei_id = b.partei_id2; -- does not contain any null values WTF
*/

---------------------------------------------------------------------
-- Schritt 3 - Verteile die Sitze pro Partei auf die Bundeslaender --
---------------------------------------------------------------------
-- Jede aus dem Bundesland hat nun eine Anzahl von Sitzen zugewiesen bekommen. (in SitzVerteilung)
-- Diese muessen nun fuer jede Partei auf die Listenkandidaten aus den Bundeslaendern gemappt werden.
-- Dazu wird in diesem Schritt fuer jede Partei (mit dem d'Hondt berechnet) welches Bundesland wieviele sitze bekommt.

-- Anteil der Stimmen pro Bundesland an den Gesamtstimmen einer Partei in %
-- Wie sind die Stimmen der Partei ueber die Bundeslaender verteilt
, AnteilZweitstimmenBundeslandPartei as (
        select a.partei_id, a.bundesland_id, a.anzahl, (a.anzahl*100.0)/(select sum(Anzahl) from AnzZweitstimmenBundesland b where a.partei_id = b.partei_id group by b.partei_id) as Anteil
        from AnzZweitstimmenBundesland a, SitzVerteilung b
        where a.partei_id = b.partei_id -- only use partys in the bundestag
)

-- Verfahren nach d'Hondt: Proportionale Verteilung der Zweitstimmen pro Bundesland auf die Sitze einer Partei
-- Ordne Sitzplaetze auf die Bundeslaender zu (ohne rest)
, PropVerteilungSitzeBundesland as (
        select a.partei_id, a.bundesland_id, a.anteil, cast(anteil*(select s.sitze from SitzVerteilung s where a.partei_id = s.partei_id) as integer)/100 as sitze, (((a.anteil*(select s.sitze from SitzVerteilung s where a.partei_id = s.partei_id))%100)/100) as rest
        from AnteilZweitstimmenBundeslandPartei a
)

-- Anzahl der noch zu vergebenden Sitze pro Partei.
-- Berechnet pro Bundesland pro Partei weiviele Sitze noch durch den rest offen sind.
, AnzahlZuVergebendeSitzePartei as (
        select p.partei_id, sum(p.sitze) as SitzeBereitsVergeben, (select s.sitze from SitzVerteilung s where p.partei_id = s.partei_id)-SitzeBereitsVergeben as SitzeZuVergeben
        from PropVerteilungSitzeBundesland p
        group by p.partei_id
)

-- Eine RowId hinzufuegen um den rest den obersten n Parteien geben zu koennen.
, PropVerteilungSitzeBundeslandMitRowid as (
        select *, ROW_NUMBER () OVER (ORDER BY partei_id, rest DESC) AS rowNum
        from PropVerteilungSitzeBundesland
)

-- Die row id war bis jetzt nur global, diese muss aber lokal fuer jede Partei berechnet werden.
, PropVerteilungSitzeBundeslandMitRowidLokal as (
        select p1.partei_id, p1.bundesland_id, p1.anteil, p1.sitze, p1.rest, p1.rowNum - min(p2.rowNum) + 1 as rowNum
        from PropVerteilungSitzeBundeslandMitRowid p1, PropVerteilungSitzeBundeslandMitRowid p2
        where p1.partei_id = p2.partei_id
        group by p1.partei_id, p1.bundesland_id, p1.anteil, p1.sitze, p1.rest, p1.rowNum
)

-- Berechne fuer jede Partei in welchen Bundeslaendern sie durch den rest noch einen Sitz bekommt.
-- Diese Tabelle enthaelt fuer Jede Partei die Bundelaender die noch einen Sitz bekommen.
, BeguenstigteBundeslandParteien as (
        select a.partei_id as partei_id, p.bundesland_id as bundesland_id
        from AnzahlZuVergebendeSitzePartei a, PropVerteilungSitzeBundeslandMitRowidLokal p
        where a.partei_id = p.partei_id
        and p.rowNum <= a.SitzeZuVergeben
)

-- Addiere die bonus sitze entsprechend hinzu.
, SitzeProBundeslandProPartei as (
        select p.partei_id, p.bundesland_id, (case when b.partei_id is null then p.sitze else p.sitze+1 end) as sitze
        from PropVerteilungSitzeBundesland p left outer join BeguenstigteBundeslandParteien b on p.partei_id=b.partei_id and p.bundesland_id=b.bundesland_id
)

-----------------------------------------------
-- Schritt 4 - Bestimme die Listenkandidaten --
-----------------------------------------------
-- Die Tabelle SitzeProBundeslandProPartei enthaelt wieviele Listenkandidaten jede Partei in jedem Bundsland auswaehlen darf.
-- Mit dieser Information koennen nun die entsprechenden Listenkandidaten bestimmt werden.

-- Rechnet die anzahl an sitzen pro Bundesland pro Partei aus, die an Kandidaten vergeben werden koennen
-- left outer join da es parteien gibt die nicht in jedem bundesland einen kandidaten haben
-- liefert zu JEDEM bundesland fuer JEDE partei die anzahl der direkt mandate
, FreieSitzeProBundeslandProPartei as (
        select p.partei_id, p.bundesland_id, (case when (p.sitze - a.anzahl) is null then p.sitze when p.sitze - a.anzahl < 0 then 0 else p.sitze - a.anzahl end) as freieSitze
        from SitzeProBundeslandProPartei p left outer join AnzDirektmandateBundeslandPartei a on p.partei_id = a.partei_id and p.bundesland_id = a.bundesland_id
)

-- check point =)
-- anzahl der sitze die auf listenkandidaten verteilt werden
-- select sum(freieSitze) from FreieSitzeProBundeslandProPartei; -- == 327

-- Landeslisten mit den Kandidaten bereinigen, die bereits ein Direktmandat haben
-- Dannach enthaelt LandeslistenBereinigt genau die Kandidaten die man verteilen darf
, LandeslistenBereinigt as (
        select *
        from landesliste l
        where l.kandidat_id not in
                (select d.kandidat_id from Direktmandate d)
)

-- Fuege eine row id hinzu, da wieder die besten n gezaehlt werden muessen und bereinige ungueltige Eintraege
, LandeslistenBereinigtRowId as (
        select l.bundesland_id, k.id as kandidat_id, k.partei_id, ROW_NUMBER () OVER (ORDER BY l.bundesland_id, k.partei_id, k.listenRang DESC) AS rowNum
        from LandeslistenBereinigt l, kandidat k
        where l.kandidat_id = k.id
        and k.partei_id <> 0 -- no candidates without a party
        and k.listenrang <> 0 -- dont know what that means but its invalid ..
)

-- Wandle Globale row ids in lokale row ids um
, LandeslistenBereinigtRowIdLokal as (
        select l1.bundesland_id, l1.partei_id, l1.kandidat_id, l1.rowNum - min(l2.rowNum) + 1 as rowNum
        from LandeslistenBereinigtRowId l1, LandeslistenBereinigtRowId l2
        where l1.partei_id = l2.partei_id and l1.bundesland_id = l2.bundesland_id
        group by l1.bundesland_id, l1.partei_id, l1.kandidat_id, l1.rowNum
)

-- Waehle in jedem Bundesland fuer jede partei die ersten n kandidaten aus
-- Wobei n die anzahl an verfuegbaren sitzen in dem bundesland fuer die partei (die bereits erhaltenen direktmandate sind in FreieSitzeProBundeslandProPartei abgezogen worden)
, Listenmandate as (
        select l.partei_id, l.bundesland_id, l.kandidat_id
        from LandeslistenBereinigtRowIdLokal l
        where 1 <= l.rowNum and l.rowNum <= (select f.freieSitze from FreieSitzeProBundeslandProPartei f where f.partei_id=l.partei_id and f.bundesland_id=l.bundesland_id)
)

-- check point =)
-- select count(*) from Listenmandate;-- == 321 weil 6 leute verlohren werden (siehe UNTEN)

-- enthaelt nun alle kandidaten die in den bundstag durch eine liste einziehen duerfen. vereinigt man das mit den direktmandaten dann hat man alle menschen im bundestag.
select * from Listenmandate;

/* UNTEN: es scheint bundeslaender zu geben inden eine partei mehr verfuegbare sitze hat als sie kandidaten stellt
      partei 13, bundesland 14 mit 4 verlohrenen sitzen
      partei 4,  bundesland 2  mit 2 verlohrenen sitzen

anfrage dafuer:
select f.partei_id, f.bundesland_id, f.freieSitze, count(*) as verfuegbareKandidaten
from FreieSitzeProBundeslandProPartei f left outer join Listenmandate l on f.partei_id=l.partei_id and f.bundesland_id=l.bundesland_id
group by f.partei_id, f.bundesland_id, f.freieSitze
having count(*) < freieSitze;
*/
