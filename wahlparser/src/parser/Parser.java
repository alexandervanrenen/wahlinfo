package parser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author alex
 * used for parsing ..
 */
public class Parser {
	
	/**
	 * maps bundesland name to bundesland id
	 */
	private HashMap<String, String> bundeslandMap;
	
	/**
	 * maps landesliste id to bundesland id
	 */
	private HashMap<String, String> landeslisteMap;
	
	/**
	 * maps the partei acronym to the partei id
	 */
	private HashMap<String, Partei> parteiMap;

	/** 
	 * stores the votes for each partei in each wahlkreis
	 * wahlkeise.get(wahlKreisId-1).get(parteiId)
	 * 
	 * sample access:
	 * number of zweitstimmen votes for SPD in the 27 wahlkreis in the year 2009
	 * wahlkeise.get(26).wahlkreisId + " " + wahlkeise.get(26).parteien.get(parteiMap.get("SPD")).zweitstimme2009);
	 */
	private ArrayList<WahlKreis> wahlkeise;
	
	/**
	 * stores all candidates without a partei
	 */
	private ArrayList<Partei> lonleyCandidates;

	/**
	 * set up the parser
	 */
	public Parser() {
		bundeslandMap = new HashMap<String, String>();
		landeslisteMap = new HashMap<String, String>();
		parteiMap = new HashMap<String, Partei>();
		wahlkeise = new ArrayList<WahlKreis>();
		lonleyCandidates = new ArrayList<Partei>();
	}
	
