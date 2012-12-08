package de.tum.wahlinfo;

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


public class BundeslandDAO {

	public List<Bundesland> findAll() {
		List<Bundesland> list = new ArrayList<Bundesland>();
		Connection c = null;
		try {
			String sql = SqlStatements.getQuery(Query.Bundeslaender);
			c = ConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				list.add(processRow(rs));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return list;
	}
	
	public List<Bundesland> findbyName(String name) {
		List<Bundesland> list = new ArrayList<Bundesland>();
        Connection c = null;
    	String sql = "SELECT * FROM bundesland " +
			"WHERE name LIKE ? ";
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(processRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return list;
	}
	
	public Bundesland findbyId(int id) {
		String sql = "SELECT * FROM bundesland WHERE id = ?";
		Bundesland bundesland = null;
        Connection c = null;
        try {
            c = ConnectionHelper.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
            	bundesland = processRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return bundesland;
	}

	protected Bundesland processRow(ResultSet rs) throws SQLException {
		Bundesland bundesland = new Bundesland();
		bundesland.setId(rs.getInt("id"));
		bundesland.setName(rs.getString("name"));
		return bundesland;
	}
	
}
