package parser;

import java.util.HashMap;

/**
 * @author alex
 * encapsulates a wahlkreis
 */
public class WahlKreis {
	/**
	 * create a new wahlkreis
	 * @param wahlkreisId
	 * @param parteien
	 */
	public WahlKreis(int wahlkreisId, HashMap<Integer, Partei> parteien) {
		this.wahlkreisId = wahlkreisId;
		this.parteien = parteien;
	}
	int wahlkreisId;
	int wahlberechtigte;
	int wahlberechtigte2005;
	int waehler;
	int waehler2005;
	HashMap<Integer, Partei> parteien;
}
