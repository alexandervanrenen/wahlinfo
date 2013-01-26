
drop table stimme;
drop table StimmenCountKandidatAggregiert;
drop table StimmenCountParteiBundeslandAggregiert;
drop table StimmenCountParteiWahlkreisAggregiert;
drop table landesliste;
drop table kandidat;
drop table kandidat2005;
drop table partei;
drop table partei2005;
drop table wahlkreis;
drop table bundesland;

create table bundesland (
        id integer primary key,
        name varchar(30) not null
);

create table wahlkreis (
        id integer primary key,
        name varchar(100) not null,
        bundesland_id integer not null references bundesland(id),
        wahlberechtigte integer not null,
        wahlberechtigte2005 integer not null,
        waehler integer not null,
        waehler2005 integer not null
);

create table partei (
        id integer primary key,
        name varchar(80) not null,
        kurzbezeichnung varchar(20) not null,
        farbe varchar(10)
);

create table partei2005 (
        id integer not null,
        name varchar(80) not null,
        kurzbezeichnung varchar(20) not null,
        farbe varchar(10),
        stimmenaggregiert integer,
        wahlkreis_id integer not null references wahlkreis(id),
        primary key (id, wahlkreis_id)
);

create table kandidat (
        id integer primary key,
        vorname varchar(50) not null,
        name varchar(50) not null,
        geburtsjahr integer not null,
        listenrang integer,
        partei_id integer not null references partei(id),
        wahlkreis_id integer not null references wahlkreis(id)
);

create table kandidat2005 (
        id integer primary key,
        vorname varchar(50) not null,
        name varchar(50) not null,
        geburtsjahr integer not null,
        partei_id integer,
        wahlkreis_id integer not null references wahlkreis(id),
        stimmenaggregiert integer,
        foreign key (partei_id, wahlkreis_id) references partei2005(id, wahlkreis_id)
);

create table landesliste (
        bundesland_id integer not null references bundesland(id),
        kandidat_id integer not null references kandidat(id),
        primary key (bundesland_id, kandidat_id)
);

create table stimme (
        kandidat_id integer not null references kandidat(id),
        partei_id integer not null references partei(id),
        wahlkreis_id integer not null references wahlkreis(id)
);

create table StimmenCountParteiWahlkreisAggregiert (
	partei_id integer not null references partei(id),
	wahlkreis_id integer not null references wahlkreis(id),
	stimmenAnzahl int not null,
	primary key(partei_id, wahlkreis_id)
);

create table StimmenCountParteiBundeslandAggregiert (
	partei_id integer not null references partei(id),
	bundesland_id integer not null references bundesland(id),
	stimmenAnzahl int not null,
	primary key(partei_id, bundesland_id)
);

create table StimmenCountKandidatAggregiert (
	kandidat_id integer primary key not null,
	stimmenAnzahl int not null
);
