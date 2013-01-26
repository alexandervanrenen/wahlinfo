
, Sitzverteilung as (
	select b.partei_id, count(*) as sitze
	from BundestagMitglierder b
	group by b.partei_id
)

select p.name as partei_name, s.sitze as sitze, p.farbe as partei_farbe, p.kurzbezeichnung as partei_kurzbezeichnung, p.id as partei_id
from Sitzverteilung s, Partei p
where s.partei_id = p.id;
