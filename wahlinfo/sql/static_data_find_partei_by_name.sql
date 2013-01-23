select p.id as partei_id, p.name as partei_name, p.kurzbezeichnung as partei_kurzbezeichnung, p.farbe as partei_farbe
from Partei p
where p.name like ? or p.kurzbezeichnung like ?;