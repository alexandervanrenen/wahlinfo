package parser;

import java.io.IOException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// print welcome
		System.out.println("started ..");
		Parser parser = new Parser();

		// load bundeslaender
		try {
			parser.readBundesLaender("Bundeslaender.csv", "Bundeslaender.tbl");
		} catch (IOException e) {
			System.out.println("fail loading bundeslaender");
			e.printStackTrace();
		}

		// load wahlkreise
		try {
			parser.readWahlkreise("Wahlkreise.csv", "Wahlkreise.tbl");
		} catch (IOException e) {
			System.out.println("fail loading wahlkreise");
			e.printStackTrace();
		}
		
		// load parteien
		try {
			parser.readParteien("Parteien.csv", "Parteien.tbl");
		} catch (IOException e) {
			System.out.println("fail loading partein");
			e.printStackTrace();
		}
		
		// load kandidaten
		try {
			parser.readKandidaten("Kandidaten.csv", "Kandidaten.tbl");
		} catch (IOException e) {
			System.out.println("fail loading kandidaten");
			e.printStackTrace();
		}
		
		System.out.println("done =)");
	}

}
