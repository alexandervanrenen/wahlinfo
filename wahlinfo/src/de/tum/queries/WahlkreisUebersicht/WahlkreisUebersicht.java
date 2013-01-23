package de.tum.queries.WahlkreisUebersicht;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.tum.domain.Kandidat;
import de.tum.domain.Partei;
import de.tum.domain.Wahlkreis;

@XmlRootElement
public class WahlkreisUebersicht {
	private float wahlBeteiligung;
	private float wahlBeteiligungVorjahr;
	private Kandidat gewinnerKandidat;
	private Partei gewinnerPartei;
	private Wahlkreis wahlkreis;

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

	public Kandidat getGewinnerKandidat() {
		return gewinnerKandidat;
	}

	public void setGewinnerKandidat(Kandidat kandidat) {
		this.gewinnerKandidat = kandidat;
	}

	public Wahlkreis getWahlkreis() {
		return wahlkreis;
	}

	public void setWahlkreis(Wahlkreis wahlkreis) {
		this.wahlkreis = wahlkreis;
	}

	public Partei getGewinnerPartei() {
		return gewinnerPartei;
	}

	public void setGewinnerPartei(Partei gewinnerPartei) {
		this.gewinnerPartei = gewinnerPartei;
	}

	public ArrayList<ParteiErgebnis> getParteiergebnisse() {
		return parteiergebnisse;
	}

	public void addParteiergebnis(ParteiErgebnis parteiergebnisse) {
		this.parteiergebnisse.add(parteiergebnisse);
	}
}
