package carrental.managers;

import carrental.entities.Car;
import carrental.entities.Customer;
import carrental.entities.Order;
import java.util.ArrayList;

/**
 *
 * @author Matej Cizik
 */
public interface OrderingManager {

	public ArrayList<Order> getAllOrdersByCustomer(Customer customer) throws OrderingManagerException;

	public Customer getCustomerByOrder(Order order) throws OrderingManagerException;

	public void assignOrderToCustomer(Order order, Customer customer) throws OrderingManagerException;

	public void assignCarToOrder(Car car, Order order) throws OrderingManagerException;

	public void removeOrderFromCustomer(Order order, Customer customer) throws OrderingManagerException;

	public void removeCarFromOrder(Car car, Order order) throws OrderingManagerException;
}
