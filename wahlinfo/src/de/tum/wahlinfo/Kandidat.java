package de.tum.wahlinfo;

public class Kandidat {

	private int id;
	private String name;
	private String vorname;
	private String kurzbezeichnung;
	private String partei;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getKurzbezeichnung() {
		return kurzbezeichnung;
	}

	public void setKurzbezeichnung(String kurzbezeichnung) {
		this.kurzbezeichnung = kurzbezeichnung;
	}

	public String getPartei() {
		return partei;
	}

	public void setPartei(String partei) {
		this.partei = partei;
	}
}
