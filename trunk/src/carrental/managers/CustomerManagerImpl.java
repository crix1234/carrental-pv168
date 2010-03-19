package carrental.managers;

import carrental.entities.Customer;
import carrental.entities.Address;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 *
 * @author Pavel Mican
 */
public class CustomerManagerImpl implements CustomerManager {

	public static final int MAXLENGTH_NAME = 40;
	public static final int MAXLENGTH_SURNAME = 40;


	/**
	 * Creates new customer and writes it into the database;
	 * @param name Customer's name
	 * @param surname Customer's surname
	 * @param address Customer's address
	 * @return Customer if creating was successfull;
	 *         null if creating was not successfull
	 */
	public Customer createNewCustomer(String name, String surname, Address address) throws CustomerManagerException {
		//initialize db connection
		name = DBManager.reduceLongString(name,MAXLENGTH_NAME);
		surname = DBManager.reduceLongString(surname,MAXLENGTH_SURNAME);
		AddressManagerImpl addrm = new AddressManagerImpl();
		Address savedAddress = null;
		try {
			savedAddress = addrm.createNewAddress(address);
		} catch (AddressManagerException ex) {
			ex.printStackTrace();
			throw new CustomerManagerException(ex);
		}
		DBManager db = new DBManager();
		Customer customer = null;
		if (db.connect()) { //connecting to the database was successfull
			if (createTable(db)) { //TODO remove table creation on createNewAddress call and replace it by database initialisation at program start up
				int customerID = -1;
				int addressID = savedAddress.getId();
				PreparedStatement st = db.getInsertIntoTableStatement("CUSTOMER", "name", "surname", "addresID");
				try {
					st.clearParameters();
					st.setString(1, name);
					st.setString(2, surname);
					st.setInt(3, addressID);
					st.executeUpdate();
					ResultSet results = st.getGeneratedKeys();
					if (results.next()) {
						customerID = results.getInt(1);
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					throw new CustomerManagerException(ex);
				}
				try {
					customer = new Customer(customerID, name, surname, savedAddress);
				} catch (IllegalArgumentException ex) {
					ex.printStackTrace();
					//TODO sql Addres insertion succeeded, but class creation doesnt so it's necessarry to remove created row from the database again;
					throw new CustomerManagerException(ex);
				}
			}
			db.disconnect();
		}
		return customer;
	}


	/**
	 * Creates new customer and writes it into the database;
	 * @param customer instance of Customer that should be imported in the database
	 * @return Customer if creating was successfull;
	 *         null if creating was not successfull
	 */
	public Customer createNewCustomer(Customer customer) throws CustomerManagerException {
		Customer result = createNewCustomer(customer.getName(), customer.getSurname(), customer.getAddress());
		System.out.println("NEWCUSTOMER: " + result.getName() + "; " + result.getSurname() + "; " + customer.getAddress());
		return result;
	}

	public void editCustomer() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void deleteCustomer() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Customer findCustomerByID(int id) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Customer findCustomerByName(String name) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Customer findCustomerBySurname(String surname) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Collection<Customer> findAllCustomers() {
		throw new UnsupportedOperationException("Not supported yet.");
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
							"name			VARCHAR(" + MAXLENGTH_NAME + ")," +
							"surname		VARCHAR(" + MAXLENGTH_SURNAME + ")," +
							"addresID		INTEGER";
		return db.createTable("CUSTOMER",columns);
	}
}
