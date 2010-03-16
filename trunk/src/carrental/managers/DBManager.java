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

	public boolean createTable(String tableName, String tableColumns) {
		boolean createdTable = false;
		if (connection != null) {
			Statement statement = null;
			String strCreateTable = "CREATE table APP." + tableName + "(" + tableColumns + ")";
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

	/**
	 * Returns PreparedStatement ready to be given proper values.
	 * insertIntoTable should be used to prepare pattern onto which only
	 * set methods should be applied and then the whole query is triggered
	 * using executeUpdate() method of PreparedStatement.
	 * example:
	 *		// db is a connected instance of DBManager
	 *		PreparedStatement st = db.insertIntoTable("MAN","NAME,SURNAME",2);
	 *		st.clearParameters();
	 *		st.setString(1, "John");
	 *		st.setString(2, "Smith");
	 *		st.executeUpdate();
	 *		ResultSet results = st.getGeneratedKeys()
	 *		if (results.next()) {
	 *			id = results.getInt(1);
	 *		}
	 *
	 *
	 * @param tableName String name of an existing table in the database
	 * @param valueNames String containing new values
	 * @param values
	 * @return PreparedStatement
	 */
	public PreparedStatement insertIntoTable(String tableName, String valueNames, int valuesCount) {
		PreparedStatement st = null;	//prepare new statement for inserting Address into the database
		String strStatement = "INSERT INTO APP." + tableName + "(" + valueNames + ") VALUES (";// + ")";
		for (int i = 0; i < valuesCount; i++) {	// adds question marks at places for later inserting certain values
			if (i > 0) {
				strStatement += ", ";
			}
			strStatement += "?";
		}
		System.out.println("statement is: " + strStatement);	//TODO delete this debug println
		try {
			st = connection.prepareStatement(strStatement,Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException ex) {
			Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		return st;
	}
}
