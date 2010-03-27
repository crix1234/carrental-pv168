package carrental.managers;

import carrental.entities.Customer;
import carrental.entities.Order;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Matej Cizik
 */
public interface OrderManager {

	public Order createNewOrder(Date bookedFrom, Date bookedTo, String orderState) throws OrderManagerException;

	public Order createNewOrder(Order order) throws OrderManagerException;

	public void editOrder(Order order) throws OrderManagerException, IllegalArgumentException;

	public Order deleteOrder(Order order) throws OrderManagerException, IllegalArgumentException;

	public Order deleteOrder(int id) throws OrderManagerException, IllegalArgumentException;

	public ArrayList<Order> findAllOrders() throws OrderManagerException;

	//public ArrayList<Order> findALLCustomersOrders(Customer customer) throws OrderManagerException;

	public Order findOrderByID(int id) throws OrderManagerException, IllegalArgumentException;

	public ArrayList<Order> findOrderByOrderState(String orderState) throws OrderManagerException;
}
