package carrental.managers;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pavel Mican
 */

//TODO most (if not all) of the methods should be throwing IllegalArgumentException on null String arguments
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

	/**
	 * Creates new table of a given name if it's not already created.
	 * @param tableName name of a new table
	 * @param tableColumns comma separated string containing names of table columns
	 * @return true if table creation succeed
	 * @return false if table already exists or sql query fails
	 */
	public boolean createTable(String tableName, String tableColumns) {
		if (tableExists(tableName)) {
			return true;
		}
		boolean createdTable = false;
		if (connection != null) {
			Statement statement = null;
			String strCreateTable = "CREATE table APP." + tableName + "(" + tableColumns + ")";
			try {
				statement = connection.createStatement();
				statement.execute(strCreateTable);
				createdTable = true;
			} catch (SQLException ex) {	//WARNING this exception should be rather thrown by this method, but this should do (for simplicity...)
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
	public PreparedStatement getInsertIntoTableStatement(String tableName, String valueNames, int valuesCount) {
		PreparedStatement st = null;	//prepare new statement for inserting Address into the database
		if (connection != null) {
			String strStatement = "INSERT INTO APP." + tableName + "(" + valueNames + ") VALUES (";// + ")";
			for (int i = 0; i < valuesCount; i++) {	// adds question marks at places for later inserting certain values
				if (i > 0) {
					strStatement += ", ";
				}
				strStatement += "?";
			}
			strStatement += ")";
			System.out.println("insert statement is: " + strStatement);	//TODO delete this debug println
			try {
				st = connection.prepareStatement(strStatement,Statement.RETURN_GENERATED_KEYS);
			} catch (SQLException ex) {
				Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return st;
	}

	/**
	 * Prepares query statement for selection without conditions
	 *
	 * @param tableName table in database in which you want to search
	 * @param values columns from table that are supposed to be gathered
	 * @return PreparedStatement if query is successfull
	 * @return null if query fails
	 */
	public PreparedStatement getSelectFromTableStatement(String tableName, String values) {
		return getSelectFromTableStatement(tableName,values,null);
	}

	/**
	 * Prepares query statement for selection with conditions
	 *
	 * @param tableName table in database in which you want to search
	 * @param values columns from table that are supposed to be gathered
	 * @param conditions String containing selecting conditions in SQL query language or null if no conditions are needed
	 * @return PreparedStatement if query is successfull
	 * @return null if query fails
	 */
	public PreparedStatement getSelectFromTableStatement(String tableName, String values, String conditions) {
		PreparedStatement st = null;	//prepare new statement for inserting Address into the database
		if (connection != null) {
			String strStatement = "SELECT " + values + " FROM APP." + tableName;
			if (conditions != null) {
				strStatement += " WHERE " + conditions;
			}
			System.out.println("select statement is: " + strStatement);	//TODO delete this debug println
			try {
				st = connection.prepareStatement(strStatement);
			} catch (SQLException ex) {
				Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return st;
	}

	/**
	 * Checks whether the table of a given name already exists in the database
	 * @param tableName name to be checked for existence
	 * @return true if given <code>tableName</code>
	 */
	public boolean tableExists(String tableName) {
		if (connection != null) {
			try {
				DatabaseMetaData dbm = connection.getMetaData();
				ResultSet tables = dbm.getTables(null, null, tableName, null);
				if (tables.next()) {
					return true;
				}
			} catch (SQLException ex) {
				Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return false;
	}
}
