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
			// Do monetdbs job and find find all parties in order to join them
			List<Partei> parteien = new ArrayList<>();
			{
				String sql = SqlStatements.getQuery(Query.FindAllParteien);
				c = ConnectionHelper.getConnection();
				Statement s = c.createStatement();
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) {
					Partei p = Partei.readFromResultSet(rs);
					if (p != null)
						parteien.add(p);
				}
			}

			// Do the real job
			String sql = SqlStatements.getQuery(Query.WahlkreisSieger);
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				// Read wahlkreis
				WahlkreisSieger wahlkreisSieger = new WahlkreisSieger();
				wahlkreisSieger.setWahlkreis(Wahlkreis.readFromResultSet(rs));

				// Visit party one
				{
					int id = rs.getInt("partei_id1");
					for (Partei partei : parteien)
						if (partei.getId() == id) {
							wahlkreisSieger.setSiegerParteiErststimme(partei);
							break;
						}
				}

				// Visit party two
				{
					int id = rs.getInt("partei_id2");
					for (Partei partei : parteien)
						if (partei.getId() == id) {
							wahlkreisSieger.setSiegerParteiZweitstimme(partei);
							break;
						}
				}
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
