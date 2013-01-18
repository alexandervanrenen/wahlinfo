select b.id, b.name, sum(w.wahlberechtigte) as wahlberechtigte, sum(w.wahlberechtigte2005) as wahlberechtigte2005, sum(w.waehler) as waehler, sum(w.waehler2005) as waehler2005
from bundesland b, wahlkreis w
where b.id = w.bundesland_id
  and b.id = ?
group by b.id, b.name