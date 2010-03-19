package carrental.managers;
import java.util.Collection;
import carrental.entities.Customer;
import carrental.entities.Address;


/**
 *
 * @author Pavel Mican
 */

public interface CustomerManager {
	
	public Customer createNewCustomer(String name, String surname, Address address) throws CustomerManagerException;
	public Customer createNewCustomer(Customer customer) throws CustomerManagerException;
	public void editCustomer();
	public void deleteCustomer();
	public Customer findCustomerByID(int id);
	public Customer findCustomerByName(String name);
	public Customer findCustomerBySurname(String surname);
	public Collection<Customer> findAllCustomers();
}
