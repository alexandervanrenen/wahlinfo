package de.tum.wahlinfo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BundestagMitglied {
	
	private String vorname;
	private String name;
	private String partei;
	private String wahlkreis;

	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPartei() {
		return partei;
	}
	public void setPartei(String partei) {
		this.partei = partei;
	}
	public String getWahlkreis() {
		return wahlkreis;
	}
	public void setWahlkreis(String wahlkreis) {
		this.wahlkreis = wahlkreis;
	}
}
