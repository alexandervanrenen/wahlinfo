package parser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class Parser {
	
	private HashMap<String, Integer> parteiMap;

	/**
	 * set up the parser
	 */
	public Parser() {
		parteiMap = new HashMap<String, Integer>();
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
		}
		
		// write file
		CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath), '|', CSVWriter.NO_QUOTE_CHARACTER);
		writer.writeAll(data);
		writer.close();		
	}
}
