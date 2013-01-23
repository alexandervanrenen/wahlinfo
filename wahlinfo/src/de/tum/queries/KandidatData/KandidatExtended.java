package de.tum.queries.KandidatData;

import javax.xml.bind.annotation.XmlRootElement;

import de.tum.domain.Kandidat;
import de.tum.domain.Partei;
import de.tum.domain.Wahlkreis;

@XmlRootElement(name = "kandidat")
public class KandidatExtended extends Kandidat {

	private int geburtsjahr;
	private Partei partei;
	private Wahlkreis wahlkreis;

	public int getGeburtsjahr() {
		return geburtsjahr;
	}

	public void setGeburtsjahr(int geburtsjahr) {
		this.geburtsjahr = geburtsjahr;
	}

	public Partei getPartei() {
		return partei;
	}

	public void setPartei(Partei partei) {
		this.partei = partei;
	}

	public Wahlkreis getWahlkreis() {
		return wahlkreis;
	}

	public void setWahlkreis(Wahlkreis wahlkreis) {
		this.wahlkreis = wahlkreis;
	}
}
