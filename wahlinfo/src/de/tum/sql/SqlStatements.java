package de.tum.sql;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SqlStatements {
	
	public enum Query {Sitzverteilung};

	public static String getQuery(Query type) {
		switch (type) {
		case Sitzverteilung:
			return readFileAsString("wahlinfo/sql/withs.sql") + readFileAsString("wahlinfo/sql/sitzverteilung.sql");
		default:
			return "unknown query";
		}
	}

	/**
	 * copy pasted from
	 * http://stackoverflow.com/questions/1656797/how-to-read-a-
	 * file-into-string-in-java think of a better way: first check length of the
	 * file, then create buffer and then read it..
	 */
	private static String readFileAsString(String filePath) {
		try {
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
		} catch (IOException e) {
			e.printStackTrace();
			return "error while reading: " + filePath;
		}
	}
}