	/**
	 * read information of all bundeslaender
	 * keep the name -> id mapping
	 * write info to file
	 * @param inputFile file to read from
	 * @param outputFile file to write to
	 * @throws IOException
	 */
	public void readBundesLaender(String inputFilePath, String outputFilePath) throws IOException {
		// read file
		CSVReader reader = new CSVReader(new FileReader(inputFilePath), ';');
		List<String[]> data = reader.readAll();
		reader.close();
		data.remove(0); // drop schema line
		
		// remember bundesland name -> id mapping
		for (int i = 0; i < data.size(); i++) {
			bundeslandMap.put(data.get(i)[1], data.get(i)[0]);
		}

		// write file		
		CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath), '|', CSVWriter.NO_QUOTE_CHARACTER);
		writer.writeAll(data);
		writer.close();
	}
	
	public void readLandeslisten(String landeslistenPath,
			String listenplaetzePath, String outputFilePath) throws IOException {
		// read landeslisten
		CSVReader reader = new CSVReader(new FileReader(landeslistenPath), ';');
		List<String[]> data = reader.readAll();
		reader.close();
		data.remove(0); // drop schema line
		
		// remember landesliste id -> bundesland id mapping
		String bundeslandId;
		for (int i = 0; i < data.size(); i++) {
			// get bundesland id
			bundeslandId = bundeslandMap.get(data.get(i)[1]);
			if (bundeslandId == null)
				throw new IOException("Failed to get ID of " + data.get(i)[1]);
			
			landeslisteMap.put(data.get(i)[0], bundeslandId);
		}
		
		// read listenplaetze
		reader = new CSVReader(new FileReader(listenplaetzePath), ';');
		data = reader.readAll();
		reader.close();
		data.remove(0); // drop schema line
		
		// replace landesliste id with bundesland id
		for (int i = 0; i < data.size(); i++) {
			data.get(i)[0] = landeslisteMap.get(data.get(i)[0]);
		}

		// write file
		CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath), '|',
				CSVWriter.NO_QUOTE_CHARACTER);
		writer.writeAll(data);
		writer.close();
	}

	/**
	 * reads the information of all wahlkreise and writes it to the output
	 * @param inputFile file to read from
	 * @param outputFile file to write to
	 * @throws IOException 
	 */
	public void readWahlkreise(String inputFilePath, String outputFilePath) throws IOException {
		// read file
		CSVReader reader = new CSVReader(new FileReader(inputFilePath), ';');
		List<String[]> data = reader.readAll();
		reader.close();
		data.remove(0); // drop schema line

		// write file
		CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath), '|', CSVWriter.NO_QUOTE_CHARACTER);
		writer.writeAll(data);
		writer.close();
	}

	/**
	 * reads the information of all parties and writes it to the output
	 * @param inputFile file to read from
	 * @param outputFile file to write to
	 * @throws IOException 
	 */
	public void readParteien(String inputFilePath, String outputFilePath) throws IOException {
		// read file
		CSVReader reader = new CSVReader(new FileReader(inputFilePath), ';');
		List<String[]> data = reader.readAll();
		reader.close();
		data.remove(0); // drop schema line
		CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath), '|', CSVWriter.NO_QUOTE_CHARACTER);

		// store identifier of the parties
		for (int i = 0; i < data.size(); i++) {
			Partei partei = new Partei(Integer.decode(data.get(i)[0]), data.get(i)[1], data.get(i)[2]);
			parteiMap.put(data.get(i)[2], partei);
			writer.writeNext(new String[] {Integer.toString(partei.parteiId), partei.longName, partei.shortName, "white"});
		}

		// write file
		writer.close();
	}

	/**
	 * reads the information of all votes and writes it to the output
	 * @param inputFile file to read from
	 * @param outputFile file to write to
	 * @throws IOException 
	 */
	public void loadStimmen(String inputFilePath) throws IOException {
		// read file
		CSVReader reader = new CSVReader(new FileReader(inputFilePath), ';');
		List<String[]> data = reader.readAll();
		reader.close();
		data.remove(0); // drop schema line (first row)

		// read all party names to a list (second row)
		String [] partyLine = data.get(0);
		data.remove(0);
		ArrayList<Integer> parteienListe = new ArrayList<Integer>();
		for (int i = 1; i < partyLine.length; i+=4)
			if(parteiMap.get(partyLine[i]) != null)
				parteienListe.add(parteiMap.get(partyLine[i]).parteiId); else
				throw new IOException("unknown partei: " + partyLine[i]);

		// loop over each line of the actual data
		for (String[] line : data) {
			// read one wahlkreis
			WahlKreis currentWahlkreis = new WahlKreis(Integer.decode(line[0]), new HashMap<Integer, Partei>());
			for (int i = 1; i < line.length; i+=4) {
				// read the votes for this party
				Partei partei = new Partei(parteiMap.get(partyLine[i]).parteiId, parteiMap.get(partyLine[i]).longName, parteiMap.get(partyLine[i]).shortName);
				partei.parteiId = parteienListe.get(i/4); 
				partei.erststimme2009 = line[i+0].length()==0?0:Integer.decode(line[i+0]);
				partei.erststimme2005 = line[i+1].length()==0?0:Integer.decode(line[i+1]);
				partei.zweitstimme2009 = line[i+2].length()==0?0:Integer.decode(line[i+2]);
				partei.zweitstimme2005 = line[i+3].length()==0?0:Integer.decode(line[i+3]);

				// add this parties result to the current wahlkreis
				currentWahlkreis.parteien.put(partei.parteiId, partei);
			}
			wahlkeise.add(currentWahlkreis);
		}
	}

	/**
	 * reads the information of all candidates and writes it to the output
	 * @param inputFile file to read from
	 * @param outputFile file to write to
	 * @throws IOException 
	 */
	public void readKandidaten(String inputFilePath, String outputFilePath) throws IOException {
		// read file
		CSVReader reader = new CSVReader(new FileReader(inputFilePath), ';');
		List<String[]> data = reader.readAll();
		reader.close();
		data.remove(0); // drop schema line
		CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath), '|', CSVWriter.NO_QUOTE_CHARACTER);
		
		// clean up data
		for (String[] iter : data) {
			// fix candidates without a list
			if (iter[4].length() == 0)
				iter[4] = "0";
			
			// fix candidates without a party and map party name to party id
			if (iter[5].length() == 0)
				iter[5] = "0"; else
				if(parteiMap.containsKey(iter[5]))
					iter[5] = Integer.toString(parteiMap.get(iter[5]).parteiId); else
					throw new IOException("found unknown partei '" + iter[5] + "'in candidate " + iter[7]);
			
			// fix candidates without a wahlkreis
			if (iter[6].length() == 0)
				iter[6] = "0";
			
			// add kandidat info to his wahlkreis for his partei
			int wahlkreis = Integer.decode(iter[6]);
			int parteiId = Integer.decode(iter[5]);
			Partei partei = null;
			if(wahlkreis != 0 && parteiId != 0) {
				partei = wahlkeise.get(wahlkreis-1).parteien.get(parteiId);
			} else {
				partei = new Partei(parteiId, "none", "none");
				lonleyCandidates.add(partei);
			}
			partei.kandidatId = Integer.decode(iter[0]);
			partei.kandidatVorname = iter[1];
			partei.kandidatNachname = iter[2];
			partei.kandidatGeburtsjahr = iter[3];
			partei.kandidatListenrang = iter[4];
			
			// write data
			writer.writeNext(new String [] {Integer.toString(partei.kandidatId), partei.kandidatVorname, partei.kandidatNachname, partei.kandidatGeburtsjahr, partei.kandidatListenrang, Integer.toString(partei.parteiId), Integer.toString(wahlkreis)});
		}
		
		// write file
		writer.close();		
	}

	/**
	 * @param outputFilePath
	 * @throws IOException
	 */
	public void generateVotes2009(String outputFilePath) throws IOException {
		// just forward the work =)
		VoteGenerator.generate(outputFilePath, wahlkeise);
	}

	/**
	 * @param outputFilePath
	 * @throws IOException
	 */
	public void generateVoteAggregates2005(String outputFilePathPartei, String outputFilePathKandidat) throws IOException {
		// create each partei in each wahlkreis
		CSVWriter parteiWriter = new CSVWriter(new FileWriter(outputFilePathPartei), '|', CSVWriter.NO_QUOTE_CHARACTER);
		CSVWriter kandidatWriter = new CSVWriter(new FileWriter(outputFilePathKandidat), '|', CSVWriter.NO_QUOTE_CHARACTER);
		
		// add parties and candidates with wahlkreise
		for (WahlKreis wahlKreis : wahlkeise) {
			for (Partei partei : wahlKreis.parteien.values()) {
				parteiWriter.writeNext(new String [] {Integer.toString(partei.parteiId), partei.longName, partei.shortName, "white", Integer.toString(partei.zweitstimme2005), Integer.toString(wahlKreis.wahlkreisId)});
				if(partei.kandidatId != -1)
					kandidatWriter.writeNext(new String [] {Integer.toString(partei.kandidatId), partei.kandidatVorname, partei.kandidatNachname, partei.kandidatGeburtsjahr, Integer.toString(partei.parteiId), Integer.toString(wahlKreis.wahlkreisId), Integer.toString(partei.erststimme2005)});
			}
		}
		
		// add candidates without wahlkreise
		for (Partei candidate : lonleyCandidates)
			kandidatWriter.writeNext(new String [] {Integer.toString(candidate.kandidatId), candidate.kandidatVorname, candidate.kandidatNachname, candidate.kandidatGeburtsjahr, Integer.toString(candidate.parteiId), "0", "0"});
		
		parteiWriter.close();
		kandidatWriter.close();
	}
}
