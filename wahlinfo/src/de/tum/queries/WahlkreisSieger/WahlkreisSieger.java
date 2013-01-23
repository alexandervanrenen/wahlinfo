package de.tum.queries.WahlkreisSieger;

import javax.xml.bind.annotation.XmlRootElement;

import de.tum.domain.Partei;
import de.tum.domain.Wahlkreis;

@XmlRootElement
public class WahlkreisSieger {

	private Wahlkreis wahlkreis;
	private Partei siegerParteiErststimme;
	private Partei siegerParteiZweitstimme;

	public Wahlkreis getWahlkreis() {
		return wahlkreis;
	}

	public void setWahlkreis(Wahlkreis wahlkreis) {
		this.wahlkreis = wahlkreis;
	}

	public Partei getSiegerParteiErststimme() {
		return siegerParteiErststimme;
	}

	public void setSiegerParteiErststimme(Partei siegerparteierststimme) {
		this.siegerParteiErststimme = siegerparteierststimme;
	}

	public Partei getSiegerParteiZweitstimme() {
		return siegerParteiZweitstimme;
	}

	public void setSiegerParteiZweitstimme(Partei siegerparteizweitstimme) {
		this.siegerParteiZweitstimme = siegerparteizweitstimme;
	}
}
