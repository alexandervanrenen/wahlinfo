package de.tum.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Wahlkreis {

	protected int id;
	protected String name;

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

	public static Wahlkreis readFromResultSet(ResultSet rs) throws SQLException {
		Wahlkreis w = new Wahlkreis();
		w.id = rs.getInt("wahlkreis_id");
		w.name = rs.getString("wahlkreis_name");
		if(w.id == 0)
			return null;
		return w;
	}
}
