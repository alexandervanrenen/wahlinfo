package de.tum.sql;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SqlStatements {

	private static String sqlDir = "wahlinfo/wahlinfo/sql/";

	public enum Query {
		Bundeslaender, Sitzverteilung, BundestagMitglieder
	};

	public static String getQuery(Query type) throws IOException {
		switch (type) {
		case Bundeslaender:
			return readFileAsString(sqlDir + "q0_helper_withs.sql") + " "
					+ readFileAsString(sqlDir + "bundeslaender.sql");
		case Sitzverteilung:
			return readFileAsString(sqlDir + "q0_helper_withs.sql") + " "
					+ readFileAsString(sqlDir + "q1_sitzverteilung.sql");
		case BundestagMitglieder:
			return readFileAsString(sqlDir + "q0_helper_withs.sql") + " "
					+ readFileAsString(sqlDir + "q2_bundestag_mitglieder.sql");
		default:
			throw new FileNotFoundException();
		}
	}

	/**
	 * copy pasted from
	 * http://stackoverflow.com/questions/1656797/how-to-read-a-
	 * file-into-string-in-java think of a better way: first check length of the
	 * file, then create buffer and then read it..
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
