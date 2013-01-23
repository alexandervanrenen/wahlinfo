package de.tum.queries.WahlkreisUebersicht;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.tum.sql.ConnectionHelper;
import de.tum.sql.SqlStatements;

public class WahlkreisUebersichtDAO {

	public WahlkreisUebersicht findById(int id) {
		WahlkreisUebersicht wahlkreisUebersicht = new WahlkreisUebersicht();
        Connection c = null;
        try {
        	c = ConnectionHelper.getConnection();
        	
        	// first fill in the winner
        	{
	    		String sql = SqlStatements.getQuery(SqlStatements.Query.WahlkreisUebersicht1);
	            PreparedStatement ps = c.prepareStatement(sql);
	            ps.setInt(1, id);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	        		wahlkreisUebersicht.setKandidatenId(rs.getInt("kandidatenId"));
	        		wahlkreisUebersicht.setKandidatenName(rs.getString("kandidatenName"));
	        		wahlkreisUebersicht.setKandidatenVorname(rs.getString("kandidatenVorname"));
	            }
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
	            	parteiErgebnis.setPartei(rs.getString("partei"));
	            	parteiErgebnis.setStimmenAnteil(rs.getFloat("anteilStimmen"));
	            	parteiErgebnis.setStimmenAnteilVorjahr(rs.getFloat("anteilStimmenVorjahr"));
	            	parteiErgebnis.setStimmenAnzahl(rs.getInt("anzahlStimmen"));
	            	parteiErgebnis.setStimmenAnzahlVorjahr(rs.getInt("anzahlStimmenVorjahr"));
	            	wahlkreisUebersicht.addParteiergebnis(parteiErgebnis);
	            }
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
