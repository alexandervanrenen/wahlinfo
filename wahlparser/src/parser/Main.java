package parser;

import java.io.IOException;

/**
 * @author alex
 * stating point of the program
 */
public class Main {

	/**
	 * no args ..
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

		// load stimmen (has to be done before loading the kandidatas)
		try {
			parser.loadStimmen("Stimmen.csv");
		} catch (IOException e) {
			System.out.println("fail loading stimmen");
			e.printStackTrace();
		}

		// load kandidaten
		try {
			parser.readKandidaten("Kandidaten.csv", "Kandidaten.tbl");
		} catch (IOException e) {
			System.out.println("fail loading kandidaten");
			e.printStackTrace();
		}

		// generate the votes
		try {
			parser.generateVotes2009("Stimmen.tbl");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// generate the votes
		try {
			parser.generateVoteAggregates2005("Parteien2005.tbl", "Kandidaten2005.tbl");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("done =)");
	}

}
