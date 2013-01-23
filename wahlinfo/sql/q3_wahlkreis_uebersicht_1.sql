
select 	k.name as kandidat_name, k.vorname as kandidat_vorname, k.id as kandidat_id,
		p.id as partei_id, p.name as partei_name, p.kurzbezeichnung as partei_kurzbezeichnung, p.farbe as partei_farbe
from Direktmandate d, Kandidat k, Partei p
where d.wahlkreis_id = ?
  and d.kandidat_id = k.id
  and d.partei_id = p.id;
