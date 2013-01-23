
, zweitstimmenProParteiProWahlkreis as (
   select s.partei_id, s.wahlkreis_id, count(*) as anzahl
   from stimme s
   group by s.partei_id, s.wahlkreis_id
)

-- when two parties have the same number of votes this will chose one (by random (more or less))
, maxProWahlkreis as (
   select z.wahlkreis_id, max(z.anzahl) as maxAnzahl
   from zweitstimmenProParteiProWahlkreis z
   group by z.wahlkreis_id
)

, gewinnerInJedemWahlkreis as (
   select z.partei_id, z.wahlkreis_id
   from zweitstimmenProParteiProWahlkreis z, maxProWahlkreis m
   where z.wahlkreis_id = m.wahlkreis_id
     and z.anzahl = m.maxAnzahl
)

select 	p1.id as partei_id1, p1.name as partei_name1, p1.kurzbezeichnung as partei_kurzbezeichnung1, p1.farbe as partei_farbe1,
		p2.id as partei_id2, p2.name as partei_name2, p2.kurzbezeichnung as partei_kurzbezeichnung2, p2.farbe as partei_farbe2,
		w.id as wahlkreis_id, w.name as wahlkreis_name
from Wahlkreis w, Direktmandate d, Partei p1, gewinnerInJedemWahlkreis g, Partei p2
where w.id = d.wahlkreis_id
  and w.id = g.wahlkreis_id
  and d.partei_id = p1.id
  and g.partei_id = p2.id;
