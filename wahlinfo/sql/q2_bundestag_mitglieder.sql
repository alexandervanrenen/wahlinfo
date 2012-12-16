
, addingWahlkreise as (select l.partei_id, l.kandidat_id, k.wahlkreis_id from Listenmandate l, Kandidat k where l.kandidat_id = k.id)

, alleKandidaten as (select l.partei_id, l.kandidat_id, l.wahlkreis_id from addingWahlkreise l union select d.partei_id, d.kandidat_id, d.wahlkreis_id from Direktmandate d)

select k.name as name, k.vorname as vorname, p.name as partei, w.name as wahlkreis
from alleKandidaten a, Kandidat k, Wahlkreis w, Partei p
where a.kandidat_id = k.id
  and a.partei_id = p.id
  and a.wahlkreis_id = w.id;
