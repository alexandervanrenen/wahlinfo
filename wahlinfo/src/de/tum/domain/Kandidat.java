package de.tum.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Kandidat {

	private int id;
	private String name;
	private String vorname;
	private int parteiId;

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

	public int getParteiId() {
		return parteiId;
	}

	public void setParteiId(int parteiId) {
		this.parteiId = parteiId;
	}

	public Kandidat readFromResultSet(ResultSet rs) throws SQLException {
		id = rs.getInt("kandidat_id");
		name = rs.getString("kandidat_name");
		vorname = rs.getString("kandidat_vorname");
		parteiId = rs.getInt("partei_id");
		return this;
	}
}
