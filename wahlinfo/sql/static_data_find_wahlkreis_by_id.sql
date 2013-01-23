select w.*, w.id as wahlkreis_id, w.name as wahlkreis_name, b.name as bundesland_name
from wahlkreis w, bundesland b
where w.bundesland_id = b.id and
      w.id = ?