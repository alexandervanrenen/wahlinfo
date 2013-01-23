package de.tum.queries.GeographicData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.tum.sql.ConnectionHelper;
import de.tum.sql.SqlStatements;
import de.tum.sql.SqlStatements.Query;

public class StaticDataDAO {

	public Deutschland getDeutschland() {
		Deutschland deutschland = new Deutschland();
		Connection c = null;
		try {
			String sql = SqlStatements.getQuery(Query.CalculateDeutschland);
			c = ConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			if (!rs.next())
				throw new RuntimeException("no data retrieved");
			deutschland.setWahlberechtigte(rs.getInt("wahlberechtigte"));
			deutschland.setWahlberechtigte2005(rs.getInt("wahlberechtigte2005"));
			deutschland.setWaehler(rs.getInt("waehler"));
			deutschland.setWaehler2005(rs.getInt("waehler2005"));
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return deutschland;
	}

	public List<BundeslandExtended> findAllBundeslaender() {
		List<BundeslandExtended> list = new ArrayList<BundeslandExtended>();
		Connection c = null;
		try {
			String sql = SqlStatements.getQuery(Query.FindAllBundeslaender);
			c = ConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				list.add(readBundesLand(rs));
			}
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return list;
	}

	public BundeslandExtended findBundeslandById(int id) {
		BundeslandExtended bundesland = null;
		Connection c = null;
		try {
			c = ConnectionHelper.getConnection();
			String sql = SqlStatements.getQuery(Query.FindBundeslandByName);
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				bundesland = readBundesLand(rs);
			}
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return bundesland;
	}

	public List<BundeslandExtended> findBundeslandByName(String name) {
		List<BundeslandExtended> list = new ArrayList<BundeslandExtended>();
		Connection c = null;
		try {
			String sql = SqlStatements.getQuery(Query.FindBundeslandByName);
			c = ConnectionHelper.getConnection();
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, "%" + name + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(readBundesLand(rs));
			}
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return list;
	}

	public List<WahlkreisExtended> findAllWahlkreise() {
		List<WahlkreisExtended> list = new ArrayList<WahlkreisExtended>();
		Connection c = null;
		try {
			String sql = SqlStatements.getQuery(Query.FindAllWahlkreise);
			c = ConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next())
				list.add(readWahlkreis(rs));
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return list;
	}

	public List<WahlkreisExtended> findAllWahlkreiseOfBundesland(int bundeslandId) {
		List<WahlkreisExtended> list = new ArrayList<WahlkreisExtended>();
		Connection c = null;
		try {
			String sql = SqlStatements.getQuery(Query.FindAllWahlkreiseOfBundesland);
			c = ConnectionHelper.getConnection();
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, bundeslandId);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				list.add(readWahlkreis(rs));
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return list;
	}

	public WahlkreisExtended findWahlkreisById(int wahlkreisId) {
		WahlkreisExtended wahlkreis = null;
		Connection c = null;
		try {
			String sql = SqlStatements.getQuery(Query.FindWahlkreisById);
			c = ConnectionHelper.getConnection();
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, wahlkreisId);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				wahlkreis = readWahlkreis(rs);
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return wahlkreis;
	}

	public List<WahlkreisExtended> findWahlkreisByName(String wahlkreisName) {
		List<WahlkreisExtended> list = new ArrayList<WahlkreisExtended>();
		Connection c = null;
		try {
			String sql = SqlStatements.getQuery(Query.FindWahlkreisByName);
			c = ConnectionHelper.getConnection();
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, "%" + wahlkreisName + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(readWahlkreis(rs));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return list;
	}

	private BundeslandExtended readBundesLand(ResultSet rs) throws SQLException {
		BundeslandExtended bundesland = new BundeslandExtended();
		bundesland.setId(rs.getInt("id"));
		bundesland.setName(rs.getString("name"));
		bundesland.setWahlberechtigte(rs.getInt("wahlberechtigte"));
		bundesland.setWahlberechtigte2005(rs.getInt("wahlberechtigte2005"));
		bundesland.setWaehler(rs.getInt("waehler"));
		bundesland.setWaehler2005(rs.getInt("waehler2005"));
		return bundesland;
	}

	private WahlkreisExtended readWahlkreis(ResultSet rs) throws SQLException {
		WahlkreisExtended wahlkreis = new WahlkreisExtended();
		wahlkreis.setId(rs.getInt("id"));
		wahlkreis.setName(rs.getString("name"));
		wahlkreis.setBundeslandId(rs.getInt("bundesland_id"));
		wahlkreis.setBundeslandName(rs.getString("bundesland_name"));
		wahlkreis.setWahlberechtigte(rs.getInt("wahlberechtigte"));
		wahlkreis.setWahlberechtigte2005(rs.getInt("wahlberechtigte2005"));
		wahlkreis.setWaehler(rs.getInt("waehler"));
		wahlkreis.setWaehler2005(rs.getInt("waehler2005"));
		return wahlkreis;
	}
}
