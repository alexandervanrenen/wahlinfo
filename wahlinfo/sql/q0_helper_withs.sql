
--  ___ ___ ___ ___ ___ ___ ___ ___ ___ ___ ___
--  \./ \./ \./ \./ \./ \./ \./ \./ \./ \./ \./
--   |   |   |   |   Schritt 0   |   |   |   |
-- _/ \_/ \_/ \_/ \_/ \_/ \_/ \_/ \_/ \_/ \_/ \_
-- 
-- Berechne Aggregate für parteien und kandidaten
--

-- Berechne die Stimmen Anzahl pro Kandidat
with StimmenCountKandidatHand as (
	 select s.kandidat_id, count(*) as stimmenAnzahl
	 from stimme s
	 group by s.kandidat_id
	 having s.kandidat_id <> 0
)

-- Use aggregiert oder hand
, StimmenCountKandidat as (
--	select * from StimmenCountKandidatHand
	select * from StimmenCountKandidatAggregiert
)

-- Berechne die Stimmen Anzahl pro Partei pro Wahlkreis
, StimmenCountParteiWahlkreisHand as (
	 select s.partei_id, s.wahlkreis_id, count(*) as stimmenAnzahl
	 from stimme s
	 group by s.partei_id, s.wahlkreis_id
	 having s.partei_id <> 0
)

-- Berechne die Stimmen Anzahl pro Partei pro Bundesland
, StimmenCountParteiBundeslandHand as (
	 select s.partei_id, w.bundesland_id, sum(s.stimmenAnzahl) as stimmenAnzahl
	 from StimmenCountParteiWahlkreisHand s, Wahlkreis w
	 where s.wahlkreis_id = w.id
	 group by s.partei_id, w.bundesland_id
 )

-- Use aggregiert oder hand
, StimmenCountParteiWahlkreis as (
--	select * from StimmenCountParteiWahlkreisHand
	select * from StimmenCountParteiWahlkreisAggregiert
)

-- Use aggregiert oder hand
, StimmenCountParteiBundesland as (
--	select * from StimmenCountParteiBundeslandHand
	select * from StimmenCountParteiBundeslandAggregiert
)

-- Berechne die Stimmen Anzahl pro Partei
, StimmenCountPartei as (
	select s.partei_id, sum(stimmenAnzahl) as stimmenAnzahl
	from StimmenCountParteiBundesland s
	group by s.partei_id
)

--  ___ ___ ___ ___ ___ ___ ___ ___ ___ ___ ___
--  \./ \./ \./ \./ \./ \./ \./ \./ \./ \./ \./
--   |   |   |   |   Schritt 1   |   |   |   |
-- _/ \_/ \_/ \_/ \_/ \_/ \_/ \_/ \_/ \_/ \_/ \_
-- 
-- Berechne DirektMandate
--

, MaxStimmenCountProWahlkreis as (
	select k.wahlkreis_id, max(s.stimmenAnzahl) as stimmenAnzahl
	from StimmenCountKandidat s, Kandidat k
	where s.kandidat_id =  k.id
	group by k.wahlkreis_id
)

-- Berechne die gewinner in jedem wahlkreis
-- PBP: schließt nicht aus, dass es nur einen kandidaten pro wahlkreis gibt
, Direktmandate as (
	select k.id as kandidat_id, k.partei_id, k.wahlkreis_id, m.stimmenAnzahl
	from MaxStimmenCountProWahlkreis m, StimmenCountKandidat s, Kandidat k
	where m.wahlkreis_id = k.wahlkreis_id
	  and m.stimmenAnzahl = s.stimmenAnzahl
	  and k.id = s.kandidat_id
)

--  ___ ___ ___ ___ ___ ___ ___ ___ ___ ___ ___
--  \./ \./ \./ \./ \./ \./ \./ \./ \./ \./ \./
--   |   |   |   |   Schritt 2   |   |   |   |
-- _/ \_/ \_/ \_/ \_/ \_/ \_/ \_/ \_/ \_/ \_/ \_
-- 
-- Berechne Erhaltene Sitmmen der Parteien in Porzent
-- 

-- Anteil der Zweitstimmen pro Partei in % (Gesamtdeutschland)
, AnteilZweitStimmen as (
	 select z.partei_id, (z.stimmenAnzahl*100.0)/(select sum(z2.stimmenAnzahl) from StimmenCountPartei z2) as ProzentualerAnteil
	 from StimmenCountPartei z
)

-- Findet die Parteien die in den Bundestag gelangen
-- Anteil der Zweitstimmen Partei unter Beruecksichtigung der Sperrklausel (mind. 5% oder drei Direktmandate)
, AnteilZweitStimmenSperrklausel as (
	select *
	from AnteilZweitStimmen a
	where a.ProzentualerAnteil >= 5
	or 3 <= (select count(*) from Direktmandate d where d.partei_id = a.partei_id)
)

