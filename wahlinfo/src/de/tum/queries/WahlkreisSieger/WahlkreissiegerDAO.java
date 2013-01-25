package de.tum.queries.WahlkreisSieger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.tum.domain.Partei;
import de.tum.domain.Wahlkreis;
import de.tum.sql.ConnectionHelper;
import de.tum.sql.SqlStatements;
import de.tum.sql.SqlStatements.Query;

public class WahlkreissiegerDAO {

	public List<WahlkreisSieger> get() {
		List<WahlkreisSieger> list = new ArrayList<WahlkreisSieger>();
		Connection c = null;
		try {
			String sql = SqlStatements.getQuery(Query.WahlkreisSieger);
			c = ConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				WahlkreisSieger wahlkreisSieger = new WahlkreisSieger();

				Partei erststimme = new Partei();
				erststimme.setId(rs.getInt("partei_id1"));
				erststimme.setName(rs.getString("partei_name1"));
				erststimme.setKurzbezeichnung(rs.getString("partei_kurzbezeichnung1"));
				erststimme.setFarbe(rs.getString("partei_farbe1"));
				wahlkreisSieger.setSiegerParteiErststimme(erststimme);

				Partei zweitstimme = new Partei();
				zweitstimme.setId(rs.getInt("partei_id1"));
				zweitstimme.setName(rs.getString("partei_name1"));
				zweitstimme.setKurzbezeichnung(rs.getString("partei_kurzbezeichnung1"));
				zweitstimme.setFarbe(rs.getString("partei_farbe1"));
				wahlkreisSieger.setSiegerParteiZweitstimme(zweitstimme);

				wahlkreisSieger.setWahlkreis(Wahlkreis.readFromResultSet(rs));
				list.add(wahlkreisSieger);
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
}
