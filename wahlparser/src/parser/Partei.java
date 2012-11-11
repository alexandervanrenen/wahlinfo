package parser;

/**
 * @author alex
 * encapsulates all information of one partei
 */
public class Partei {
	
	/**
	 * Create a partei with no information
	 */
	public Partei(int parteiId, String longName, String shortName) {
		this.parteiId = parteiId;
		kandidatId = -1;
		this.longName = longName;
		this.shortName = shortName;
		erststimme2005 = 0;
		erststimme2009 = 0;
		zweitstimme2005 = 0;
		zweitstimme2009 = 0;
	}

	int parteiId;
	String longName;
	String shortName;
	public int erststimme2005;
	public int erststimme2009;
	public int zweitstimme2005;
	public int zweitstimme2009;
	
	// each partei is represented by maximal one candidate in each wahlkreis
	int kandidatId;
	String kandidatVorname;
	String kandidatNachname;
	String kandidatGeburtsjahr;
	String kandidatListenrang;
}
