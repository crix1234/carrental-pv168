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

	public Address createNewAddress(int houseNumber, String street, String town, String state, String zipcode) {
		//initialize db connection
		DBManager db = new DBManager();
		Address addr = null;
		if (db.connect()) { //connecting to the database was successfull
			if (createTable(db)) { //create the table to put addresses in - true if success
				int id = -1;
				PreparedStatement st = db.insertIntoTable("ADDRESS", "houseNumber, street, town, state, zipcode", 5);
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
		throw new UnsupportedOperationException("Not supported yet.");
	}

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
