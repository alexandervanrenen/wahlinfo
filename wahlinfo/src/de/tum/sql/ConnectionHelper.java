package de.tum.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionHelper {

	private String url, user, password;
	private static ConnectionHelper instance;

	private ConnectionHelper() {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("de.tum.sql.wahlinfo");
			Class.forName(bundle.getString("jdbc.driver"));
			url = bundle.getString("jdbc.url");
			user = bundle.getString("jdbc.user");
			password = bundle.getString("jdbc.password");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Connection getConnection() throws SQLException {
		if (instance == null) {
			instance = new ConnectionHelper();
		}
		try {
			try {
				Class.forName("nl.cwi.monetdb.jdbc.MonetDriver");
			} catch (ClassNotFoundException e) {
				System.out.println("cna not find fucking driver");
				e.printStackTrace();
			}

			return DriverManager.getConnection(instance.url, instance.user, instance.password);
		} catch (SQLException e) {
			throw e;
		}
	}

	public static void close(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
