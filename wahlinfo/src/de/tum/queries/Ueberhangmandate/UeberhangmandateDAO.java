package de.tum.queries.Ueberhangmandate;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.tum.domain.Bundesland;
import de.tum.domain.Partei;
import de.tum.sql.ConnectionHelper;
import de.tum.sql.SqlStatements;
import de.tum.sql.SqlStatements.Query;

public class UeberhangmandateDAO {

	public List<Ueberhangmandate> findAll() {
		List<Ueberhangmandate> list = new ArrayList<Ueberhangmandate>();
		Connection c = null;
		try {
			String sql = SqlStatements.getQuery(Query.Ueberhangmandate);
			c = ConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				Ueberhangmandate ueberhangmandate = new Ueberhangmandate();
				ueberhangmandate.setUeberhangmandate(rs.getInt("ueberhangmandate"));
				ueberhangmandate.setBundesland(new Bundesland().readFromResultSet(rs));
				ueberhangmandate.setPartei(Partei.readFromResultSet(rs));
				list.add(ueberhangmandate);
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
}
