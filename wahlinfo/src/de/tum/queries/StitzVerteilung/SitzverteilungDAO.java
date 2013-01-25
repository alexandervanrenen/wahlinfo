package de.tum.queries.StitzVerteilung;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.tum.domain.Partei;
import de.tum.sql.ConnectionHelper;
import de.tum.sql.SqlStatements;
import de.tum.sql.SqlStatements.Query;

public class SitzverteilungDAO {

	public List<Sitzverteilung> get() {
		// Get data
		List<Sitzverteilung> list = new ArrayList<Sitzverteilung>();
		Connection c = null;
		try {
			String sql = SqlStatements.getQuery(Query.Sitzverteilung);
			c = ConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				Sitzverteilung sitzverteilung = new Sitzverteilung();
				sitzverteilung.setPartei(Partei.readFromResultSet(rs));
				sitzverteilung.setSitze(rs.getInt("sitze"));
				list.add(sitzverteilung);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}

		// Unite CDU and CSU .. stupid politics screwing up my code :(
		Sitzverteilung firstOfThem = null;
		for (Sitzverteilung iter : list) {
			if (iter.getPartei().getKurzbezeichnung().contentEquals("CDU") || iter.getPartei().getKurzbezeichnung().contentEquals("CSU")) {
				if (firstOfThem == null) {
					// Found the first: Remember the first of the two parties
					firstOfThem = iter;
					firstOfThem.getPartei().setKurzbezeichnung("CDU/CSU");
					firstOfThem.getPartei().setName("Die Union");
					continue;
				} else {
					// Found the second: Add value to the first and remove
					firstOfThem.setSitze(firstOfThem.getSitze() + iter.getSitze());
					list.remove(iter);
					break;
				}
			}
		}
		return list;
	}
}
