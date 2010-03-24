package carrental.managers;

import carrental.entities.Address;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Pavel Mican
 */
public class AddressManagerImpl implements AddressManager {
	public static final int MAXLENGTH_STREET = 40;
	public static final int MAXLENGTH_TOWN = 40;
	public static final int MAXLENGTH_STATE = 40;
	public static final int MAXLENGTH_ZIPCODE = 20;

	/**
	 * Creates new <code>Address</code> and saves it into the database
	 * 
	 * @param houseNumber
	 * @param street String containing street name. Longer than database capacity names will be reduced
	 * @param town String containing town name. Longer than database capacity names will be reduced
	 * @param state String containing state name. Longer than database capacity names will be reduced
	 * @param zipcode String containing zipcode. Longer than database capacity names will be reduced
	 * @return Address generated <code>Address</code> reflecting input parameters;
	 *         null if <code>Address</code> generating was unsuccessfull
	 * @throws AddressManagerException if address creating was unsuccessfull
	 */
	public Address createNewAddress(int houseNumber, String street, String town, String state, String zipcode) throws AddressManagerException {
		//initialize db connection
		street = DBManager.reduceLongString(street,MAXLENGTH_STREET);
		town = DBManager.reduceLongString(town,MAXLENGTH_TOWN);
		state = DBManager.reduceLongString(state,MAXLENGTH_STATE);
		zipcode = DBManager.reduceLongString(zipcode,MAXLENGTH_ZIPCODE);
		DBManager db = new DBManager();
		Address addr = null;
		if (db.connect()) { //connecting to the database was successfull
			if (createTable(db)) { //TODO remove table creation on createNewAddress call and replace it by database initialisation at program start up
				int id = -1;
				PreparedStatement st = db.getInsertIntoTableStatement("ADDRESS", "houseNumber", "street", "town", "state", "zipcode");
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
				} catch (SQLException ex) {
					throw new AddressManagerException(ex);
				}
				try {
					addr = new Address(id, houseNumber, street, town, state, zipcode);
				} catch (IllegalArgumentException ex) {
					//TODO sql Address insertion succeeded, but class creation doesnt so it's necessarry to remove created row from the database again;
					throw new AddressManagerException(ex);
				}
			}
			db.disconnect();
		}
		return addr;
	}

	/**
	 * Calls createNewAddress with values gathered from given parameter <code>Address</code>
	 * @param address <code>Address</code> containing values to be set into the database
	 * @return Address generated <code>Address</code> reflecting input parameters;
	 *         null if <code>Address</code> generating was unsuccessfull
	 * @throws AddressManagerException if address creating was unsuccessfull
	 */
	public Address createNewAddress(Address address) throws AddressManagerException {
		if (address != null) {
			return createNewAddress(address.getHouseNumber(), address.getStreet(), address.getTown(), address.getState(), address.getZipcode());
		} else {
			throw new AddressManagerException("createNewAddress argument should be an existing instance of Address, not null.");
		}
	}

	/**
	 * deletes given address from the database; Only address ID is searched
	 * @param address the <code>Address</code> that should be removed from the database.
	 * @return Address representation of the deleted <code>Address</code>
	 * @throws AddressManagerException on address deletion failure;
	 *         IllegalArgumentException if argument is null or address id < 1
	 */
	public Address deleteAddress(Address address) throws AddressManagerException, IllegalArgumentException {
		if (address != null) {
			return deleteAddress(address.getId());
		} else {
			throw new IllegalArgumentException("Can't delete address that is null");
		}
	}


	/**
	 * deletes <code>Address</code> with the given id from the database
	 * @param id the <code>id</code> of the <code>Address</code> record in the database
	 *           that should be removed
	 * @return Address representation of the deleted <code>Address</code>
	 * @throws AddressManagerException on address deletion failure;
	 *         IllegalArgumentException if argument is null or address id < 1
	 */
	public Address deleteAddress(int id) throws AddressManagerException, IllegalArgumentException {
		if (id > 0) {
			Address deletedAddress = findAddressByID(id);
			if (deletedAddress != null) {
				//initialize db connection
				DBManager db = new DBManager();
				if (db.connect()) { //connecting to the database was successfull
					try {
						if (db.deleteRow("ADDRESS", id) == 0) {
							return null;	// no address was actually deleted
						}
					} catch (SQLException ex) {
						throw new AddressManagerException(ex);
					}
				}
			}
			return deletedAddress;
		} else {
			throw new IllegalArgumentException("Address id should be positive integer!");
		}
	}

	/**
	 * Edits existing <code>Address</code> accessed in the database by ID
	 * @param newAddress <code>Address</code> that should be inserted into the database.
	 * @throws CustomerManagerException	on SQL queries failure
	 * @throws IllegalArgumentException on failure accessing given <code>Address</code>'s <code>id</code> in the database
	 *                                  or <code>id</code> < 1
	 */
	public void editAddress(Address newAddress) throws AddressManagerException, IllegalArgumentException {
		if (newAddress.getId() < 1) {
			throw new IllegalArgumentException("Can't find Address with id < 1");
		}
		//initialize db connection
		DBManager db = new DBManager();
		if (db.connect()) { //connecting to the database was successfull
			if (db.tableExists("ADDRESS")) {
				try {
					PreparedStatement st = db.getUpdateTableStatement("ADDRESS", "houseNumber","street","town","state","zipcode");
					st.setInt(1, newAddress.getHouseNumber());
					st.setString(2, newAddress.getStreet());
					st.setString(3, newAddress.getTown());
					st.setString(4, newAddress.getState());
					st.setString(5, newAddress.getZipcode());
					st.setInt(6, newAddress.getId()); //sets ID value into the statement condition (WHERE ID = ?)
					int updates = st.executeUpdate();
					if (updates < 1) {
						throw new IllegalArgumentException("Given ID was not found during update");
					}
				} catch (SQLException ex) {
					throw new AddressManagerException(ex);
				}
				return;
			}
			throw new AddressManagerException("Could not find ADDRESS table.");
		}
		throw new AddressManagerException("Database connection was not reached.");
	}

	/**
	 * Finds <code>Addres</code> with a given <code>id</code> in the database
	 * @param id <code>Address</code> <code>id</code> in the database
	 * @return <code>Address</code> with a given <code>id</code> or <code>null</code>
	 * @throws AddressManagerException on SQL failure
	 * @throws IllegalArgumentException on <code>id</code> out of range (if <code>id</code> < 1)
	 */
	public Address findAddressByID(int id) throws AddressManagerException, IllegalArgumentException {
		if (id < 1) {
			throw new IllegalArgumentException("Can't find Address with id < 1");
		}
		//initialize db connection
		DBManager db = new DBManager();
		Address addr = null;
		if (db.connect()) { //connecting to the database was successfull
			if (db.tableExists("ADDRESS")) {
				PreparedStatement st = db.getSelectFromTableStatement("ADDRESS", "*", "id = "+id);
				try {
					ResultSet rs = st.executeQuery();
					ArrayList<Address> queryResult = getAddressFromResultSet(rs);
					if (queryResult.size() > 0) {
						addr = queryResult.get(0);
					}
				} catch (SQLException ex) {
					throw new AddressManagerException(ex);
				}
			}
		}
		return addr;
	}

	/**
	 * Finds all <code>Address</code>es in the database and returns the <code>List</code>
	 * containing instances of found <code>Address</code>es.
	 * @return ArrayList<Address> containing all the found values
	 * @throws AddressManagerException on SQL query failure
	 */
	public ArrayList<Address> findAllAddresses() throws AddressManagerException {
		//initialize db connection
		DBManager db = new DBManager();
		ArrayList<Address> addr = new ArrayList<Address>();
		if (db.connect()) { //connecting to the database was successfull
			if (db.tableExists("ADDRESS")) {
				PreparedStatement st = db.getSelectFromTableStatement("ADDRESS", "*");
				try {
					ResultSet rs = st.executeQuery();
					addr.addAll(getAddressFromResultSet(rs));
				} catch (SQLException ex) {
					throw new AddressManagerException(ex);
				}
			}
			db.disconnect();
		}
		return addr;
	}

	/**
	 * creates new table if it's not already in the database
	 * 
	 * @param db <code>DBManager</code> that handles db connection and table creation
	 * @return true if successfull creation;
	 *         false if the database respond was unsuccessfull for some reason
	 */
	private static final boolean createTable(DBManager db) {
		String columns =	"ID				INTEGER NOT NULL" +
							"				PRIMARY KEY GENERATED ALWAYS AS IDENTITY" +
							"				(START WITH 1, INCREMENT BY 1)," +
							"houseNumber	INTEGER," +
							"street			VARCHAR(" + MAXLENGTH_STREET + ")," +
							"town			VARCHAR(" + MAXLENGTH_TOWN + ")," +
							"state			VARCHAR(" + MAXLENGTH_STATE + ")," +
							"zipcode		VARCHAR(" + MAXLENGTH_ZIPCODE + ")";
		return db.createTable("ADDRESS",columns);
	}

	/**
	 * Handles creating <code>Address</code> instances from database's <code>ResultSet</code>
	 * 
	 * @param rs <code>ResultSet</code> retreaved from the previous database query
	 * @return ArrayList<Address> of all retreaved addresses
	 * @throws SQLException if reading arguments fails
	 */
	private static final ArrayList<Address> getAddressFromResultSet(ResultSet rs) throws SQLException {
		ArrayList<Address> addresses = new ArrayList<Address>();
		Address newAddress;
		while(rs.next()) {
			newAddress = new Address(rs.getInt("ID"),
					rs.getInt("houseNumber"),
					rs.getString("street"),
					rs.getString("town"),
					rs.getString("state"),
					rs.getString("zipcode"));
			addresses.add(newAddress);
		}
		return addresses;
	}
}
