package carrental.managers;

import carrental.entities.Customer;
import carrental.entities.Address;
import java.sql.*;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pavel Mican
 */
public class CustomerManagerImpl implements CustomerManager {
	public CustomerManagerImpl() {
		DBManager db = new DBManager();
		Connection conn = db.connect();
		db.disconnect(conn);
	}

	public Customer createNewCustomer(String name, String surname, Address address) {
		int id = 1; //TODO id generator
		Customer newCustomer = null;
		DBManager db = new DBManager();
		Connection conn = db.connect();
		PreparedStatement st;
		try {
			st = conn.prepareStatement("SELECT * FROM APP.CUSTOMER");
			ResultSet rs = st.executeQuery();
			newCustomer = new Customer(id, name, surname, address);
		} catch (SQLException ex) {
			Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		conn.close();
		return newCustomer;
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
}
