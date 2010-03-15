/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package carrental.managers;
import java.util.Collection;
import carrental.entities.Customer;

/**
 *
 * @author Pavel Mican
 */

public interface CustomerManager {
	public Customer createNewCustomer();
	public void editCustomer();
	public void deleteCustomer();
	public Customer findCustomerByID(int id);
	public Customer findCustomerByName(String name);
	public Customer findCustomerBySurname(String surname);
	public Collection<Customer> findAllCustomers();
}
