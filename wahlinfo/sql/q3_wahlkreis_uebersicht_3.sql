
, zweitstimmenFuerDenWahlkreis as ( select s.partei_id as partei_id, count(*) as anzahl
                                    from stimme s
                                    where s.wahlkreis_id = ?
                                    group by s.partei_id )

, zweitstimmenFuerDenWahlkreis2005 as ( select p.id as partei_id, p.name as partei_name, p.stimmenaggregiert as anzahl
                                        from partei2005 p
                                        where p.wahlkreis_id = ? )

select z2005.partei_name as partei, (z.anzahl*1.0) / (select sum(anzahl) from zweitstimmenFuerDenWahlkreis) as anteilStimmen, z.anzahl as anzahlStimmen, (z2005.anzahl*1.0) / (select sum(anzahl) from zweitstimmenFuerDenWahlkreis2005) as anteilStimmenVorjahr, z2005.anzahl as anzahlStimmenVorjahr
from zweitstimmenFuerDenWahlkreis z, zweitstimmenFuerDenWahlkreis2005 z2005
where z.partei_id = z2005.partei_id;
