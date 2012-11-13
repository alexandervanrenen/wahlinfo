package parser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import au.com.bytecode.opencsv.CSVWriter;

public class VoteGenerator {

	public static void generate(String outputFilePath,
			ArrayList<WahlKreis> wahlkeise) throws IOException {

		// create output and hope that is works stream based ..
		int lastPercentage = -1;
		CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath), '|',
				CSVWriter.NO_QUOTE_CHARACTER);

		// generate the votes for each wahlkreis
		for (WahlKreis wahlKreis : wahlkeise) {
			if (wahlKreis.wahlkreisId != 3)
				continue;

			int currentPercentage = (int) (wahlKreis.wahlkreisId
					/ (float) wahlkeise.size() * 100);
			if (lastPercentage != currentPercentage)
				System.out.println(currentPercentage + "%");
			lastPercentage = currentPercentage;
			// System.out.println("processing wahlkreis: " +
			// wahlKreis.wahlkreisId + "   " +
			// wahlKreis.parteien.get(parteiMap.get("SPD")).parteiId + "  " +
			// wahlKreis.parteien.get(4).zweitstimme2009);

			Iterator<Entry<Integer, Partei>> erstIter = wahlKreis.parteien
					.entrySet().iterator();
			Iterator<Entry<Integer, Partei>> zweitIter = wahlKreis.parteien
					.entrySet().iterator();

			Partei currentErstPartei = erstIter.next().getValue();
			Partei currentZweitPartei = zweitIter.next().getValue();

			while (erstIter.hasNext() && zweitIter.hasNext()) {

				if (currentErstPartei.erststimme2009 <= 0)
					currentErstPartei = erstIter.next().getValue();
				if (currentZweitPartei.zweitstimme2009 <= 0)
					currentZweitPartei = zweitIter.next().getValue();

				while (currentErstPartei.erststimme2009 > 0
						&& currentZweitPartei.zweitstimme2009 > 0) {

					writer.writeNext(new String[] {
								Integer.toString(currentErstPartei.kandidatId == -1 ? 0
										: currentErstPartei.kandidatId),
								Integer.toString(currentZweitPartei.parteiId),
								Integer.toString(wahlKreis.wahlkreisId) });
					currentErstPartei.erststimme2009--;
					currentZweitPartei.zweitstimme2009--;
				}
			}

			while (erstIter.hasNext()) {

				if (currentErstPartei.erststimme2009 <= 0)
					currentErstPartei = erstIter.next().getValue();

				while (currentErstPartei.erststimme2009 > 0) {
					writer.writeNext(new String[] {
							Integer.toString(currentErstPartei.kandidatId == -1 ? 0
									: currentErstPartei.kandidatId), "0",
							Integer.toString(wahlKreis.wahlkreisId) });
					currentErstPartei.erststimme2009--;
				}
			}

			while (zweitIter.hasNext()) {

				if (currentZweitPartei.zweitstimme2009 <= 0)
					currentZweitPartei = zweitIter.next().getValue();

				while (currentZweitPartei.zweitstimme2009 > 0) {
					writer.writeNext(new String[] { "0",
							Integer.toString(currentZweitPartei.parteiId),
							Integer.toString(wahlKreis.wahlkreisId) });
					currentZweitPartei.zweitstimme2009--;
				}
			}
		}

		// write data
		writer.close();
	}
}
