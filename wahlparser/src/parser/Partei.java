package parser;

/**
 * @author alex
 * encapsulates all information of one partei
 */
public class Partei {
	
	/**
	 * Create a partei with no information
	 */
	public Partei() {
		parteiId = -1;
		kandidatId = -1;
		erststimme2005 = -1;
		erststimme2009 = -1;
		zweitstimme2005 = -1;
		zweitstimme2009 = -1;
	}

	int parteiId;
	int kandidatId;
	public int erststimme2005;
	public int erststimme2009;
	public int zweitstimme2005;
	public int zweitstimme2009;
}
