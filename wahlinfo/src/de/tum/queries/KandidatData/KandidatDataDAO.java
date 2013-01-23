package de.tum.queries.KandidatData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

public class KandidatDataDAO {

	public List<KandidatExtended> findAllKandidaten() {
		List<KandidatExtended> list = new ArrayList<KandidatExtended>();
		Connection c = null;
		try {
			String sql = SqlStatements.getQuery(Query.FindAllKandidaten);
			c = ConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next())
				list.add(readKandidat(rs));
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return list;
	}

	public KandidatExtended findKandidatById(int id) {
		KandidatExtended kandidat = null;
		Connection c = null;
		try {
			c = ConnectionHelper.getConnection();
			String sql = SqlStatements.getQuery(Query.FindKandidatById);
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				kandidat = readKandidat(rs);
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return kandidat;
	}

	public List<KandidatExtended> findKandidatByName(String name) {
		List<KandidatExtended> list = new ArrayList<KandidatExtended>();
		Connection c = null;
		try {
			String sql = SqlStatements.getQuery(Query.FindKandidatByName);
			c = ConnectionHelper.getConnection();
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, "%" + name + "%");
			ps.setString(2, "%" + name + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				list.add(readKandidat(rs));
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return list;
	}

	private KandidatExtended readKandidat(ResultSet rs) throws SQLException {
		KandidatExtended kandidat = new KandidatExtended();
		kandidat.readFromResultSet(rs);
		kandidat.setPartei(new Partei().readFromResultSet(rs));
		kandidat.setWahlkreis(new Wahlkreis().readFromResultSet(rs));
		kandidat.setGeburtsjahr(rs.getInt("kandidat_geburtsjahr"));
		return kandidat;
	}
}
