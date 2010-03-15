package carrental.managers;
import java.util.Collection;
import java.sql.SQLException;
import carrental.entities.Customer;
import carrental.entities.Address;


/**
 *
 * @author Pavel Mican
 */

public interface CustomerManager {
	/**
	 * Creates new customer and writes it into the database;
	 * @param name Customer's name
	 * @param surname Customer's surname
	 * @param address Customer's address
	 * @return Customer if creating was successfull
	 * @return null if creating was not successfull
	 */
	public Customer createNewCustomer(String name, String surname, Address address);
	public void editCustomer();
	public void deleteCustomer();
	public Customer findCustomerByID(int id);
	public Customer findCustomerByName(String name);
	public Customer findCustomerBySurname(String surname);
	public Collection<Customer> findAllCustomers();
}
