
select	k.id as kandidat_id, k.name as kandidat_name, k.vorname as kandidat_vorname, k.wahlkreis_id,
		p.id as partei_id, p.name as partei_name, p.kurzbezeichnung as partei_kurzbezeichnung, p.farbe as partei_farbe,
		w.name as wahlkreis_name
from BundestagMitglierder b, Kandidat k, Wahlkreis w, Partei p
where b.kandidat_id = k.id
  and b.partei_id = p.id
  and k.wahlkreis_id = w.id;
