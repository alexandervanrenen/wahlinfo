package de.tum.queries.BundestagMitglieder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.tum.sql.ConnectionHelper;
import de.tum.sql.SqlStatements;
import de.tum.sql.SqlStatements.Query;

public class BundestagMitgliederDAO {

	public List<BundestagMitglied> findAll() {
		List<BundestagMitglied> list = new ArrayList<BundestagMitglied>();
		Connection c = null;
		try {
			String sql = SqlStatements.getQuery(Query.BundestagMitglieder);
			c = ConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				list.add(processRow(rs));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return list;
	}

	protected BundestagMitglied processRow(ResultSet rs) throws SQLException {
		BundestagMitglied bundesland = new BundestagMitglied();
		bundesland.setVorname(rs.getString("vorname"));
		bundesland.setName(rs.getString("name"));
		bundesland.setPartei(rs.getString("partei"));
		bundesland.setWahlkreis(rs.getString("wahlkreis"));
		return bundesland;
	}
}
