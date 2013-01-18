
select k.name, k.vorname, p.name as partei, p.kurzbezeichnung
from Kandidat k, Partei p
where k.wahlkreis_id = ?
  and k.partei_id = p.id
