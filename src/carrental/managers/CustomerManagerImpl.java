/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package carrental.managers;

import carrental.entities.Customer;
import java.util.Collection;

/**
 *
 * @author SirGlorg
 */
public class CustomerManagerImpl implements CustomerManager {
	public CustomerManagerImpl() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		initializeDBConnection();
	}

	private static void initializeDBConnection() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		String driver = "org.apache.derby.jdbc.ClientDriver";
		Class.forName(driver).newInstance();
	}

	public Customer createNewCustomer() {
		throw new UnsupportedOperationException("Not supported yet.");
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
