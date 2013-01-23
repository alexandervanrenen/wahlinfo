package de.tum.queries.StimmAbgabe;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.tum.sql.ConnectionHelper;
import de.tum.sql.SqlStatements;
import de.tum.sql.SqlStatements.Query;

public class StimmAbgabeDAO {

	public StimmAbgabe addVote(String wahlkreisidStr, String kandidatidStr, String parteiidStr) {
		// Parse input
		StimmAbgabe result = new StimmAbgabe();
		int wahlkreisid, kandidatid, parteiid;
		try {
			wahlkreisid = Integer.parseInt(wahlkreisidStr);
			kandidatid = Integer.parseInt(kandidatidStr);
			parteiid = Integer.parseInt(parteiidStr);
		} catch (NumberFormatException e) {
			result.setErfolg(false);
			result.setFehler("unable to parse input parameters");
			return result;
		}

		// Check if the candidate is represented in the given wahlkreis
		Connection c = null;
		try {
			c = ConnectionHelper.getConnection();
			String sql = SqlStatements.getQuery(Query.Stimmabgabe_Check);
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, wahlkreisid);
			ps.setInt(2, kandidatid);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				result.setErfolg(false);
				result.setFehler("candidate is not represented in this wahlkreis");
				return result;
			}
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		}

		// Insert the new vote
		try {
			String sql = SqlStatements.getQuery(Query.Stimmabgabe_Insert);
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, kandidatid);
			ps.setInt(2, parteiid);
			ps.setInt(3, wahlkreisid);
			ps.executeUpdate();
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		}

		ConnectionHelper.close(c);
		result.setErfolg(true);
		result.setFehler("none 8)");
		return result;
	}

}
