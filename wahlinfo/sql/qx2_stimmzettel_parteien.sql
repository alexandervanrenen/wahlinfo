
-- Get all parties in the bundesland of this wahlkreis
with GelisteteParteien as (
   select distinct k.partei_id
   from LandesListe l, Wahlkreis w, Kandidat k
   where w.id = ?
	 and l.bundesland_id = w.bundesland_id
	 and l.kandidat_id = k.id
)

-- Get the votes of these parties
, GelisteteParteienVotes as (
	select p.id as partei_id, p.name as partei_name, p.farbe as partei_farbe, p.kurzbezeichnung as partei_kurzbezeichnung, sum(p.stimmenaggregiert) as anzahl
	from Partei2005 p, GelisteteParteien gp
	where p.id = gp.partei_id
	  and p.id <> 0
	group by p.id, p.name, p.farbe, p.kurzbezeichnung
)

, VereinigtMitKandidaten as (
		select k.id as kandidat_id, k.name as kandidat_name, k.vorname as kandidat_vorname, p.*
		from Kandidat k full outer join GelisteteParteienVotes p on k.partei_id = p.partei_id
		where k.wahlkreis_id = ?
)

select *
from VereinigtMitKandidaten vmk
order by vmk.anzahl desc, vmk.partei_name;
