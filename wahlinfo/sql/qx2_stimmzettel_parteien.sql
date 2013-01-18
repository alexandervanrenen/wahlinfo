
-- Get all parties in the bundesland of this wahlkreis
, GelisteteParteien as (
   select distinct k.partei_id
   from LandesListe l, Wahlkreis w, Kandidat k
   where w.id = ?
	 and l.bundesland_id = w.bundesland_id
	 and l.kandidat_id = k.id
)

-- Get the votes of these parties
, GelisteteParteienVotes as (
	select p.id as partei_id, p.name, p.farbe, p.kurzbezeichnung, sum(p.stimmenaggregiert) as anzahl
	from Partei2005 p, GelisteteParteien gp
	where p.id = gp.partei_id
	  and p.id <> 0
	group by p.id, p.name, p.farbe, p.kurzbezeichnung
)

select *
from GelisteteParteienVotes gpv
order by gpv.anzahl desc, gpv.name 
