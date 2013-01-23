package de.tum.queries.BundestagMitglieder;

import javax.xml.bind.annotation.XmlRootElement;

import de.tum.domain.Kandidat;
import de.tum.domain.Partei;
import de.tum.domain.Wahlkreis;

@XmlRootElement
public class BundestagMitglied {

	private Partei partei;
	private Kandidat kandidat;
	private Wahlkreis wahlkreis;

	public Partei getPartei() {
		return partei;
	}

	public void setPartei(Partei partei) {
		this.partei = partei;
	}

	public Kandidat getKandidat() {
		return kandidat;
	}

	public void setKandidat(Kandidat kandidat) {
		this.kandidat = kandidat;
	}

	public Wahlkreis getWahlkreis() {
		return wahlkreis;
	}

	public void setWahlkreis(Wahlkreis wahlkreis) {
		this.wahlkreis = wahlkreis;
	}
}
