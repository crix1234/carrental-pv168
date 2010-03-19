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
			connection = newConnection;
			return true;
		} catch (Exception ex) {
			Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
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
				Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return createdTable;
	}

	/**
	 * Drops existing <code>tableName</code> table in the database
	 * @param tableName name of the table to be dropped
	 * @return true on success
	 * @return false on sql communication failure
	 */
	public boolean dropTable(String tableName) {
		if (tableExists(tableName)) {
			if (connection != null) {
				try {
					Statement statement = connection.createStatement();
					statement.execute("DROP TABLE APP."+tableName);
				} catch (SQLException ex) {
					ex.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Gets <code>PreparedStatement</code> ready to be assigned values
	 *
	 * @param tableName	name of the table where the values should be changed
	 * @param valueId id of the row in the table, which should be changed
	 * @param valueNames variable number of String arguments containing the names of certain Table values to be changed
	 * @return PreparedStatement if table update was successfull
	 * @throws IllegalArgumentException if the given ID is out of range or no equal id is found
	 */
	public PreparedStatement getUpdateTableStatement(String tableName, String... valueNames) throws IllegalArgumentException, SQLException {
		PreparedStatement st = null;
		if (tableExists(tableName)) {
			if (connection != null) {
				String strStatement = "UPDATE APP." + tableName + " SET ";
				for (int i = 0; i < valueNames.length; i++) {
					if (i == 0) {
						strStatement += valueNames[i] + " = ?";	//first value doesn't need to be separated by comma
					} else {
						strStatement += ", " + valueNames[i] + " = ?";
					}
				}
				strStatement += " WHERE ID = ?";
				st = connection.prepareStatement(strStatement);
			}
		}
		return st;
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
	 * @param valueNames custom amount of <code>String</code> arguments containing
	 *                   names of values that are to be changed
	 * @return PreparedStatement Statement used for database calling
	 */
	public PreparedStatement getInsertIntoTableStatement(String tableName, String ... valueNames) {
		PreparedStatement st = null;	//prepare new statement for inserting Address into the database
		if (connection != null) {
			String strStatement = "INSERT INTO APP." + tableName + "(";
			for (int i = 0; i < valueNames.length; i++) {	// adds value names that are to be changed
				if (i > 0) {
					strStatement += ", ";
				}
				strStatement += valueNames[i];
			}
			 strStatement += ") VALUES (";
			for (int i = 0; i < valueNames.length; i++) {	// adds question marks at places for later inserting certain values
				if (i > 0) {
					strStatement += ", ";
				}
				strStatement += "?";
			}
			strStatement += ")";
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

	/**
	 * reduces lenght of the given <code>String</code> to the maximum
	 * of <code>maxLength</code> length.
	 * @param value string to be checked for being too long
	 * @param maxLength lenght of a given string
	 * @return String reduced string to the given <maxLength> length
	 * @return null if maxLength < 0
	 */
	public static final String reduceLongString(String value, int maxLength) {
		if (maxLength < 0) {
			return null;
		}
		String strNew = value;
		if (value != null) {
			if (value.length() > maxLength) {
				strNew = value.substring(0, maxLength);
			}
		}
		return strNew;
	}
}
