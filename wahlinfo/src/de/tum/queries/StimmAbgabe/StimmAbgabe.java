package de.tum.queries.StimmAbgabe;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StimmAbgabe {

	private boolean erfolg;
	
	public boolean isErfolg() {
		return erfolg;
	}

	public void setErfolg(boolean erfolg) {
		this.erfolg = erfolg;
	}

	public String getFehler() {
		return fehler;
	}

	public void setFehler(String fehler) {
		this.fehler = fehler;
	}

	private String fehler;
}
