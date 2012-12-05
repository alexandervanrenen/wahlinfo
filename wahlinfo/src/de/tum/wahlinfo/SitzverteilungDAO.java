package de.tum.wahlinfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.tum.sql.ConnectionHelper;
import de.tum.sql.SqlStatements;
import de.tum.sql.SqlStatements.Query;

public class SitzverteilungDAO {

	public List<Sitzverteilung> get() {
		List<Sitzverteilung> list = new ArrayList<Sitzverteilung>();
		Connection c = null;
		String sql = SqlStatements.getQuery(Query.Sitzverteilung);
		try {
			c = ConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				Sitzverteilung sitzverteilung = new Sitzverteilung();
				sitzverteilung.setName(rs.getString("partei_name"));
				sitzverteilung.setSitze(rs.getInt("sitze"));
				sitzverteilung.setFarbe(rs.getString("partei_farbe"));
				list.add(sitzverteilung);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return list;
	}
}
