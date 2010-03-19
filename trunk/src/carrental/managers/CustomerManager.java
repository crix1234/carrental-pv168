package carrental.managers;
import java.util.ArrayList;
import carrental.entities.Customer;
import carrental.entities.Address;


/**
 *
 * @author Pavel Mican
 */

public interface CustomerManager {
	
	public Customer createNewCustomer(String name, String surname, Address address) throws CustomerManagerException;
	public Customer createNewCustomer(Customer customer) throws CustomerManagerException;
	public void editCustomer(Customer customer) throws CustomerManagerException ;
	public void deleteCustomer(Customer customer) throws CustomerManagerException ;
	public Customer findCustomerByID(int id) throws CustomerManagerException ;
	public ArrayList<Customer> findCustomerByName(String name) throws CustomerManagerException ;
	public ArrayList<Customer> findCustomerBySurname(String surname) throws CustomerManagerException ;
	public ArrayList<Customer> findAllCustomers() throws CustomerManagerException;
}
