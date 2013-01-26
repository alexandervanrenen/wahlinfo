package de.tum.queries.KandidatData;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "kandidat")
public class KandidatExtended {

	private String name;
	private String vorname;
	private int geburtsjahr;
	private String partei;
	private String wahlkreis;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public int getGeburtsjahr() {
		return geburtsjahr;
	}

	public void setGeburtsjahr(int geburtsjahr) {
		this.geburtsjahr = geburtsjahr;
	}

	public String getPartei() {
		return partei;
	}

	public void setPartei(String partei) {
		this.partei = partei;
	}

	public String getWahlkreis() {
		return wahlkreis;
	}

	public void setWahlkreis(String wahlkreis) {
		this.wahlkreis = wahlkreis;
	}
}
