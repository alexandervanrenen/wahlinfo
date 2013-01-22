package de.tum.wahlinfo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.tum.sql.ConnectionHelper;
import de.tum.sql.SqlStatements;
import de.tum.sql.SqlStatements.Query;

public class StimmzettelDAO {

	public Stimmzettel create(int wahlkreisId) {
		Stimmzettel stimmzettel = new Stimmzettel();

		// Get list with parties
		Connection c = null;
		try {
			// Query
			c = ConnectionHelper.getConnection();
			String sql = SqlStatements.getQuery(Query.StimmzettelParteien);
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, wahlkreisId);
			ResultSet rs = ps.executeQuery();

			// Parse result
			List<Partei> list = new ArrayList<Partei>();
			while (rs.next()) {
				Partei partei = new Partei();
				partei.setId(rs.getInt("partei_id"));
				partei.setName(rs.getString("name"));
				partei.setKurzbezeichnung(rs.getString("kurzbezeichnung"));
				partei.setFarbe(rs.getString("farbe"));
				partei.setAnzahl(rs.getInt("anzahl"));
				list.add(partei);
			}
			stimmzettel.setParteienList(list);
		} catch (SQLException | IOException e) {
			ConnectionHelper.close(c);
			throw new RuntimeException(e);
		}

		// Get list with candidates
		try {
			// Query
			String sql = SqlStatements.getQuery(Query.StimmzettelKandidaten);
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, wahlkreisId);
			ResultSet rs = ps.executeQuery();

			// Parse result
			List<Kandidat> list = new ArrayList<Kandidat>();
			while (rs.next()) {
				Kandidat kandidat = new Kandidat();
				kandidat.setId(rs.getInt("kandidat_id"));
				kandidat.setName(rs.getString("name"));
				kandidat.setVorname(rs.getString("vorname"));
				kandidat.setPartei(rs.getString("partei"));
				kandidat.setKurzbezeichnung(rs.getString("kurzbezeichnung"));
				list.add(kandidat);
			}
			stimmzettel.setKandidatenList(list);
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}

		return stimmzettel;
	}
}
