package de.tum.queries.WahlkreisSieger;

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
				wahlkreisSieger.setWahlkreisname(rs.getString("wahlkreisname"));
				wahlkreisSieger.setSiegerparteierststimme(rs.getString("siegerparteierststimme"));
				wahlkreisSieger.setSiegerparteizweitstimme(rs.getString("siegerparteizweitstimme"));
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
