package de.tum.queries.KnappsteSieger;

import javax.xml.bind.annotation.XmlRootElement;

import de.tum.domain.Kandidat;
import de.tum.domain.Partei;
import de.tum.domain.Wahlkreis;

@XmlRootElement
public class KnappsteSiegerTeilnehmer {

	private Partei partei;
	private Kandidat kandidat;
	private Wahlkreis wahlkreis;
	private int stimmenVorsprung;

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

	public int getStimmenVorsprung() {
		return stimmenVorsprung;
	}

	public void setStimmenVorsprung(int stimmenVorsprung) {
		this.stimmenVorsprung = stimmenVorsprung;
	}
}
