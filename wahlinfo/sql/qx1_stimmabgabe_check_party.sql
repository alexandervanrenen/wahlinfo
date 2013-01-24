
-- Get all parties in the bundesland of this wahlkreis
with GelisteteParteien as (
   select distinct k.partei_id
   from LandesListe l, Wahlkreis w, Kandidat k
   where w.id = ?
	 and l.bundesland_id = w.bundesland_id
	 and l.kandidat_id = k.id
)

select *
from GelisteteParteien
where partei_id = ?;