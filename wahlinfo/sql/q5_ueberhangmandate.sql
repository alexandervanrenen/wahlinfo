
, ueberhangMandate as (
   select p.partei_id, p.bundesland_id, (case when (p.sitzeBundesland - a.direktmandateAnzahl) is null then 0 when a.direktmandateAnzahl - p.sitzeBundesland < 0 then 0 else a.direktmandateAnzahl - p.sitzeBundesland end) as ueberhangMandate
   from SitzeProBundeslandProPartei p left outer join AnzDirektmandateBundeslandPartei a
   on p.partei_id = a.partei_id and p.bundesland_id = a.bundesland_id
)

select	p.id as partei_id, p.name as partei_name, p.farbe as partei_farbe, p.kurzbezeichnung as partei_kurzbezeichnung,
		b.id as bundesland_id, b.name as bundesland_name,
		u.ueberhangMandate as ueberhangmandate
from ueberhangMandate u, Partei p, Bundesland b
where u.partei_id = p.id
  and u.bundesland_id = b.id
  and u.ueberhangMandate <> 0;
