package de.tum.queries.StitzVerteilung;

import javax.xml.bind.annotation.XmlRootElement;

import de.tum.domain.Partei;

@XmlRootElement
public class Sitzverteilung {

	private Partei partei;
	private int sitze;
	private int erststimmen;
	private int zweitstimmen;

	public Partei getPartei() {
		return partei;
	}

	public void setPartei(Partei partei) {
		this.partei = partei;
	}

	public int getSitze() {
		return sitze;
	}

	public void setSitze(int sitze) {
		this.sitze = sitze;
	}

	public int getErststimmen() {
		return erststimmen;
	}

	public void setErststimmen(int erststimmen) {
		this.erststimmen = erststimmen;
	}

	public void setZweitstimmen(int zweitstimmen) {
		this.zweitstimmen = zweitstimmen;
	}

	public int getZweitstimmen() {
		return zweitstimmen;
	}
}
