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
	 * maps the partei acronym to the partei id
	 */
	private HashMap<String, Integer> parteiMap;

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
	 * set up the parser
	 */
	public Parser() {
		parteiMap = new HashMap<String, Integer>();
		wahlkeise = new ArrayList<WahlKreis>();
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

		// write file		
		CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath), '|', CSVWriter.NO_QUOTE_CHARACTER);
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

		// store identifier of the parties
		for (int i = 0; i < data.size(); i++) {
			parteiMap.put(data.get(i)[2], Integer.decode(data.get(i)[0]));
			data.set(i, new String[] {data.get(i)[0], data.get(i)[1], data.get(i)[2], "white", "0", "2009"});
		}

		// write file
		CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath), '|', CSVWriter.NO_QUOTE_CHARACTER);
		writer.writeAll(data);
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
		ArrayList<Integer> parteien = new ArrayList<Integer>();
		for (int i = 1; i < partyLine.length; i+=4)
			if(parteiMap.get(partyLine[i]) != null)
				parteien.add(parteiMap.get(partyLine[i])); else
				throw new IOException("unknown partei: " + partyLine[i]);

		// loop over each line of the actual data
		for (String[] line : data) {
			// read one wahlkreis
			WahlKreis currentWahlkreis = new WahlKreis(Integer.decode(line[0]), new HashMap<Integer, Partei>());
			for (int i = 1; i < line.length; i+=4) {
				// read the votes for this party
				Partei wahlData = new Partei();
				wahlData.parteiId = parteien.get(i/4); 
				wahlData.erststimme2005 = line[i+0].length()==0?0:Integer.decode(line[i+0]);
				wahlData.erststimme2009 = line[i+1].length()==0?0:Integer.decode(line[i+1]);
				wahlData.zweitstimme2005 = line[i+2].length()==0?0:Integer.decode(line[i+2]);
				wahlData.zweitstimme2009 = line[i+3].length()==0?0:Integer.decode(line[i+3]);

				// add this parties result to the current wahlkreis
				currentWahlkreis.parteien.put(wahlData.parteiId, wahlData);
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
		
		// clean up data
		for (String[] iter : data) {
			// fix candidates without a list
			if (iter[4].length() == 0)
				iter[4] = "0";
			
			// fix candidates without a party and map party name to party id
			if (iter[5].length() == 0)
				iter[5] = "0"; else
				if(parteiMap.containsKey(iter[5]))
					iter[5] = parteiMap.get(iter[5]).toString(); else
					throw new IOException("found unknown partei '" + iter[5] + "'in candidate " + iter[7]);
			
			// fix candidates without a wahlkreis
			if (iter[6].length() == 0)
				iter[6] = "0";
			
			// add kandidat to his wahlkreis for his partei
			if(iter[5].compareTo("0") != 0 && iter[6].compareTo("0") != 0) { // kandidat must have a partei and a wahlkreis
				int wahlkreis = Integer.decode(iter[6])-1;
				int parteiId = Integer.decode(iter[5]);
				wahlkeise.get(wahlkreis).parteien.get(parteiId).kandidatId = Integer.decode(iter[0]);
			}
		}
		
		// write file
		CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath), '|', CSVWriter.NO_QUOTE_CHARACTER);
		writer.writeAll(data);
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
}
