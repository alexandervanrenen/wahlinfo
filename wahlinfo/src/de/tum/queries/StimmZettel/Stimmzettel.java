package de.tum.queries.StimmZettel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Stimmzettel {

	private List<StimmzettelEntry> liste = new ArrayList<StimmzettelEntry>();
	private int wahlkreisId;

	public List<StimmzettelEntry> getListe() {
		return liste;
	}

	public void setListe(List<StimmzettelEntry> liste) {
		this.liste = liste;
	}

	public int getWahlkreisId() {
		return wahlkreisId;
	}

	public void setWahlkreisId(int wahlkreisId) {
		this.wahlkreisId = wahlkreisId;
	}
}
