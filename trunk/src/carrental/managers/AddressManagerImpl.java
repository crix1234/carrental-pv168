package carrental.managers;

import carrental.entities.Address;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.omg.CORBA.PRIVATE_MEMBER;

/**
 *
 * @author Pavel Mican
 */
public class AddressManagerImpl implements AddressManager {

	/**
	 * Creates new <code>Address</code> and saves it into the database
	 * 
	 * @param houseNumber
	 * @param street String containing street name. MAX 40 chars - longer names will be reduced
	 * @param town String containing town name. MAX 40 chars - longer names will be reduced
	 * @param state String containing state name. MAX 40 chars - longer names will be reduced
	 * @param zipcode String containing zipcode. MAX 20 chars - longer names will be reduced
	 * @return Address generated <code>Address</code> reflecting input parameters
	 * @return null if <code>Address</code> generating was unsuccessfull
	 */
	public Address createNewAddress(int houseNumber, String street, String town, String state, String zipcode) throws AddressManagerException {
		//initialize db connection
		street = reduceLongString(street,40);
		town = reduceLongString(town,40);
		state = reduceLongString(state,40);
		zipcode = reduceLongString(zipcode,20);
		DBManager db = new DBManager();
		Address addr = null;
		if (db.connect()) { //connecting to the database was successfull
			if (createTable(db)) { //TODO remove table creation on createNewAddress call and replace it by database initialisation at program start up
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
				} catch (SQLException ex) {
					ex.printStackTrace();
					throw new AddressManagerException(ex);
				}
				try {
					addr = new Address(id, houseNumber, street, town, state, zipcode);
				} catch (IllegalArgumentException ex) {
					ex.printStackTrace();
					//TODO sql Addres insertion succeeded, but class creation doesnt so it's necessarry to remove created row from the database again;
					throw new AddressManagerException(ex);
				}
			}
			db.disconnect();
		}
		return addr;
	}


	public void editAddress(Address newAddress, int id) throws AddressManagerException, IllegalArgumentException {
		//initialize db connection
		if (id < 1) {
			throw new IllegalArgumentException("Can't find Address with id < 1");
		}
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
					st.setInt(6, id); //sets ID value into the statement condition (WHERE ID = ?)
					int updates = st.executeUpdate();
					if (updates < 1) {
						throw new IllegalArgumentException("Given ID was not found during update");
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					throw new AddressManagerException(ex);
				}
				return;
			}
			throw new AddressManagerException("Could not find ADDRESS table.");
		}
		throw new AddressManagerException("Database connection was not reached.");
	}


	/**
	 * Finds <code>Addres</code> with a given ID in the database
	 * @param id database ID
	 * @return <code>Address</code> with a given ID
	 * @throws AddressManagerException on SQL failure
	 * @throws IllegalArgumentException on id out of range (id > 0)
	 */
	public Address findAddressByID(int id) throws AddressManagerException, IllegalArgumentException {
		//initialize db connection
		if (id < 1) {
			throw new IllegalArgumentException("Can't find Address with id < 1");
		}
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
					ex.printStackTrace();
					throw new AddressManagerException(ex);
				}
			}
		}
		return addr;
	}

	/**
	 * Finds all addresses in the database and returns the <code>List</code>
	 * containing instances of <code>Address</code>.
	 * @return List<Address> containing all the found values
	 * @throws AddressManagerException
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
					ex.printStackTrace();
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


	private static String reduceLongString(String value, int maxLength) {
		String strNew = value;
		if (value != null) {
			if (value.length() > maxLength) {
				strNew = value.substring(0, maxLength);
			}
		}
		return strNew;
	}

	private static ArrayList<Address> getAddressFromResultSet(ResultSet rs) throws SQLException {
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
