select sum(w.wahlberechtigte) as wahlberechtigte, sum(w.wahlberechtigte2005) as wahlberechtigte2005
     , sum(w.waehler) as waehler, sum(w.waehler2005) as waehler2005
from wahlkreis w
