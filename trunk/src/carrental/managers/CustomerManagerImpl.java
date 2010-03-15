package carrental.managers;

import carrental.entities.Customer;
import carrental.entities.Address;
import java.util.Collection;

/**
 *
 * @author Pavel Mican
 */
public class CustomerManagerImpl implements CustomerManager {
	public CustomerManagerImpl() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		initializeDBConnection();
	}

	private static void initializeDBConnection() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
	}

	public Customer createNewCustomer(String name, String surname, Address address) {
		int id = 1; //TODO id generator
		Customer newCustomer = new Customer(id, name, surname, address);
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
