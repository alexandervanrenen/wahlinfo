select	k.id as kandidat_id, k.name as kandidat_name, k.vorname as kandidat_vorname, k.geburtsjahr as kandidat_geburtsjahr,
		p.id as partei_id, p.name as partei_name, p.kurzbezeichnung as partei_kurzbezeichnung, p.farbe as partei_farbe,
		w.id as wahlkreis_id, w.name as wahlkreis_name
from Kandidat k, Partei p, Wahlkreis w
where k.partei_id = p.id
  and k.wahlkreis_id = w.id;