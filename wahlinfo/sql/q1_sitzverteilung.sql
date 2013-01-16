select p.name as name, s.sitze as sitze, p.farbe as farbe, p.kurzbezeichnung as kurzbezeichnung from SitzVerteilung s, Partei p where s.partei_id=p.id;
