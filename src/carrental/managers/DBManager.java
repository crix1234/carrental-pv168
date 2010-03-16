package carrental.managers;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pavel Mican
 */
public class DBManager {
	private Connection connection;

	public DBManager() {
		connection = null;
	}

	@Override
	protected void finalize() {
		disconnect();
	}

	/**
	 * connects to the specified database
	 *
	 * @return true if a connection to the specified URL if successfull
	 * @return false if unsuccessfull
	 * @param db_connect_url a database url of the form jdbc:subprotocol:subname
	 * @param db_userid the database user on whose behalf the connection is being made
	 * @param db_password the user's password 
	 */
    public boolean connect(String db_connect_url, String db_userid, String db_password) {
		disconnect();	// disconnect actual connection before connecting
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			Connection newConnection = DriverManager.getConnection(db_connect_url, db_userid, db_password);
			System.out.println("connected");
			connection = newConnection;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Connects to the default CAR_RENTAL database
	 *
	 * @return true if a connection to the default CAR_RENTAL database succeeds
	 * @return false if unsuccessfull
	 */
    public boolean connect() {
		return connect("jdbc:derby://localhost:1527/CAR_RENTAL;create=true", "host", "host");
	}

	/**
	 * Disconnects current database connection;
	 */
	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException ex) {
				Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public boolean createTable(String tableColumns) {
		boolean createdTable = false;
		if (connection != null) {
			Statement statement = null;
			String strCreateTable = "CREATE table APP.ADDRESS (" + tableColumns + ")";
			try {
				statement = connection.createStatement();
				statement.execute(strCreateTable);
				createdTable = true;
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return createdTable;
	}

	public boolean insertIntoTable(Connection conn, String tableName, String tableValues) {
		PreparedStatement st;	//prepare new statement for inserting Address into the database
		try {
			st = conn.prepareStatement("INSERT INTO ");
			st.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}
}
