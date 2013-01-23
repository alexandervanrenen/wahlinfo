package de.tum.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Partei {

	private int id;
	private String name;
	private String kurzbezeichnung;
	private String farbe;

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

	public String getKurzbezeichnung() {
		return kurzbezeichnung;
	}

	public void setKurzbezeichnung(String kurzbezeichnung) {
		this.kurzbezeichnung = kurzbezeichnung;
	}

	public String getFarbe() {
		return farbe;
	}

	public void setFarbe(String farbe) {
		this.farbe = farbe;
	}

	public Partei readFromResultSet(ResultSet rs) throws SQLException {
		id = rs.getInt("partei_id");
		name = rs.getString("partei_name");
		kurzbezeichnung = rs.getString("partei_kurzbezeichnung");
		farbe = rs.getString("partei_farbe");
		return this;
	}
}
