package de.tum.queries.GeographicData;

import javax.xml.bind.annotation.XmlRootElement;

import de.tum.domain.Wahlkreis;

@XmlRootElement(name = "wahlkreis")
public class WahlkreisExtended extends Wahlkreis {

	private int bundeslandid;
	private int wahlberechtigte;
	private int wahlberechtigte2005;
	private int waehler;
	private int waehler2005;
	private String bundesland_name;

	public int getBundeslandid() {
		return bundeslandid;
	}

	public void setBundeslandId(int bundeslandid) {
		this.bundeslandid = bundeslandid;
	}

	public int getWahlberechtigte() {
		return wahlberechtigte;
	}

	public void setWahlberechtigte(int wahlberechtigte) {
		this.wahlberechtigte = wahlberechtigte;
	}

	public int getWahlberechtigte2005() {
		return wahlberechtigte2005;
	}

	public void setWahlberechtigte2005(int wahlberechtigte2005) {
		this.wahlberechtigte2005 = wahlberechtigte2005;
	}

	public int getWaehler() {
		return waehler;
	}

	public void setWaehler(int waehler) {
		this.waehler = waehler;
	}

	public int getWaehler2005() {
		return waehler2005;
	}

	public void setWaehler2005(int waehler2005) {
		this.waehler2005 = waehler2005;
	}

	public String getBundeslandName() {
		return bundesland_name;
	}

	public void setBundeslandName(String bundesland_name) {
		this.bundesland_name = bundesland_name;
	}
}
