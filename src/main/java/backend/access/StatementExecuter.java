package backend.access;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

class StatementExecuter {
	private Logger logger = Logger.getLogger(AbstractDAO.class.getName());;

	void execute(String sqlQuery) {
		ConnectionManager connectionManager = ConnectionManager.getInstance();
		Statement statement = null;
		try {
			Connection connection = connectionManager.getConnection();
			statement = connection.createStatement();
			statement.executeUpdate(sqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.log(Level.INFO, "Запрос не выполнен");
		} finally {
			connectionManager.closeConnection();
			try {
				statement.close();
			} catch (SQLException e) {
				logger.log(Level.INFO, "Statement не закрыт");
				e.printStackTrace();
			}
		}
	}

	Long executeAndGetNumber(String sqlQuery) throws SQLException {
		ConnectionManager connectionManager = ConnectionManager.getInstance();
		Connection connection = connectionManager.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sqlQuery);
		long number;
		try {
			resultSet.next();
			number = resultSet.getLong(1);
			return number;
		} catch (SQLException e) {
			e.printStackTrace();
			return (long) -1;
		} finally {
			resultSet.close();
			statement.close();
			connectionManager.closeConnection();
		}

	}

	boolean check(String sqlQuery) {
		ConnectionManager connectionManager = ConnectionManager.getInstance();
		Connection connection = connectionManager.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlQuery);
			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				connectionManager.closeConnection();
				resultSet.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
