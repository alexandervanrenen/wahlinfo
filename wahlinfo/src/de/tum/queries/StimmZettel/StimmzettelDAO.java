package de.tum.queries.StimmZettel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.tum.domain.Kandidat;
import de.tum.domain.Partei;
import de.tum.sql.ConnectionHelper;
import de.tum.sql.SqlStatements;
import de.tum.sql.SqlStatements.Query;

public class StimmzettelDAO {

	public Stimmzettel create(int wahlkreisId) {
		// Get list with parties
		List<StimmzettelEntry> liste = new ArrayList<StimmzettelEntry>();
		Connection c = null;
		try {
			// Query
			c = ConnectionHelper.getConnection();
			String sql = SqlStatements.getQuery(Query.StimmzettelParteien);
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, wahlkreisId);
			ps.setInt(2, wahlkreisId);
			ResultSet rs = ps.executeQuery();

			// Parse result
			while (rs.next()) {
				StimmzettelEntry entry = new StimmzettelEntry();
				Partei partei = new Partei().readFromResultSet(rs);
				entry.setPartei(partei);
				Kandidat kandidat = new Kandidat().readFromResultSet(rs);
				entry.setKandidat(kandidat);
				liste.add(entry);
			}
		} catch (SQLException | IOException e) {
			ConnectionHelper.close(c);
			throw new RuntimeException(e);
		}

		Stimmzettel stimmzettel = new Stimmzettel();
		stimmzettel.setWahlkreisId(wahlkreisId);
		stimmzettel.setListe(liste);
		return stimmzettel;
	}
}
