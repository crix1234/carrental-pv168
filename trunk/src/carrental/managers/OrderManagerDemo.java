package carrental.managers;

import carrental.entities.Order;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Matej Cizik
 */
public class OrderManagerDemo {
	public static void main(String args[]) throws Exception {
		OrderManagerImpl om = new OrderManagerImpl();
		DBManager db = new DBManager();
		db.connect();
		om.createTable(db);
		db.disconnect();
		//DateFormat df = new DateFormat("dd-MM-yyyy");
		//Date bf = new Date();
		//GregorianCalendar gc = new GregorianCalendar(1995, 11, 1);
//		Date bf = new GregorianCalendar(2011, 2, 28).getTime();
		//System.out.println(bf);

//		Date bt = new Date();
		//Order order = new Order(3, bf, bf, "edit");
		//System.out.println(bt.getTime());
//		om.createNewOrder(bt, bf, "first");
//		om.createNewOrder(bf, bt, "second");
//		om.createNewOrder(bf, bt, "third");
		//om.createNewOrder(order);
		//om.deleteOrder(order);
		//om.editOrder();
		//ArrayList<Order> list = om.findAllOrders();
//		for (Order order1 : list) {
//			System.out.println(order1);
//		}

//		ArrayList<Order> list = om.findOrderByOrderState("second");
//		for (Order order : list) {
//			System.out.println(order);
//		}
	}
}
