package de.tum.sql;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SqlStatements {

	private static String sqlDir = "../wahlinfo/sql/";

	public enum Query {
		FindAllBundeslaender, Sitzverteilung, BundestagMitglieder, WahlkreisUebersicht1, WahlkreisUebersicht2, WahlkreisUebersicht3, WahlkreisSieger, Ueberhangmandate, Stimmabgabe_Check, Stimmabgabe_Insert, CalculateDeutschland, FindAllWahlkreise, FindAllWahlkreiseOfBundesland, FindWahlkreisById, FindWahlkreisByName, FindBundeslandByName, FindBundeslandById, StimmzettelParteien, KnappsteSieger, FindAllKandidaten, FindKandidatById, FindKandidatByName, FindAllParteien, FindParteiById, FindParteiByName
	}

	public static String getQuery(Query type) throws IOException {
		switch (type) {
		case CalculateDeutschland:
			return readFileAsString(sqlDir + "static_data_calculate_deutschland.sql");
			// Bundesländer
		case FindAllBundeslaender:
			return readFileAsString(sqlDir + "static_data_find_bundeslaender_all.sql");
		case FindBundeslandById:
			return readFileAsString(sqlDir + "static_data_find_bundesland_by_id.sql");
		case FindBundeslandByName:
			return readFileAsString(sqlDir + "static_data_find_bundesland_by_name.sql");
			// Wahlkreise
		case FindAllWahlkreise:
			return readFileAsString(sqlDir + "static_data_find_wahlkreise_all.sql");
		case FindAllWahlkreiseOfBundesland:
			return readFileAsString(sqlDir + "static_data_find_wahlkreise_all_of_bundesland.sql");
		case FindWahlkreisById:
			return readFileAsString(sqlDir + "static_data_find_wahlkreis_by_id.sql");
		case FindWahlkreisByName:
			return readFileAsString(sqlDir + "static_data_find_wahlkreis_by_name.sql");
			// Kandidaten
		case FindAllKandidaten:
			return readFileAsString(sqlDir + "static_data_find_kandidat_all.sql");
		case FindKandidatById:
			return readFileAsString(sqlDir + "static_data_find_kandidat_by_id.sql");
		case FindKandidatByName:
			return readFileAsString(sqlDir + "static_data_find_kandidat_by_name.sql");
			// Parteien
		case FindAllParteien:
			return readFileAsString(sqlDir + "static_data_find_partei_all.sql");
		case FindParteiById:
			return readFileAsString(sqlDir + "static_data_find_partei_by_id.sql");
		case FindParteiByName:
			return readFileAsString(sqlDir + "static_data_find_partei_by_name.sql");
			// Blatt 7
		case Sitzverteilung:
			return readFileAsString(sqlDir + "q0_helper_withs.sql") + " " + readFileAsString(sqlDir + "q1_sitzverteilung.sql");
		case BundestagMitglieder:
			return readFileAsString(sqlDir + "q0_helper_withs.sql") + " " + readFileAsString(sqlDir + "q2_bundestag_mitglieder.sql");
		case WahlkreisUebersicht1:
			return readFileAsString(sqlDir + "q0_helper_withs.sql") + " " + readFileAsString(sqlDir + "q3_wahlkreis_uebersicht_1.sql");
		case WahlkreisUebersicht2:
			return readFileAsString(sqlDir + "q0_helper_withs.sql") + " " + readFileAsString(sqlDir + "q3_wahlkreis_uebersicht_2.sql");
		case WahlkreisUebersicht3:
			return readFileAsString(sqlDir + "q0_helper_withs.sql") + " " + readFileAsString(sqlDir + "q3_wahlkreis_uebersicht_3.sql");
		case WahlkreisSieger:
			return readFileAsString(sqlDir + "q0_helper_withs.sql") + " " + readFileAsString(sqlDir + "q4_wahlkreis_sieger.sql");
		case Ueberhangmandate:
			return readFileAsString(sqlDir + "q0_helper_withs.sql") + " " + readFileAsString(sqlDir + "q5_ueberhangmandate.sql");
		case KnappsteSieger:
			return readFileAsString(sqlDir + "q0_helper_withs.sql") + " " + readFileAsString(sqlDir + "q6_knappste_sieger.sql");
			// Blatt 9 oder so .. ;)
		case Stimmabgabe_Check:
			return readFileAsString(sqlDir + "q0_helper_withs.sql") + " " + readFileAsString(sqlDir + "qx1_stimmabgabe_check.sql");
		case Stimmabgabe_Insert:
			return readFileAsString(sqlDir + "qx1_stimmabgabe_insert.sql");
		case StimmzettelParteien:
			return readFileAsString(sqlDir + "qx2_stimmzettel_parteien.sql");
		default:
			throw new RuntimeException("not impl");
		}
	}

	/**
	 * copy pasted from
	 * http://stackoverflow.com/questions/1656797/how-to-read-a-
	 * file-into-string-in-java think of a better way: first check length of the
	 * file, then create buffer and then read it..
	 * 
	 * @throws IOException
	 */
	private static String readFileAsString(String filePath) throws IOException {
		StringBuffer fileData = new StringBuffer();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
		}
		reader.close();
		return fileData.toString();
	}
}
