select p.name as partei_name, s.sitze as sitze, p.farbe as partei_farbe, p.kurzbezeichnung as partei_kurzbezeichnung, p.id as partei_id
from SitzVerteilung s, Partei p
where s.partei_id=p.id;
