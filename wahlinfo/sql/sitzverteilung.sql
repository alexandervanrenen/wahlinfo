select p.name as name, s.sitze as sitze, p.farbe as farbe from SitzVerteilung s, Partei p where s.partei_id=p.id;
