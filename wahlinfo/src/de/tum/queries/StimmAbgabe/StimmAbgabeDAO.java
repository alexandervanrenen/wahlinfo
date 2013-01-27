package de.tum.queries.StimmAbgabe;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.tum.sql.ConnectionHelper;
import de.tum.sql.SqlStatements;
import de.tum.sql.SqlStatements.Query;

public class StimmAbgabeDAO {

	public StimmAbgabe addVote(String wahlkreisidStr, String kandidatidStr, String parteiidStr) {
		// Parse input
		StimmAbgabe result = new StimmAbgabe();
		int wahlkreisId, kandidatId, parteiId;
		try {
			wahlkreisId = Integer.parseInt(wahlkreisidStr);
			kandidatId = Integer.parseInt(kandidatidStr);
			parteiId = Integer.parseInt(parteiidStr);
		} catch (NumberFormatException e) {
			result.setErfolg(false);
			result.setFehler("unable to parse input parameters");
			return result;
		}

		// Establish database connection
		Connection c = null;
		try {
			c = ConnectionHelper.getConnection();
		} catch (SQLException e) {
			new RuntimeException(e);
		}

//		// Check if the candidate is represented in the given wahlkreis
//		if (kandidatId != 0) {
//			try {
//				String sql = SqlStatements.getQuery(Query.Stimmabgabe_Check_Candidate);
//				PreparedStatement ps = c.prepareStatement(sql);
//				ps.setInt(1, wahlkreisId);
//				ps.setInt(2, kandidatId);
//				ResultSet rs = ps.executeQuery();
//				if (!rs.next()) {
//					result.setErfolg(false);
//					result.setFehler("candidate is not represented in this wahlkreis");
//					return result;
//				}
//			} catch (SQLException | IOException e) {
//				throw new RuntimeException(e);
//			}
//		}
//
//		// Check if party is represented in this wahlkreis
//		if (parteiId != 0) {
//			try {
//				String sql = SqlStatements.getQuery(Query.Stimmabgabe_Check_Party);
//				PreparedStatement ps = c.prepareStatement(sql);
//				ps.setInt(1, wahlkreisId);
//				ps.setInt(2, parteiId);
//				ResultSet rs = ps.executeQuery();
//				if (!rs.next()) {
//					result.setErfolg(false);
//					result.setFehler("party is not represented in this bundesland");
//					return result;
//				}
//			} catch (SQLException | IOException e) {
//				throw new RuntimeException(e);
//			}
//		}

		// Insert the new vote
		try {
			String sql = SqlStatements.getQuery(Query.Stimmabgabe_Insert);
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, kandidatId);
			ps.setInt(2, parteiId);
			ps.setInt(3, wahlkreisId);
			ps.executeUpdate();
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		}

		// Insert the new vote
		try {
			PreparedStatement ps = c.prepareStatement("update StimmenCountKandidatAggregiert set stimmenAnzahl = stimmenAnzahl + 1 where kandidat_id = ?;");
			ps.setInt(1, kandidatId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		// Insert the new vote
		try {
			PreparedStatement ps = c.prepareStatement("update StimmenCountParteiWahlkreisAggregiert set stimmenAnzahl = stimmenAnzahl + 1 where partei_id = ? and wahlkreis_id = ?;");
			ps.setInt(1, parteiId);
			ps.setInt(2, wahlkreisId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		// Insert the new vote
		try {
			PreparedStatement ps = c.prepareStatement("update StimmenCountParteiBundeslandAggregiert set stimmenAnzahl = stimmenAnzahl + 1 where partei_id = ? and bundesland_id = (select w.bundesland_id from wahlkreis w where w.id = ?);");
			ps.setInt(1, parteiId);
			ps.setInt(2, wahlkreisId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		ConnectionHelper.close(c);
		result.setErfolg(true);
		result.setFehler("none 8)");
		return result;
	}

}
