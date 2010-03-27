package carrental.managers;

import carrental.entities.Order;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Matej Cizik
 */
public class OrderManagerImplTest {

	public OrderManagerImplTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		System.out.println("CAR MANAGER TESTS:");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
		DBManager db = new DBManager();
		db.connect();
		db.dropTable("ORDERS");
		db.disconnect();
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of createNewOrder method, of class OrderManagerImpl.
	 */
	@Test
	public void testCreateNewOrder_3args() throws Exception {
		System.out.println("createNewOrder");
		try {
			Date bookedFrom = new GregorianCalendar(2010, 2, 28).getTime();
			Date bookedTo = new GregorianCalendar(2010, 2, 29).getTime();
			String orderState = "test";
			OrderManagerImpl instance = new OrderManagerImpl();
			Order expResult = new Order(1, bookedFrom, bookedTo, orderState);
			Order result = instance.createNewOrder(bookedFrom, bookedTo, orderState);
			assertEquals(expResult, result);

			orderState = "blablablabblablablabblablablabblablablabblablablab";
			result = instance.createNewOrder(bookedFrom, bookedTo, orderState);
			assertEquals(OrderManagerImpl.MAXLENGTH_ORDER_STATE, result.getOrderState().length());

			bookedFrom = new GregorianCalendar(2010, 2, 30).getTime();
			try {
				instance.createNewOrder(bookedFrom, bookedTo, orderState);
				fail("Should have thrown OrderManagerException");
			} catch (OrderManagerException ex) {
			}

			try {
				instance.createNewOrder(null);
				fail();
			} catch (OrderManagerException ex) {
			}

		} catch (OrderManagerException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Test of createNewOrder method, of class OrderManagerImpl.
	 */
	@Test
	public void testCreateNewOrder_Order() throws Exception {
		System.out.println("createNewOrder");
		Date bookedFrom = new GregorianCalendar(2010, 2, 28).getTime();
		Date bookedTo = new GregorianCalendar(2010, 2, 29).getTime();
		String orderState = "new";
		Order order = new Order(1, bookedFrom, bookedTo, orderState);
		OrderManagerImpl instance = new OrderManagerImpl();
		Order expResult = order;
		try {
			Order result = instance.createNewOrder(order);
			assertEquals(expResult, result);
		} catch (OrderManagerException ex) {
			fail();
		}
	}

	/**
	 * Test of editOrder method, of class OrderManagerImpl.
	 */
	@Test
	public void testEditOrder() throws Exception {
		System.out.println("editOrder");
		try {
			Date bookedFrom = new GregorianCalendar(2000, 0, 1).getTime();
			Date bookedTo = new GregorianCalendar(2010, 2, 28).getTime();
			OrderManagerImpl instance = new OrderManagerImpl();
			instance.createNewOrder(bookedFrom, bookedTo, "first");
			bookedFrom = new GregorianCalendar(2020, 0, 1).getTime();
			bookedTo = new GregorianCalendar(2040, 2, 28).getTime();
			instance.createNewOrder(bookedFrom, bookedTo, "second");
			bookedFrom = new GregorianCalendar(2020, 11, 31).getTime();
			Order newOrder = new Order(1, bookedFrom, bookedTo, "second");

			try {
				instance.editOrder(newOrder);
			} catch (OrderManagerException ex) {
				fail();
			}

			newOrder = new Order(3, bookedFrom, bookedTo, "second");
			try {
				instance.editOrder(newOrder);
				fail();
			} catch (IllegalArgumentException ex) {
			}
		} catch (OrderManagerException ex) {
			fail();
		}
	}

	/**
	 * Test of deleteOrder method, of class OrderManagerImpl.
	 */
	@Test
	public void testDeleteOrder_Order() throws Exception {
		System.out.println("deleteOrder");
		try {
			Order order = new Order(1, new Date(), new Date(), "delete!!");
			OrderManagerImpl instance = new OrderManagerImpl();
			Order expResult = null;
			Order result = instance.deleteOrder(order);
			assertEquals(expResult, result);

			instance.createNewOrder(order);
			expResult = order;
			result = instance.deleteOrder(order);
			assertEquals(expResult, result);

			ArrayList<Order> list = instance.findAllOrders();
			ArrayList<Order> expList = new ArrayList<Order>();
			assertEquals(expList, list);
		} catch (OrderManagerException ex) {
			fail();
		}
	}

	/**
	 * Test of deleteOrder method, of class OrderManagerImpl.
	 */
	@Test
	public void testDeleteOrder_int() throws Exception {
		System.out.println("deleteOrder");
		try {
			Order order = new Order(1, new Date(), new Date(), "delete!!");
			OrderManagerImpl instance = new OrderManagerImpl();
			Order expResult = instance.createNewOrder(order);
			Order result = instance.deleteOrder(1);
			assertEquals(expResult, result);

			try {
				instance.deleteOrder(0);
				fail();
			} catch (IllegalArgumentException e) {
			}
		} catch (OrderManagerException ex) {
			fail();
		}
	}

	/**
	 * Test of findAllOrders method, of class OrderManagerImpl.
	 */
	@Test
	public void testFindAllOrders() throws Exception {
		System.out.println("findAllOrders");
		try {
			OrderManagerImpl instance = new OrderManagerImpl();
			ArrayList<Order> expResult = new ArrayList<Order>();
			ArrayList<Order> result = instance.findAllOrders();
			assertEquals(expResult, result);

			Order order = new Order(1, new Date(), new Date(), "delete!!");
			instance.createNewOrder(order);
			expResult.add(order);
			order = new Order(2, new Date(), new Date(), "destruct");
			instance.createNewOrder(order);
			result = instance.findAllOrders();
			expResult.add(order);
			assertEquals(expResult, result);
		} catch (OrderManagerException ex) {
			fail();
		}

	}

	/**
	 * Test of findOrderById method, of class OrderManagerImpl.
	 */
	@Test
	public void testFindOrderById() throws Exception {
		System.out.println("findOrderById");
		try {
			int id = 0;
			OrderManagerImpl instance = new OrderManagerImpl();
			Order expResult = null;
			try {
				Order result = instance.findOrderById(id);
				fail();
			} catch (IllegalArgumentException e) {
			}

			Order order = instance.createNewOrder(new Date(), new Date(), "first");
			Order order2 = instance.createNewOrder(new Date(), new Date(), "second");
			Order result = instance.findOrderById(2);
			assertEquals(order2, result);

			result = instance.findOrderById(1);
			assertEquals(order, result);
		} catch (OrderManagerException ex) {
			fail();
		}
	}

	/**
	 * Test of findOrderByOrderState method, of class OrderManagerImpl.
	 */
	@Test
	public void testFindOrderByOrderState() throws Exception {
		System.out.println("findOrderByOrderState");
		try {
			String orderState = "";
			OrderManagerImpl instance = new OrderManagerImpl();
			ArrayList<Order> expResult = new ArrayList<Order>();
			ArrayList<Order> result = instance.findOrderByOrderState(orderState);
			assertEquals(expResult, result);

			Order order = instance.createNewOrder(new Date(), new Date(), "first");
			Order order2 = instance.createNewOrder(new Date(), new Date(), "second");
			Order order3 = instance.createNewOrder(new Date(), new Date(), "second");
			orderState = "first";
			result = instance.findOrderByOrderState(orderState);
			expResult.add(order);
			assertEquals(expResult, result);

			expResult.clear();
			orderState = "second";
			expResult.add(order2);
			expResult.add(order3);
			result = instance.findOrderByOrderState(orderState);
			assertEquals(expResult, result);
		} catch (OrderManagerException ex) {
			fail();
		}
	}
}
