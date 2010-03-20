package carrental.managers;

import carrental.entities.Customer;
import carrental.entities.Address;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
			if (createTable(db)) { //TODO remove table creation on createNewCustomer call and replace it by database initialisation at program start up
				int customerID = -1;
				int addressID = savedAddress.getId();
				PreparedStatement st = db.getInsertIntoTableStatement("CUSTOMER", "name", "surname", "addressID");
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
					//TODO sql Customer insertion succeeded, but class creation doesnt so it's necessarry to remove created row from the database again;
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
		if (customer != null) {
			return createNewCustomer(customer.getName(), customer.getSurname(), customer.getAddress());
		} else {
			throw new CustomerManagerException("createNewCustomer argument should be an existing instance of Customer, not null.");
		}
	}


	/**
	 * Edits existing <code>Customer</code> accessed in the database by ID
	 * @param newCustomer <code>Customer</code> that should be inserted into the database
	 * @throws CustomerManagerException	on SQL queries failure
	 * @throws IllegalArgumentException on failure accessing given <code>Customer</code>'s <code>id</code> in the database
	 *                                  or <code>id</code> < 1
	 */
	public void editCustomer(Customer newCustomer) throws CustomerManagerException, IllegalArgumentException {
		if (newCustomer.getId() < 1) {
			throw new IllegalArgumentException("Can't find Address with id < 1");
		}
		//initialize db connection
		DBManager db = new DBManager();
		if (db.connect()) { //connecting to the database was successfull
			if (db.tableExists("CUSTOMER")) {
				try {
					Address newAddress = newCustomer.getAddress();
					AddressManagerImpl addrm = new AddressManagerImpl();
					try {
						addrm.editAddress(newAddress);
					} catch (Exception ex) {
						ex.printStackTrace();
						throw new CustomerManagerException(ex);
					}
					PreparedStatement st = db.getUpdateTableStatement("CUSTOMER", "name","surname","addressID");
					st.setString(1, newCustomer.getName());
					st.setString(2, newCustomer.getSurname());
					st.setInt(3, newAddress.getId());
					st.setInt(4, newCustomer.getId()); //sets ID value into the statement condition (WHERE ID = ?)
					int updates = st.executeUpdate();
					if (updates < 1) {
						throw new IllegalArgumentException("Given ID was not found during update");
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					throw new CustomerManagerException(ex);
				}
				return;
			}
			throw new CustomerManagerException("Could not find CUSTOMER table.");
		}
		throw new CustomerManagerException("Database connection was not reached.");
	}


	/**
	 * deletes given customer from the database; Only customer ID is searched
	 * @param customer the <code>Customer</code> that should be removed from the database.
	 * @return Customer representation of the deleted <code>Custommer</code>
	 * @throws CustomerManagerException on customer deletion failure;
	 *         IllegalArgumentException if argument is null or customer <code>id</code> < 1
	 */
	public Customer deleteCustomer(Customer customer) throws CustomerManagerException, IllegalArgumentException {
		if (customer != null) {
			return deleteCustomer(customer.getId());
		} else {
			throw new IllegalArgumentException("Can't delete customer that is null");
		}
	}

	/**
	 * deletes <code>Customer</code> with the given id from the database
	 * @param custId the <code>id</code> of the <code>Customer</code> record in the database
	 *           that should be removed
	 * @return Customer representation of the deleted <code>Customer</code>
	 * @throws CustomerManagerException on customer deletion failure;
	 *         IllegalArgumentException if argument is null or customer <code>id</code> < 1
	 */
	public Customer deleteCustomer(int id) throws CustomerManagerException, IllegalArgumentException {
		if (id > 0) {
			AddressManagerImpl addrm = new AddressManagerImpl();
			Customer customerToDelete = findCustomerByID(id);
			Address customersAddress = customerToDelete.getAddress();
			try {
				addrm.deleteAddress(customersAddress);
			} catch (AddressManagerException ex) {
				ex.printStackTrace();
				throw new CustomerManagerException(ex);
			}
			if (customerToDelete != null) {
				//initialize db connection
				DBManager db = new DBManager();
				if (db.connect()) { //connecting to the database was successfull
					try {
						if (db.deleteRow("CUSTOMER", id) == 0) {
							return null;	// no address was actually deleted
						} else {
							return customerToDelete;
						}
					} catch (SQLException ex) {
						try {
							addrm.createNewAddress(customersAddress); //customer was not delted, but his address was, so it's necessarry to put it back into the database
						} catch (AddressManagerException addrEx) {
							addrEx.printStackTrace();
							throw new CustomerManagerException("FATAL ERROR - it's highly probable, that the database has been corrupted. Customers addres was to be removed but failed and its Address reentry into the database failed to");
						}
						ex.printStackTrace();
						throw new CustomerManagerException(ex);
					}
				}
			}
			return null;
		} else {
			throw new IllegalArgumentException("Customer id should be positive integer!");
		}
	}

	/**
	 * Finds <code>Customer</code> with a given <code>id</code>
	 * @param id <code>Customer</code>'s <code>id</code> in the database
	 * @return <code>Customer</code> with a given <code>id</code> or <code>null</code>
	 * @throws CustomerManagerException on SQL failure
	 * @throws IllegalArgumentException on <code>id</code> out of range (if <code>id</code> < 1)
	 */
	public Customer findCustomerByID(int id) throws CustomerManagerException, IllegalArgumentException  {
		if (id < 1) {
			throw new IllegalArgumentException("Can't find Customer with id < 1");
		}
		//initialize db connection
		DBManager db = new DBManager();
		Customer customer = null;
		if (db.connect()) { //connecting to the database was successfull
			if (db.tableExists("CUSTOMER")) {
				PreparedStatement st = db.getSelectFromTableStatement("CUSTOMER", "*", "id = "+id);
				try {
					ResultSet rs = st.executeQuery();
					ArrayList<Customer> queryResult = getCustomerFromResultSet(rs);
					if (queryResult.size() > 0) {
						customer = queryResult.get(0);
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					throw new CustomerManagerException(ex);
				}
			} else {
				throw new CustomerManagerException("Could not find CUSTOMER table.");
			}
		} else {
			throw new CustomerManagerException("Database connection was not reached.");
		}
		return customer;
	}

	/**
	 * Finds <code>Customer</code> with a given <code>name</code>
	 * @param name <code>Customer</code>'s <code>name</code> in the database
	 * @return <code>ArrayList<Customer></code> with a given <code>name</code>
	 *               (including empty <code>ArrayList</code> if no result found)
	 * @throws CustomerManagerException on SQL failure
	 * @throws IllegalArgumentException if <code>name</code> is null
	 */
	public ArrayList<Customer> findCustomerByName(String name) throws CustomerManagerException, IllegalArgumentException  {
		if (name == null) {
			throw new IllegalArgumentException("Argument String can not be null");
		}
		return findCustomerByValue("name",name);
	}

	/**
	 * Finds <code>Customer</code> with a given <code>surname</code>
	 * @param surname <code>Customer</code>'s <code>surname</code> in the database
	 * @return <code>ArrayList<Customer></code> with a given <code>surname</code>
	 *               (including empty <code>ArrayList</code> if no result found)
	 * @throws CustomerManagerException on SQL failure
	 * @throws IllegalArgumentException if <code>surname</code> is null
	 */
	public ArrayList<Customer> findCustomerBySurname(String surname) throws CustomerManagerException  {
		if (surname == null) {
			throw new IllegalArgumentException("Argument String can not be null");
		}
		return findCustomerByValue("surname",surname);
	}

	/**
	 * Finds all <code>Customer</code>s and returns the <code>ArrayList</code>
	 * containing instances of found <code>Customer</code>s.
	 * @return ArrayList<Customer> containing all the found values
	 * @throws CustomerManagerException on SQL query failure
	 */
	public ArrayList<Customer> findAllCustomers() throws CustomerManagerException  {
		return findCustomerByValue(null,null);
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
							"addressID		INTEGER";
		return db.createTable("CUSTOMER",columns);
	}

	/**
	 * Handles creating <code>Customer</code> instances from database's <code>ResultSet</code>
	 *
	 * @param rs <code>ResultSet</code> retreaved from the previous database query
	 * @return ArrayList<Customer> of all retreaved customers
	 * @throws SQLException if reading arguments fails
	 * @throws CustomerManagerException if calling customer's <code>Address</code> fails
	 */
	private static final ArrayList<Customer> getCustomerFromResultSet(ResultSet rs) throws SQLException, CustomerManagerException {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		Customer newCustomer;
		Address customersAddress;
		AddressManagerImpl addrm = new AddressManagerImpl();
		while(rs.next()) {
			try {	// frist receive Customers Address using retrieved ID from the database
				customersAddress = addrm.findAddressByID(rs.getInt("addressID"));
			} catch (Exception ex) {	//IllegalArgumentException or AddressManagerException
				ex.printStackTrace();
				throw new CustomerManagerException(ex);
			}
			// now create new Customer using all retrieved info from the database
			// and put it into the ArrayList for later returning
			newCustomer = new Customer(rs.getInt("ID"),
					rs.getString("name"),
					rs.getString("surname"),
					customersAddress);
			customers.add(newCustomer);
		}
		return customers;
	}

	/**
	 * Supporting method handling SQL searches using the given value name and the
	 * corresponding value. Select search is triggered with given parameters and
	 * the result is returned.
	 * @param valueName the name of the attribute in the database, that should be checked
	 * @param value records with this value in <code>valueName</code> column of the database
	 *              will be returned.
	 * @return ArrayList<Customer> <code>Collection</code> of all the results
	 * @throws CustomerManagerException on SQL query failure
	 */
	private static final ArrayList<Customer> findCustomerByValue(String valueName, String value) throws CustomerManagerException  {
		//initialize db connection
		DBManager db = new DBManager();
		ArrayList<Customer> queryResult = null;
		if (db.connect()) { //connecting to the database was successfull
			if (db.tableExists("CUSTOMER")) {
				PreparedStatement st;
				try {
					if (valueName == null) {
						st = db.getSelectFromTableStatement("CUSTOMER", "*", null);
					} else {
						st = db.getSelectFromTableStatement("CUSTOMER", "*", valueName + " = ?");
						st.setString(1, value);
					}
					ResultSet rs = st.executeQuery();
					queryResult = getCustomerFromResultSet(rs);
				} catch (SQLException ex) {
					ex.printStackTrace();
					throw new CustomerManagerException(ex);
				}
			} else {
				throw new CustomerManagerException("Could not find CUSTOMER table.");
			}
		} else {
			throw new CustomerManagerException("Database connection was not reached.");
		}
		return queryResult;
	}
}
