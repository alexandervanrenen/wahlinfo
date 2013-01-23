package de.tum.queries.WahlkreisUebersicht;

import javax.xml.bind.annotation.XmlRootElement;

import de.tum.domain.Partei;

@XmlRootElement
public class ParteiErgebnis {
	private Partei partei;
	private float stimmenAnteil;
	private float stimmenAnteilVorjahr;
	private int stimmenAnzahl;
	private int stimmenAnzahlVorjahr;

	public Partei getPartei() {
		return partei;
	}

	public void setPartei(Partei partei) {
		this.partei = partei;
	}

	public float getStimmenAnteil() {
		return stimmenAnteil;
	}

	public void setStimmenAnteil(float stimmenAnteil) {
		this.stimmenAnteil = stimmenAnteil;
	}

	public float getStimmenAnteilVorjahr() {
		return stimmenAnteilVorjahr;
	}

	public void setStimmenAnteilVorjahr(float stimmenAnteilVorjahr) {
		this.stimmenAnteilVorjahr = stimmenAnteilVorjahr;
	}

	public int getStimmenAnzahl() {
		return stimmenAnzahl;
	}

	public void setStimmenAnzahl(int stimmenAnzahl) {
		this.stimmenAnzahl = stimmenAnzahl;
	}

	public int getStimmenAnzahlVorjahr() {
		return stimmenAnzahlVorjahr;
	}

	public void setStimmenAnzahlVorjahr(int stimmenAnzahlVorjahr) {
		this.stimmenAnzahlVorjahr = stimmenAnzahlVorjahr;
	}
}
