package de.tum.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Kandidat {

	private int id;
	private String name;
	private String vorname;

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

	public static Kandidat readFromResultSet(ResultSet rs) throws SQLException {
		Kandidat k = new Kandidat();
		k.id = rs.getInt("kandidat_id");
		k.name = rs.getString("kandidat_name");
		k.vorname = rs.getString("kandidat_vorname");
		if(k.id == 0)
			return null;
		return k;
	}
}
