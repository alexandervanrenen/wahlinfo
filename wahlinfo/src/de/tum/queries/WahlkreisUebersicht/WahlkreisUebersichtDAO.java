package de.tum.queries.WahlkreisUebersicht;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.tum.domain.Kandidat;
import de.tum.domain.Partei;
import de.tum.domain.Wahlkreis;
import de.tum.sql.ConnectionHelper;
import de.tum.sql.SqlStatements;

public class WahlkreisUebersichtDAO {

	public WahlkreisUebersicht findById(int id) {
		WahlkreisUebersicht wahlkreisUebersicht = new WahlkreisUebersicht();
		Connection c = null;
		try {
			c = ConnectionHelper.getConnection();

			// first fill in the winner kandidat
			{
				String sql = SqlStatements.getQuery(SqlStatements.Query.WahlkreisUebersicht1);
				PreparedStatement ps = c.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					wahlkreisUebersicht.setGewinnerKandidat(Kandidat.readFromResultSet(rs));
					wahlkreisUebersicht.setGewinnerPartei(Partei.readFromResultSet(rs));
				} else
					throw new RuntimeException("wahlkreis ohne gewinner");
			}

			// second fill in the election participation ration
			{
				String sql = SqlStatements.getQuery(SqlStatements.Query.WahlkreisUebersicht2);
				PreparedStatement ps = c.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					wahlkreisUebersicht.setWahlBeteiligung(rs.getFloat("wahlbeteiligung"));
					wahlkreisUebersicht.setWahlBeteiligungVorjahr(rs.getFloat("wahlbeteiligungVorjahr"));
				}
			}

			// third fill in the results of each party in this wahlkreis
			{
				String sql = SqlStatements.getQuery(SqlStatements.Query.WahlkreisUebersicht3);
				PreparedStatement ps = c.prepareStatement(sql);
				ps.setInt(1, id);
				ps.setInt(2, id);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					ParteiErgebnis parteiErgebnis = new ParteiErgebnis();
					parteiErgebnis.setPartei(Partei.readFromResultSet(rs));
					parteiErgebnis.setStimmenAnteil(rs.getFloat("anteilStimmen"));
					parteiErgebnis.setStimmenAnteilVorjahr(rs.getFloat("anteilStimmenVorjahr"));
					parteiErgebnis.setStimmenAnzahl(rs.getInt("anzahlStimmen"));
					parteiErgebnis.setStimmenAnzahlVorjahr(rs.getInt("anzahlStimmenVorjahr"));
					wahlkreisUebersicht.addParteiergebnis(parteiErgebnis);
				}
			}

			// fourth fill in wahlkreis details
			{
				String sql = SqlStatements.getQuery(SqlStatements.Query.FindWahlkreisById);
				PreparedStatement ps = c.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				if (rs.next())
					wahlkreisUebersicht.setWahlkreis(Wahlkreis.readFromResultSet(rs));
				else
					throw new RuntimeException("unknown wahlkreis");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return wahlkreisUebersicht;
	}
}
