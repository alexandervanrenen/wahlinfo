
, addingWahlkreise as (
		select l.partei_id, l.kandidat_id, k.wahlkreis_id
		from Listenmandate l, Kandidat k
		where l.kandidat_id = k.id
)

, alleKandidaten as (
		select l.partei_id, l.kandidat_id, l.wahlkreis_id
		from addingWahlkreise l union select d.partei_id, d.kandidat_id, d.wahlkreis_id from Direktmandate d
)

select	k.id as kandidat_id, k.name as kandidat_name, k.vorname as kandidat_vorname, k.wahlkreis_id,
		p.id as partei_id, p.name as partei_name, p.kurzbezeichnung as partei_kurzbezeichnung, p.farbe as partei_farbe,
		w.name as wahlkreis_name
from alleKandidaten a, Kandidat k, Wahlkreis w, Partei p
where a.kandidat_id = k.id
  and a.partei_id = p.id
  and a.wahlkreis_id = w.id;
