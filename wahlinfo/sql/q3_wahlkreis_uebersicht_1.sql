
select k.name as kandidatenName, k.vorname as kandidatenVorname, k.id as kandidatenId
from Direktmandate d, Kandidat k
where d.wahlkreis_id = ?
  and d.kandidat_id = k.id;
