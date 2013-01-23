
-- Abstand von jedem kandidaten zu dem 2. gewinner in seinem wahlkreis
, AbstandZumZweitenProKandidat as (
		select d.kandidat_id, d.partei_id, d.stimmenAnzahl - max(s.stimmenAnzahl) as stimmenVorsprung
		from Direktmandate d, StimmenCountKandidat s
		where d.wahlkreis_id = s.wahlkreis_id
		  and d.kandidat_id <> s.kandidat_id
		  and d.partei_id <> 0
		group by d.kandidat_id, d.stimmenAnzahl, d.partei_id
)

-- Sortiere (mit hilfe von row id) nach dem partei und innerhalb nach abstand
-- Damit erhalten wir eine durchnummerierte tabelle
, AbstandZumSiegerProKandidatSortiert as (
		select *, ROW_NUMBER () OVER (ORDER BY partei_id, stimmenVorsprung asc) as platzierung
		from AbstandZumZweitenProKandidat
)

-- Finde für jede partei die minimale nummer
, DirektmandateVorsprungMinimalePlatzierung as (
		select d1.partei_id, min(platzierung) as minimalePlatzierung
		from AbstandZumSiegerProKandidatSortiert d1
		group by d1.partei_id
)

-- Die knappsten 10 sieger sind die deren nummerierung einen kleineren abstand als 10 zum besten ihrer partei hat
-- Damit sind die gewinner abgeschlossen
, KnappsteGewinnerTop10 as (
		select d2.kandidat_id, d2.partei_id, d2.platzierung - d1.minimalePlatzierung + 1 as platzierung, d2.stimmenVorsprung, true as istGewinner
		from DirektmandateVorsprungMinimalePlatzierung d1, AbstandZumSiegerProKandidatSortiert d2
		where d1.partei_id = d2.partei_id
		  and d2.platzierung - d1.minimalePlatzierung < 10
)

-- Abstand von jedem kandidaten zu dem gewinner in seines wahlkreises
, AbstandZumGewinner as (
		select s.kandidat_id, s.partei_id, s.wahlkreis_id, max(d.stimmenAnzahl) - s.stimmenAnzahl as stimmenVorsprung
		from Direktmandate d, StimmenCountKandidat s
		where d.wahlkreis_id = s.wahlkreis_id
		group by s.kandidat_id, s.wahlkreis_id, s.stimmenAnzahl, s.partei_id
)

-- Beschränke auf die kandidaten die zu parteien gehören die nicht in den gewinnern sind
-- Enthält eine liste mit allen kandidaten und deren abstand zum gewinner ihres wahlkreises
, AbstandZumGewinnerNurVerliererParteien as (
		select *
		from AbstandZumGewinner a
		where a.partei_id not in (	select d.partei_id
									from KnappsteGewinnerTop10 d )
		  and a.partei_id <> 0
)

-- 
, AbstandZumGewinnerNurVerliererParteienSortiert as (
		select *, row_number () over ( order by a.partei_id, a.stimmenVorsprung ) as platzierung
		from AbstandZumGewinnerNurVerliererParteien a
)

-- 
, AbstandZumGewinnerNurVerliererParteienMinimalePlatzierung as (
		select a.partei_id, min(a.platzierung) as minPlatzierungDerPartei
		from AbstandZumGewinnerNurVerliererParteienSortiert a
		group by a.partei_id
)

, KnappsteVerliererTop10 as (
		select a.kandidat_id, a.partei_id, a.platzierung - bc.minPlatzierungDerPartei + 1 as platzierung, a.stimmenVorsprung, false as istGewinner
		from AbstandZumGewinnerNurVerliererParteienSortiert a, AbstandZumGewinnerNurVerliererParteienMinimalePlatzierung bc
		where a.partei_id = bc.partei_id
		  and a.platzierung - minPlatzierungDerPartei < 10
)

, SiegerUndVerliererVereinigt as (
		select *
		from (	select * from KnappsteVerliererTop10 union all
				select * from KnappsteGewinnerTop10 ) as r
)

select k.id as kandidat_id, k.name as kandidat_name, k.vorname as kandidat_vorname, k.wahlkreis_id,
       p.id as partei_id, p.name as partei_name, p.kurzbezeichnung as partei_kurzbezeichnung, p.farbe as partei_farbe,
	   s.platzierung, s.stimmenVorsprung, w.name as wahlkreis_name, s.istGewinner
from SiegerUndVerliererVereinigt s, Partei p, Kandidat k, Wahlkreis w
where s.kandidat_id = k.id
  and s.partei_id = p.id
  and k.wahlkreis_id = w.id
order by partei_id, platzierung;
