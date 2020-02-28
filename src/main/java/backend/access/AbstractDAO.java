package backend.access;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import backend.entities.AbstractEntity;


public abstract class AbstractDAO<E extends AbstractEntity> {
	
	private StatementExecuter executer = new StatementExecuter();
	
	protected abstract ArrayList<E> executeResultSet(ResultSet resultSet) throws SQLException;
	protected abstract E returnEntity(ResultSet resultSet) throws SQLException;
	
	protected void doQuery(String sqlQuery) {
		executer.execute(sqlQuery);
	}
	protected boolean checkQuery(String sqlQuery) throws SQLException{
		return executer.check(sqlQuery);
	}
	protected Long getNumber(String sqlQuery) throws SQLException{
		return executer.executeAndGetNumber(sqlQuery);
	}

	protected ArrayList<E> showTable(String sqlQuery) {
		ConnectionManager connectionManager = ConnectionManager.getInstance();
		Connection connection = connectionManager.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlQuery);
			connectionManager.closeConnection();
			return executeResultSet(resultSet);

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		}
		
	}

	protected E getEntity(String sqlQuery){
		ConnectionManager connectionManager = ConnectionManager.getInstance();
		Connection connection = connectionManager.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlQuery);
			return returnEntity(resultSet);

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally{
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}		
			connectionManager.closeConnection();
		}
	}

	

}
