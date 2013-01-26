package de.tum.queries.KnappsteSieger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.tum.domain.Kandidat;
import de.tum.domain.Partei;
import de.tum.domain.Wahlkreis;
import de.tum.sql.ConnectionHelper;
import de.tum.sql.SqlStatements;
import de.tum.sql.SqlStatements.Query;

public class KnappsteSiegerDAO {

	public KnappsteSieger get() {
		// Get list with parties
		KnappsteSieger result = new KnappsteSieger();
		Connection c = null;
		try {
			String sql = SqlStatements.getQuery(Query.KnappsteSieger);
			c = ConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				KnappsteSiegerTeilnehmer entry = new KnappsteSiegerTeilnehmer();
				Wahlkreis wahlkreis = Wahlkreis.readFromResultSet(rs);
				entry.setWahlkreis(wahlkreis);
				Partei partei = Partei.readFromResultSet(rs);
				entry.setPartei(partei);
				Kandidat kandidat = Kandidat.readFromResultSet(rs);
				entry.setKandidat(kandidat);
				entry.setStimmenVorsprung(rs.getInt("stimmenvorsprung"));
				boolean isWinner = rs.getBoolean("istGewinner");
				if (isWinner)
					result.getGewinner().add(entry);
				else
					result.getVerlierer().add(entry);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}

		return result;
	}
}
