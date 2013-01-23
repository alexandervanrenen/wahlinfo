package de.tum.queries.ParteiData;

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

public class ParteiDataDAO {

	public List<ParteiExtended> findAllParteien() {
		List<ParteiExtended> list = new ArrayList<ParteiExtended>();
		Connection c = null;
		try {
			String sql = SqlStatements.getQuery(Query.FindAllParteien);
			c = ConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next())
				list.add(readPartei(rs));
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return list;
	}

	public ParteiExtended findParteiById(int id) {
		ParteiExtended Partei = null;
		Connection c = null;
		try {
			c = ConnectionHelper.getConnection();
			String sql = SqlStatements.getQuery(Query.FindParteiById);
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				Partei = readPartei(rs);
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return Partei;
	}

	public List<ParteiExtended> findParteiByName(String name) {
		List<ParteiExtended> list = new ArrayList<ParteiExtended>();
		Connection c = null;
		try {
			String sql = SqlStatements.getQuery(Query.FindParteiByName);
			c = ConnectionHelper.getConnection();
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, "%" + name + "%");
			ps.setString(2, "%" + name + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next())
				list.add(readPartei(rs));
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return list;
	}

	private ParteiExtended readPartei(ResultSet rs) throws SQLException {
		ParteiExtended partei = new ParteiExtended();
		partei.readFromResultSet(rs);
		return partei;
	}
}
