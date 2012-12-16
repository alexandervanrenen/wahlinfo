package de.tum.wahlinfo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WahlkreisUebersicht {
	private float wahlBeteiligung;
	private float wahlBeteiligungVorjahr;
	private int kandidatenId; // winner
	private String kandidatenName;
	private String kandidatenVorname;

	@XmlElement(name = "parteiergebnisse")
	private ArrayList<ParteiErgebnis> parteiergebnisse = new ArrayList<ParteiErgebnis>();

	public float getWahlBeteiligung() {
		return wahlBeteiligung;
	}

	public void setWahlBeteiligung(float wahlBeteiligung) {
		this.wahlBeteiligung = wahlBeteiligung;
	}

	public float getWahlBeteiligungVorjahr() {
		return wahlBeteiligungVorjahr;
	}

	public void setWahlBeteiligungVorjahr(float wahlBeteiligungVorjahr) {
		this.wahlBeteiligungVorjahr = wahlBeteiligungVorjahr;
	}

	public int getKandidatenId() {
		return kandidatenId;
	}

	public void setKandidatenId(int kandidatenId) {
		this.kandidatenId = kandidatenId;
	}

	public String getKandidatenName() {
		return kandidatenName;
	}

	public void setKandidatenName(String kandidatenName) {
		this.kandidatenName = kandidatenName;
	}

	public String getKandidatenVorname() {
		return kandidatenVorname;
	}

	public void setKandidatenVorname(String kandidatenVorname) {
		this.kandidatenVorname = kandidatenVorname;
	}

	public ArrayList<ParteiErgebnis> getParteiergebnisse() {
		return parteiergebnisse;
	}

	public void addParteiergebnis(ParteiErgebnis parteiergebnisse) {
		this.parteiergebnisse.add(parteiergebnisse);
	}
}
