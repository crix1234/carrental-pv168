package carrental.managers;

import carrental.entities.Order;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Matej Cizik
 */
public class OrderManagerImpl {

	public static final int MAXLENGTH_ORDER_STATE = 40;

	/**
	 * Creates new <code>Order</code> and saves it into the database
	 * 
	 * @param bookedFrom Date from the order starts
	 * @param bookedTo Date when the order ends
	 * @param orderState String state of an order
	 * @return Order generated <code>Order</code> reflecting input parameters;
	 *         null if <code>Order</code> generating was unsuccessfull
	 * @throws OrderManagerException if Order creating was unsuccessfull or
	 *		   the date bookedTo is before bookedFrom
	 */
	public Order createNewOrder(Date bookedFrom, Date bookedTo, String orderState) throws OrderManagerException {
		if (bookedTo.before(bookedFrom)) {
			throw new OrderManagerException("Date bookedFrom should be before (in time) the date bookedTo.");
		}
		//initialize db connection
		orderState = DBManager.reduceLongString(orderState, MAXLENGTH_ORDER_STATE);
		DBManager db = new DBManager();
		Order order = null;
		if (db.connect()) { //connecting to the database was successfull
			if (createTable(db)) { //TODO remove table creation on createNewOrder call and replace it by database initialisation at program start up
				int id = -1;
				PreparedStatement st = db.getInsertIntoTableStatement("ORDERS", "bookedFrom", "bookedTo", "orderState");
				try {
					st.clearParameters();
					st.setTimestamp(1,new Timestamp(bookedFrom.getTime()));
					st.setTimestamp(2,new Timestamp(bookedTo.getTime()));
					st.setString(3, orderState);
					st.executeUpdate();
					ResultSet results = st.getGeneratedKeys();
					if (results.next()) {
						id = results.getInt(1);
					}
				} catch (SQLException ex) {
					throw new OrderManagerException(ex);
				}
				try {
					order = new Order(id, bookedFrom, bookedTo, orderState);
				} catch (IllegalArgumentException ex) {
					//TODO sql Order insertion succeeded, but class creation doesnt so it's necessarry to remove created row from the database again;
					throw new OrderManagerException(ex);
				}
			}
			db.disconnect();
		}
		return order;
	}

	/**
	 * Calls createNewOrder with values gathered from given parameter <code>order</code>
	 *
	 * @param order <code>Order</code> containing values to be set into the database
	 * @return Order generated <code>Order</code> reflecting input parameters;
	 *         null if <code>Order</code> generating was unsuccessfull
	 * @throws OrderManagerException if Order creating was unsuccessfull
	 */
	public Order createNewOrder(Order order) throws OrderManagerException {
		if (order != null) {
			return createNewOrder(order.getBookedFrom(), order.getBookedTo(), order.getOrderState());
		} else {
			throw new OrderManagerException("createNewOrder argument should be an existing instance of Order, not null.");
		}
	}

	/**
	 * Edits existing <code>Order</code> accessed in the database by ID
	 *
	 * @param neworder <code>Order</code> that should be inserted into the database
	 * @throws OrderManagerException on SQL queries failure or
	 *		   the date bookedTo is before bookedFrom
	 * @throws IllegalArgumentException on failure accessing given <code>Order</code>'s <code>id</code> in the database
	 *                                  or <code>id</code> < 1
	 */
	public void editOrder(Order newOrder) throws OrderManagerException, IllegalArgumentException {
		if (newOrder.getId() < 1) {
			throw new IllegalArgumentException("Can't find Order with id < 1");
		}
		if (newOrder.getBookedTo().before(newOrder.getBookedFrom())) {
			throw new OrderManagerException("Date bookedFrom should be before (in time) the date bookedTo.");
		}
		//initialize db connection
		DBManager db = new DBManager();
		if (db.connect()) { //connecting to the database was successfull
			if (db.tableExists("ORDERS")) {
				try {
					PreparedStatement st = db.getUpdateTableStatement("ORDERS", "bookedFrom", "bookedTo", "orderState");
					st.clearParameters();
					st.setTimestamp(1,new Timestamp(newOrder.getBookedFrom().getTime()));
					st.setTimestamp(2,new Timestamp(newOrder.getBookedTo().getTime()));
					st.setString(3, newOrder.getOrderState());
					st.setInt(4, newOrder.getId()); //sets ID value into the statement condition (WHERE ID = ?)
					int updates = st.executeUpdate();
					if (updates < 1) {
						throw new IllegalArgumentException("Given ID was not found during update");
					}
				} catch (SQLException ex) {
					throw new OrderManagerException(ex);
				}
				return;
			}
			throw new OrderManagerException("Could not find ORDERS table.");
		}
		throw new OrderManagerException("Database connection was not reached.");
	}

	/**
	 * Deletes given <code>Order</code> from the database
	 *
	 * @param order the <code>Order</code> record in the database that should be removed
	 * @return Order representation of the deleted <code>Order</code>
	 * @throws OrderManagerException on Order deletion failure
	 * @throws IllegalArgumentException if argument is null or car id < 1
	 */
	public Order deleteOrder(Order order) throws OrderManagerException, IllegalArgumentException {
		if (order != null) {
			return deleteOrder(order.getId());
		} else {
			throw new IllegalArgumentException("Can't delete order that is null");
		}
	}

