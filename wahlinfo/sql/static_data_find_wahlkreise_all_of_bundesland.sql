select w.*, b.name as bundesland_name
from wahlkreis w, bundesland b
where w.bundesland_id = b.id
  and b.id = ?
  and b.id <> 0;