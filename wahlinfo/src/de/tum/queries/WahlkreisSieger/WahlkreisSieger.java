package de.tum.queries.WahlkreisSieger;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WahlkreisSieger {

	private String wahlkreisname;
	private String siegerparteierststimme;
	private String siegerparteizweitstimme;

	public String getWahlkreisname() {
		return wahlkreisname;
	}

	public void setWahlkreisname(String wahlkreisname) {
		this.wahlkreisname = wahlkreisname;
	}

	public String getSiegerparteierststimme() {
		return siegerparteierststimme;
	}

	public void setSiegerparteierststimme(String siegerparteierststimme) {
		this.siegerparteierststimme = siegerparteierststimme;
	}

	public String getSiegerparteizweitstimme() {
		return siegerparteizweitstimme;
	}

	public void setSiegerparteizweitstimme(String siegerparteizweitstimme) {
		this.siegerparteizweitstimme = siegerparteizweitstimme;
	}
}