-- Skaliere die %-Werte der Parteien, die in den bundestag kommen, auf 100% skalieren
, ProzentualeSitzverteilungImBundestag as (
	select a.partei_id, (select 1/(sum(b.ProzentualerAnteil)/100) from AnteilZweitStimmenSperrklausel b)*a.ProzentualerAnteil as ProzentualerAnteil
	from AnteilZweitStimmenSperrklausel a
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
        select partei_id, prozentualerAnteil, cast(prozentualerAnteil*(598-(select Anzahl from AnzDirektmandateOhneBundestagspartei)) as integer)/100 as sitze, (((prozentualerAnteil*598)%100)/100) as rest
        from ProzentualeSitzverteilungImBundestag
)

-- Berechne die Anzahl der noch zu vergebenden Sitze
, AnzZuVergebendeSitze as (
        select 598-sum(sitze) as Anzahl
        from PropVerteilungSitzeBundestag
)

-- Eine RowId hinzufuegen um die obersten n Rest anteile zu finden
, PropVerteilungSitzeBundestagRowId as (
        select partei_id, prozentualerAnteil as anteil, sitze, rest, ROW_NUMBER () OVER (ORDER BY rest DESC) AS rowNum
        from PropVerteilungSitzeBundestag
)
-- Verbleibende Sitze entsprechend der Rest-Anteile vergeben
, ParteienBeguenstigt as (
        select v.partei_id as partei_id
        from PropVerteilungSitzeBundestagRowId v
        where v.rowNum <= (select a.Anzahl from AnzZuVergebendeSitze a)
)

-- Endgueltige SitzVerteilungOhneUeberhang (beguenstigte Parteien erhalten einen zusaetzlichen Sitz)
-- Ordnet jeder Partei die Anzahl von Sitzen zu
, SitzVerteilungOhneUeberhang as (
        select v.partei_id, (case when (select count(*) from ParteienBeguenstigt p where v.partei_id = p.partei_id) = 1 then v.sitze +1  else v.sitze end) as sitze
        from PropVerteilungSitzeBundestag v
)

-- check point =)
-- hier sind die sitze jeder partei auf die bundeslaender verteilt
-- select sum(sitze) from SitzVerteilungOhneUeberhang; -- == 598

/*
select * from PropVerteilungSitzeBundestag;
select * from ParteienBeguenstigt;
select * from ParteienBeguenstigt b right outer join PropVerteilungSitzeBundestag v on v.partei_id = b.partei_id2; -- does not contain any null values WTF
*/

