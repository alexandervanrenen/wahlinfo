package parser;

import java.io.IOException;

/**
 * @author alex stating point of the program
 */
public class Main {

	/**
	 * no args ..
	 * 
	 * @param args
	 * @throws Exception
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
			return;
		}

		// load landeslisten
		try {
			parser.readLandeslisten("Landeslisten.csv", "Listenplaetze.csv", "Landeslisten.tbl");
		} catch (IOException e) {
			System.out.println("fail loading landeslisten");
			e.printStackTrace();
			return;
		}

		// load wahlkreise
		try {
			parser.readWahlkreise("Wahlkreise.csv", "Wahlkreise.tbl");
		} catch (IOException e) {
			System.out.println("fail loading wahlkreise");
			e.printStackTrace();
			return;
		}

		// load parteien
		try {
			parser.readParteien("Parteien.csv", "Parteien.tbl");
		} catch (IOException e) {
			System.out.println("fail loading partein");
			e.printStackTrace();
			return;
		}

		// load stimmen (has to be done before loading the kandidatas)
		try {
			parser.loadStimmen("Stimmen.csv");
		} catch (IOException e) {
			System.out.println("fail loading stimmen");
			e.printStackTrace();
			return;
		}

		// load kandidaten
		try {
			parser.readKandidaten("Kandidaten.csv", "Kandidaten.tbl");
		} catch (IOException e) {
			System.out.println("fail loading kandidaten");
			e.printStackTrace();
			return;
		}

		// generate the votes
		try {
			parser.generateVotes2009("Stimmen.tbl");
		} catch (IOException e) {
			System.out.println("fail generating votes 2009");
			e.printStackTrace();
			return;
		}

		// generate the votes
		try {
			parser.generateVoteAggregates2005("Parteien2005.tbl", "Kandidaten2005.tbl");
		} catch (IOException e) {
			System.out.println("fail generating votes 2005");
			e.printStackTrace();
			return;
		}

		System.out.println("done =)");
	}

}
