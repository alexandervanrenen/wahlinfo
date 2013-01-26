
insert into StimmenCountKandidatAggregiert
	select s.kandidat_id, count(*) as stimmenAnzahl
    from stimme s
    group by s.kandidat_id
	having kandidat_id <> 0
;

insert into StimmenCountParteiWahlkreisAggregiert
	 select s.partei_id, s.wahlkreis_id, count(*) as stimmenAnzahl
	 from stimme s
	 group by s.partei_id, s.wahlkreis_id
	 having s.partei_id <> 0
;

insert into StimmenCountParteiBundeslandAggregiert
	 select s.partei_id, w.bundesland_id, sum(s.stimmenAnzahl) as stimmenAnzahl
	 from StimmenCountParteiWahlkreisAggregiert s, Wahlkreis w
	 where s.wahlkreis_id = w.id
	 group by s.partei_id, w.bundesland_id
;