---------------------------------------------------------------------
-- Schritt 3 - Verteile die Sitze pro Partei auf die Bundeslaender --
---------------------------------------------------------------------
-- Jede partei hat nun eine Anzahl von Sitzen zugewiesen bekommen. (in SitzVerteilungOhneUeberhang)
-- Diese muessen nun fuer jede Partei auf die Listenkandidaten aus den Bundeslaendern gemappt werden.
-- Dazu wird in diesem Schritt fuer jede Partei (mit dem d'Hondt) berechnet welches Bundesland wieviele sitze bekommt.

-- Anteil der Stimmen pro Bundesland an den Gesamtstimmen einer Partei in %
-- Wie sind die Stimmen der Partei ueber die Bundeslaender verteilt
, StimmenAnteilParteiBundesland as (
        select s.partei_id, s.bundesland_id, s.stimmenAnzahl, b.sitze as gesammtSitze,
		      (s.stimmenAnzahl*100.0)/(select sum(s2.stimmenAnzahl) from StimmenCountPartei s2 where s2.partei_id = s.partei_id)as stimmenAnteil
        from StimmenCountParteiBundesland s, SitzVerteilungOhneUeberhang b
        where s.partei_id = b.partei_id -- only use partys in the bundestag
)

-- Verfahren nach d'Hondt: Proportionale Verteilung der Zweitstimmen pro Bundesland auf die Sitze einer Partei
-- Ordne Sitzplaetze auf die Bundeslaender zu (ohne rest)
, PropVerteilungSitzeBundesland as (
        select a.partei_id, a.bundesland_id, a.stimmenAnteil, a.gesammtSitze,
		       cast(a.stimmenAnteil * a.gesammtSitze as integer)/100 as sitzeBundesland,
			   (((a.stimmenAnteil * a.gesammtSitze)%100)/100) as rest
        from StimmenAnteilParteiBundesland a
)

-- Anzahl der noch zu vergebenden Sitze pro Partei.
-- Berechnet pro Bundesland pro Partei weiviele Sitze noch durch den rest offen sind.
, AnzahlZuVergebendeSitzePartei as (
        select p.partei_id, sum(p.sitzeBundesland) as SitzeBereitsVergeben, p.gesammtSitze as gesammtSitze
        from PropVerteilungSitzeBundesland p
        group by p.partei_id, p.gesammtSitze
)

-- For some reason i cant integrate this in the query above
, AnzahlZuVergebendeSitzeParteiHelper as (
	select *, gesammtSitze - SitzeBereitsVergeben as verbleibend
	from AnzahlZuVergebendeSitzePartei
)


-- Eine RowId hinzufuegen um den rest den obersten n Parteien geben zu koennen.
, PropVerteilungSitzeBundeslandMitRowid as (
        select *, ROW_NUMBER () OVER (ORDER BY partei_id, rest DESC) AS rowNum
        from PropVerteilungSitzeBundesland
)

-- Die row id war bis jetzt nur global, diese muss aber lokal fuer jede Partei berechnet werden.
, PropVerteilungSitzeBundeslandMitRowidLokal as (
        select p1.partei_id, p1.bundesland_id, p1.rowNum - min(p2.rowNum) + 1 as rowNum
        from PropVerteilungSitzeBundeslandMitRowid p1, PropVerteilungSitzeBundeslandMitRowid p2
        where p1.partei_id = p2.partei_id
        group by p1.partei_id, p1.bundesland_id, p1.rowNum
)

-- Berechne fuer jede Partei in welchen Bundeslaendern sie durch den rest noch einen Sitz bekommt.
-- Diese Tabelle enthaelt fuer Jede Partei die Bundelaender die noch einen Sitz bekommen.
, BeguenstigteBundeslandParteien as (
        select a.partei_id as partei_id, p.bundesland_id as bundesland_id
        from AnzahlZuVergebendeSitzeParteiHelper a, PropVerteilungSitzeBundeslandMitRowidLokal p
        where a.partei_id = p.partei_id
        and p.rowNum <= (a.verbleibend)
)

-- Addiere die bonus sitze entsprechend hinzu.
, SitzeProBundeslandProPartei as (
        select p.partei_id, p.bundesland_id, (case when (select count(*) from BeguenstigteBundeslandParteien b where p.partei_id=b.partei_id and p.bundesland_id=b.bundesland_id) = 1 then p.sitzeBundesland+1 else p.sitzeBundesland end) as sitzeBundesland
        from PropVerteilungSitzeBundesland p
)

-----------------------------------------------
-- Schritt 4 - Bestimme die Listenkandidaten --
-----------------------------------------------
-- Die Tabelle SitzeProBundeslandProPartei enthaelt wieviele Listenkandidaten jede Partei in jedem Bundsland auswaehlen darf.
-- Mit dieser Information koennen nun die entsprechenden Listenkandidaten bestimmt werden.

-- Zaehlt die Anzahl der Direktmandate pro Bundesland pro Partei
, AnzDirektmandateBundeslandPartei as (
        select w.bundesland_id, d.partei_id, count(*) as direktmandateAnzahl
        from Direktmandate d, wahlkreis w
        where d.wahlkreis_id = w.id
        group by w.bundesland_id, d.partei_id
)

-- Rechnet die anzahl an sitzen pro Bundesland pro Partei aus, die an Kandidaten vergeben werden koennen
-- left outer join da es parteien gibt die nicht in jedem bundesland einen kandidaten haben
-- liefert zu JEDEM bundesland fuer JEDE partei die anzahl der direkt mandate
, FreieSitzeProBundeslandProPartei as (
        select p.partei_id, p.bundesland_id, (case when (p.sitzeBundesland - a.direktmandateAnzahl) is null then p.sitzeBundesland when p.sitzeBundesland - a.direktmandateanzahl < 0 then 0 else p.sitzeBundesland - a.direktmandateanzahl end) as freieSitze
        from SitzeProBundeslandProPartei p left outer join AnzDirektmandateBundeslandPartei a
		  on p.partei_id = a.partei_id and p.bundesland_id = a.bundesland_id
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
        where 1 <= l.rowNum and l.rowNum <= (select min(f.freieSitze) from FreieSitzeProBundeslandProPartei f where f.partei_id=l.partei_id and f.bundesland_id=l.bundesland_id)
)

, BundestagMitglierder as (
	select *
	from ((select kandidat_id, partei_id from Listenmandate) union all (select kandidat_id, partei_id from Direktmandate)) as tab
)

-- check point =)
-- select count(*) from Listenmandate;-- == 321 weil 6 leute verlohren werden (siehe UNTEN)

-- enthaelt nun alle kandidaten die in den bundstag durch eine liste einziehen duerfen. vereinigt man das mit den direktmandaten dann hat man alle menschen im bundestag.
-- select * from Listenmandate;

/* UNTEN: es scheint bundeslaender zu geben inden eine partei mehr verfuegbare sitze hat als sie kandidaten stellt
      partei 13, bundesland 14 mit 4 verlohrenen sitzen
      partei 4,  bundesland 2  mit 2 verlohrenen sitzen

anfrage dafuer:
select f.partei_id, f.bundesland_id, f.freieSitze, count(*) as verfuegbareKandidaten
from FreieSitzeProBundeslandProPartei f left outer join Listenmandate l on f.partei_id=l.partei_id and f.bundesland_id=l.bundesland_id
group by f.partei_id, f.bundesland_id, f.freieSitze
having count(*) < freieSitze;
*/
