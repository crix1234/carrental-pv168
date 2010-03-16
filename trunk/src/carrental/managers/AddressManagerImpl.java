package carrental.managers;

import carrental.entities.Address;
import java.util.Collection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author Pavel Mican
 */
public class AddressManagerImpl implements AddressManager {

	/**
	 * Creates new <code>Address</code> and saves it into the database
	 * 
	 * @param houseNumber
	 * @param street
	 * @param town
	 * @param state
	 * @param zipcode
	 * @return Address generated <code>Address</code> reflecting input parameters
	 * @return null if <code>Address</code> generating was unsuccessfull
	 */
	public Address createNewAddress(int houseNumber, String street, String town, String state, String zipcode) {
		//initialize db connection
		DBManager db = new DBManager();
		Address addr = null;
		if (db.connect()) { //connecting to the database was successfull
			if (createTable(db)) { //create the table to put addresses in - true if success
				int id = -1;
				PreparedStatement st = db.getInsertIntoTableStatement("ADDRESS", "houseNumber, street, town, state, zipcode", 5);
				try {
					st.clearParameters();
					st.setInt(1, houseNumber);
					st.setString(2, street);
					st.setString(3, town);
					st.setString(4, state);
					st.setString(5, zipcode);
					st.executeUpdate();
					ResultSet results = st.getGeneratedKeys();
					if (results.next()) {
						id = results.getInt(1);
					}
					addr = new Address(id, houseNumber, street, town, state, zipcode);
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			db.disconnect();
		}
		return addr;
	}

	public void editAddress(Address address) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Address findAddressByID(int id) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Collection<Address> findAllAddresses() {
		//initialize db connection
		DBManager db = new DBManager();
		Collection<Address> addr = null;
		if (db.connect()) { //connecting to the database was successfull
			if (db.tableExists("ADDRESS")) {
				PreparedStatement st = db.getSelectFromTableStatement("ADDRESS", "*");
				try {
					ResultSet rs = st.executeQuery();
					while(rs.next()) {
						System.out.println("id=" + rs.getInt("id")
								+ " houseNumber=" + rs.getInt("houseNumber")
								+ " street=" + rs.getString("street")
								+ " town=" + rs.getString("town")
								+ " state=" + rs.getString("state")
								+ " zipcode=" + rs.getString("zipcode") );
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}

			}
		}
		return addr;
	}

	/**
	 * creates new table if it's not already in the database
	 * 
	 * @param db <code>DBManager</code> that handles db connection and table creation
	 * @return true if successfull creation
	 * @return false if the database respond was unsuccessfull for some reason
	 */
	private static boolean createTable(DBManager db) {
		String columns =	"ID				INTEGER NOT NULL" +
							"				PRIMARY KEY GENERATED ALWAYS AS IDENTITY" +
							"				(START WITH 1, INCREMENT BY 1)," +
							"houseNumber	INTEGER," +
							"street			VARCHAR(40)," +
							"town			VARCHAR(40)," +
							"state			VARCHAR(40)," +
							"zipcode		VARCHAR(20)";
		return db.createTable("ADDRESS",columns);
	}
}
