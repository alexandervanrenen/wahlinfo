package de.tum.queries.Ueberhangmandate;

import javax.xml.bind.annotation.XmlRootElement;

import de.tum.domain.Bundesland;
import de.tum.domain.Partei;

@XmlRootElement
public class Ueberhangmandate {

	private Partei partei;
	private Bundesland bundesland;
	private int ueberhangmandate;

	public Partei getPartei() {
		return partei;
	}

	public void setPartei(Partei partei) {
		this.partei = partei;
	}

	public Bundesland getBundesland() {
		return bundesland;
	}

	public void setBundesland(Bundesland bundesland) {
		this.bundesland = bundesland;
	}

	public int getUeberhangmandate() {
		return ueberhangmandate;
	}

	public void setUeberhangmandate(int ueberhangmandate) {
		this.ueberhangmandate = ueberhangmandate;
	}
}
