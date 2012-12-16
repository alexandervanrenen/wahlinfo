
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

select p1.name as siegerparteierststimme, p2.name as siegerparteizweitstimme, w.name as wahlkreisname
from Wahlkreis w, Direktmandate d, Partei p1, gewinnerInJedemWahlkreis g, Partei p2
where w.id = d.wahlkreis_id
  and w.id = g.wahlkreis_id
  and d.partei_id = p1.id
  and g.partei_id = p2.id;
