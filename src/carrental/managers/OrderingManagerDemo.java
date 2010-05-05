package carrental.managers;

import carrental.entities.Address;
import carrental.entities.Car;
import carrental.entities.CarType;
import carrental.entities.Customer;
import carrental.entities.Order;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Matej Cizik
 */
public class OrderingManagerDemo {

	public static void main(String args[]) throws Exception {
		OrderingManagerImpl om = new OrderingManagerImpl();
		CarManagerImpl cmi = new CarManagerImpl();
		OrderManagerImpl omi = new OrderManagerImpl();
		AddressManagerImpl ami = new AddressManagerImpl();
		CustomerManagerImpl ccmi = new CustomerManagerImpl();
		CarType type = CarType.MICROBUS;
		//Car car = cmi.createNewCar("mazda", "ba-122-ba", "ok", type);
		Car ca = new Car(2, "mazda", "ba-122-ba", "ok", type);
		Order ord = new Order(2, new Date(), new Date(), "OK");
		//Order order = omi.createNewOrder(new Date(), new Date(), "OK");
		Address add = new Address(2, 200, "streeeet", "teeown", "seetate", "zeeipcode");
		//add = ami.createNewAddress(add);
		Customer cust = new Customer(2, "name", "surname", add);
		//cust = ccmi.createNewCustomer(cust);
		ord = omi.findOrderById(2);
		Customer gusto = om.getCustomerByOrder(ord);
		System.out.println(gusto);
		ArrayList<Customer> listt= ccmi.findCustomerByName("name");
		ArrayList<Order> list = om.getAllOrdersByCustomer(listt.get(0));
		System.out.println("name's orders");
		for (Order ordd : list) {
			System.out.println(ordd);
		}
		//om.assignCarToOrder(ca, ord);
		//om.assignOrderToCustomer(ord, cust);
		//cust = ccmi.findCustomerByID(1);
		//om.removeOrderFromCustomer(ord, cust);
		//ca = cmi.findCarById(1);
		//om.removeCarFromOrder(ca, ord);
	}
}
