
select k.id
from Wahlkreis w, Kandidat k
where w.id = ?
  and k.id = ?
  and k.wahlkreis_id = w.id
