package de.tum.queries.ParteiData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.tum.domain.Partei;
import de.tum.sql.ConnectionHelper;
import de.tum.sql.SqlStatements;
import de.tum.sql.SqlStatements.Query;

public class ParteiDataDAO {

	public List<Partei> findAllParteien() {
		List<Partei> list = new ArrayList<Partei>();
		Connection c = null;
		try {
			String sql = SqlStatements.getQuery(Query.FindAllParteien);
			c = ConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				Partei p = Partei.readFromResultSet(rs);
				if( p != null)
					list.add(p);
			}
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return list;
	}

	public Partei findParteiById(int id) {
		Partei partei = null;
		Connection c = null;
		try {
			c = ConnectionHelper.getConnection();
			String sql = SqlStatements.getQuery(Query.FindParteiById);
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				partei = Partei.readFromResultSet(rs);
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return partei;
	}

	public List<Partei> findParteiByName(String name) {
		List<Partei> list = new ArrayList<Partei>();
		Connection c = null;
		try {
			String sql = SqlStatements.getQuery(Query.FindParteiByName);
			c = ConnectionHelper.getConnection();
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, "%" + name + "%");
			ps.setString(2, "%" + name + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Partei p = Partei.readFromResultSet(rs);
				if (p != null)
					list.add(p);
			}
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return list;
	}
}
