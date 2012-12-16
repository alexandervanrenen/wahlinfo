
select (w.waehler*1.0)/w.wahlberechtigte as wahlbeteiligung, (w.waehler2005*1.0)/w.wahlberechtigte2005 as wahlbeteiligungVorjahr
from Wahlkreis w
where w.id = ?;
