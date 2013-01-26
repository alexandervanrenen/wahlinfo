
, maxProWahlkreis as (
   select z.wahlkreis_id, max(z.stimmenanzahl) as maxAnzahl
   from StimmenCountParteiWahlkreis z
   group by z.wahlkreis_id
)

, gewinnerInJedemWahlkreis as (
   select z.partei_id, z.wahlkreis_id
   from StimmenCountParteiWahlkreis z, maxProWahlkreis m
   where z.wahlkreis_id = m.wahlkreis_id
     and z.stimmenanzahl = m.maxAnzahl
)

, gewinnerInJedemWahlkreisMitPartei as (
	select d.partei_id as partei_id1, g.partei_id as partei_id2, d.wahlkreis_id, w.name as wahlkreis_name
	from Direktmandate d, gewinnerInJedemWahlkreis g, Wahlkreis w
	where d.wahlkreis_id = g.wahlkreis_id
	  and w.id = d.wahlkreis_id
)

select * from gewinnerInJedemWahlkreisMitPartei;
