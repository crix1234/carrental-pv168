package carrental.managers;

import carrental.entities.Address;
import java.util.Collection;

/**
 *
 * @author Pavel Mican
 */
public class AddressManagerImpl implements AddressManager {

	public Address createNewAddress(int id, int houseNumber, String street, String town, String state, String zipcode) {
		//initialize db connection
		DBManager db = new DBManager();
		Address addr = null;
		if (db.connect()) { //connecting to the database was successfull
			if (createTable(db)) { //create the table to put addresses in - true if success
				//TODO insert new Address into the address database
				addr = new Address(id, houseNumber, street, town, state, zipcode);
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
		return db.createTable(columns);
	}
}
