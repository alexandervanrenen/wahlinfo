
, Sitzverteilung as (
	select b.partei_id, count(*) as sitze
	from BundestagMitglierder b
	group by b.partei_id
)

, KandidatenStimmen as (
	select k.partei_id, sum(s.stimmenAnzahl) as stimmenAnzahl
	from StimmenCountKandidat s, Kandidat k
	where s.kandidat_id = k.id
	group by k.partei_id
)

select p.name as partei_name, s.sitze as sitze, p.farbe as partei_farbe, p.kurzbezeichnung as partei_kurzbezeichnung, p.id as partei_id, c.stimmenAnzahl as zweitstimmen, k.stimmenanzahl as erststimmen
from Sitzverteilung s, Partei p, StimmenCountPartei c, KandidatenStimmen k
where s.partei_id = p.id
  and s.partei_id = c.partei_id
  and s.partei_id = k.partei_id
