package backend.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

class ConnectionManager {
	private Connection connection;
	private Logger logger;

	private ConnectionManager() {
		logger = Logger.getLogger(ConnectionManager.class.getName());
	}

	private static ConnectionManager instance = null;

	static ConnectionManager getInstance() {
		if (instance == null) {
			instance = new ConnectionManager();
		}
		return instance;
	}

	Connection getConnection() {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			logger.log(Level.INFO, "Драйвер не найден");
			e.printStackTrace();
		}
		try {
			String connectionString = "jdbc:hsqldb:file:mydb/clinic";
			connection = DriverManager.getConnection(connectionString, " ", " ");
			return connection;

		} catch (SQLException e) {
			logger.log(Level.INFO, "Соединение не создано");
			e.printStackTrace();
		}
		return connection;
	}

	void closeConnection() {

		Statement statement;
		try {
			statement = connection.createStatement();
			String sql = "SHUTDOWN";
			statement.execute(sql);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			logger.log(Level.INFO, "Соединение не закрыто");
			e.printStackTrace();
		}
	}
}