	/**
	 * Deletes given <code>Order</code> with the given the database
	 *
	 * @param id the <code>id</code> of the <code>Order</code> record in the database
	 *           that should be removed
	 * @return Order representation of the deleted <code>Order</code>
	 * @throws OrderManagerException on Orderr deletion failure
	 * @throws IllegalArgumentException if argument is null or car id < 1
	 */
	public Order deleteOrder(int id) throws OrderManagerException, IllegalArgumentException {
		if (id > 0) {
			Order deletedOrder = findOrderById(id);
			if (deletedOrder != null) {
				//initialize db connection
				DBManager db = new DBManager();
				if (db.connect()) { //connecting to the database was successfull
					try {
						if (db.deleteRow("ORDERS", id) == 0) {
							return null;	// no order was actually deleted
						}
					} catch (SQLException ex) {
						throw new OrderManagerException(ex);
					}
				}
			}
			return deletedOrder;
		} else {
			throw new IllegalArgumentException("Order id should be positive integer!");
		}
	}

	/**
	 * Finds all <code>Orders</code> in the database and returns the <code>List</code>
	 * containing instances of found <code>Orders</code>.
	 *
	 * @return ArrayList<Order> containing all the found values
	 * @throws OrderManagerException on SQL queries failure
	 */
	public ArrayList<Order> findAllOrders() throws OrderManagerException {
		//initialize db connection
		DBManager db = new DBManager();
		ArrayList<Order> order = new ArrayList<Order>();
		if (db.connect()) { //connecting to the database was successfull
			if (db.tableExists("ORDERS")) {
				PreparedStatement st = db.getSelectFromTableStatement("ORDERS", "*");
				try {
					ResultSet rs = st.executeQuery();
					order.addAll(getOrderFromResultSet(rs));
				} catch (SQLException ex) {
					throw new OrderManagerException(ex);
				}
			}
			db.disconnect();
		}
		return order;
	}

	/**
	 * Finds all <code>Customer's</code> <code>Orders</code> in the database and returns the <code>List</code>
	 * containing instances of found <code>Orders</code>.
	 *
	 * @param customer
	 * @return
	 * @throws OrderManagerException
	 */
//	public ArrayList<Order> findALLCustomersOrders(Customer customer) throws OrderManagerException {
//		//TODO nemal by toto riesit OrderingManager?
//		throw new UnsupportedOperationException();
//	}

	/**
	 * Finds <code>Order</code> with a given <code>id</code> in the database
	 *
	 * @param id
	 * @return <code>Order</code> with a given <code>id</code> or <code>null</code>
	 * @throws OrderManagerException on SQL query failure
	 * @throws IllegalArgumentException on <code>id</code> out of range (if <code>id</code> < 1)
	 */
	public Order findOrderById(int id) throws OrderManagerException, IllegalArgumentException {
		if (id < 1) {
			throw new IllegalArgumentException("Can't find Order with id < 1");
		}
		//initialize db connection
		DBManager db = new DBManager();
		Order order = null;
		if (db.connect()) { //connecting to the database was successfull
			if (db.tableExists("ORDERS")) {
				PreparedStatement st = db.getSelectFromTableStatement("ORDERS", "*", "id = "+id);
				try {
					ResultSet rs = st.executeQuery();
					ArrayList<Order> queryResult = getOrderFromResultSet(rs);
					if (queryResult.size() > 0) {
						order = queryResult.get(0);
					}
				} catch (SQLException ex) {
					throw new OrderManagerException(ex);
				}
			}
		}
		return order;
	}
	
	/**
	 * Finds all <code>Orders</code> with given order state in the database and returns
	 * the <code>List</code> containing instances of found <code>Order</code>.
	 *
	 * @param orderState <code>state</code> of wanted orders
	 * @return ArrayList<Car> containing all the found values
	 * @throws OrderManagerException on SQL query failure
	 */
	public ArrayList<Order> findOrderByOrderState(String orderState) throws OrderManagerException {
		//TODO add in class diagram and orderManager
		//TODO is it neccessary?
		//initialize db connection
		DBManager db = new DBManager();
		ArrayList<Order> order = new ArrayList<Order>();
		if (db.connect()) { //connecting to the database was successfull
			if (db.tableExists("ORDERS")) {
				try {
					PreparedStatement st = db.getSelectFromTableStatement("ORDERS", "*", "orderState = ?");
					st.setString(1, orderState);
					ResultSet rs = st.executeQuery();
					order.addAll(getOrderFromResultSet(rs));
				} catch (SQLException ex) {
					throw new OrderManagerException(ex);
				}
			}
		}
		return order;
	}

	/**
	 * creates new table if it's not already in the database
	 *
	 * @param db <code>DBManager</code> that handles db connection and table creation
	 * @return true if successfull creation;
	 *         false if the database respond was unsuccessfull for some reason
	 */
	public static final boolean createTable(DBManager db) {
		String columns = "ID				INTEGER NOT NULL" +
						 "					PRIMARY KEY GENERATED ALWAYS AS IDENTITY" +
						 "					(START WITH 1, INCREMENT BY 1)," +
						 "bookedFrom		TIMESTAMP," +
						 "bookedTo		    TIMESTAMP,"+
					     "orderState		VARCHAR(" + MAXLENGTH_ORDER_STATE + ")";
		return db.createTable("ORDERS", columns);
	}

	/**
	 * Handles creating <code>Order</code> instances from database's <code>ResultSet</code>
	 *
	 * @param rs <code>ResultSet</code> retreaved from the previous database query
	 * @return ArrayList<Car> of all retreaved orders
	 * @throws SQLException if reading arguments fails
	 */
	private static final ArrayList<Order> getOrderFromResultSet(ResultSet rs) throws SQLException {
		ArrayList<Order> orders = new ArrayList<Order>();
		Order newOrder;
		while (rs.next()) {
			newOrder = new Order(rs.getInt("ID"),
					new Date(rs.getTimestamp("bookedFrom").getTime()),
					new Date(rs.getTimestamp("bookedTo").getTime()),
					rs.getString("orderState"));
			orders.add(newOrder);
		}
		return orders;
	}
}
