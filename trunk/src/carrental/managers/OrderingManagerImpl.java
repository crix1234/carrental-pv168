package carrental.managers;

import carrental.entities.Car;
import carrental.entities.Customer;
import carrental.entities.Order;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Matej Cizik
 */
public class OrderingManagerImpl implements OrderingManager {

	/**
	 * Finds all <code>Customer</code>'s <code>Order<code/>s in database
	 *
	 * @param customer <code>Customer</code> the "owner" of these <code>Order<code/>s
	 * @return ArrayList<Order> containing all the found values
	 *		   null if no order found, or customer doesn't exist in the database
	 */
	public ArrayList<Order> getAllOrdersByCustomer(Customer customer) throws OrderingManagerException {
		DBManager db = new DBManager();
		ArrayList<Order> order = new ArrayList<Order>();
		if (db.connect()) { //connecting to the database was successfull
			PreparedStatement st = db.getSelectFromTableStatement("ORDERING", "*", "customerID = " + customer.getId());
			try {
				ResultSet rs = st.executeQuery();
				CustomerManagerImpl cmi = new CustomerManagerImpl();
				OrderManagerImpl omi = new OrderManagerImpl();
				try {
					Customer customer1 = cmi.findCustomerByID(customer.getId());
					if (customer1 != null) {
						if (customer1.equals(customer)) {
						while (rs.next()) {
							//if (customer1.equals(customer)) {
								try {
									order.add(omi.findOrderById(rs.getInt("orderID")));
								} catch (OrderManagerException cex) {
									throw new OrderingManagerException(cex);
								}
							}
						}

					}
				} catch (CustomerManagerException expc) {
					throw new OrderingManagerException(expc);
				}
			} catch (SQLException ex) {
				throw new OrderingManagerException(ex);
			}

		}
		return order;
	}

	/**
	 * Finds all <code>Order<code/>s in database
	 *
	 * @return ArrayList<Order> containing all the found values
	 *		   null if no order found
	 */
	public ArrayList<Order> getAllOrders() throws OrderingManagerException {
		DBManager db = new DBManager();
		ArrayList<Order> order = new ArrayList<Order>();
		if (db.connect()) { //connecting to the database was successfull
			PreparedStatement st = db.getSelectFromTableStatement("ORDERING", "*");
			try {
				ResultSet rs = st.executeQuery();
				CustomerManagerImpl cmi = new CustomerManagerImpl();
				OrderManagerImpl omi = new OrderManagerImpl();
				while (rs.next()) {
					try {
						order.add(omi.findOrderById(rs.getInt("orderID")));
					} catch (OrderManagerException cex) {
						throw new OrderingManagerException(cex);
					}
				}
			} catch (SQLException ex) {
				throw new OrderingManagerException(ex);
			}

		}
		return order;
	}

	/**
	 * Finds the "owner" of the given <code>Order<code/>
	 *
	 * @param order <code>Order<code/> we are looking for
	 * @return <code>Customer</code> found by order
	 *		   null if customer not found, or given order doesn't exist in the database
	 */
	public Customer getCustomerByOrder(Order order) throws OrderingManagerException {
		DBManager db = new DBManager();
		Customer customer = null;
		if (db.connect()) { //connecting to the database was successfull
			PreparedStatement st = db.getSelectFromTableStatement("ORDERING", "*", "orderID = " + order.getId());
			try {
				ResultSet rs = st.executeQuery();
				CustomerManagerImpl cmi = new CustomerManagerImpl();
				OrderManagerImpl omi = new OrderManagerImpl();
				try {
					Order order1 = omi.findOrderById(order.getId());
					if (order1 != null) {
						if (order1.equals(order)) {
							if (rs.next()) {
								try {
									return customer = cmi.findCustomerByID(rs.getInt("customerID"));
								} catch (CustomerManagerException cex) {
									throw new OrderingManagerException(cex);
								}
							}
						}

					}
				} catch (OrderManagerException expc) {
					throw new OrderingManagerException(expc);
				}
			} catch (SQLException ex) {
				throw new OrderingManagerException(ex);
			}

		}
		return customer;
	}

	/**
	 * Finds the car assigned to the given <code>Order<code/>
	 *
	 * @param order <code>Order<code/> we are looking for
	 * @return <code>Car</code> found by order
	 *		   null if car not found, or given order doesn't exist in the database
	 */
	public Car getCarByOrder(Order order) throws OrderingManagerException {
		DBManager db = new DBManager();
		Car car = null;
		if (db.connect()) { //connecting to the database was successfull
			PreparedStatement st = db.getSelectFromTableStatement("ORDERING", "*", "orderID = " + order.getId());
			try {
				ResultSet rs = st.executeQuery();
				CarManagerImpl cmi = new CarManagerImpl();
				OrderManagerImpl omi = new OrderManagerImpl();
				try {
					Order order1 = omi.findOrderById(order.getId());
					if (order1 != null) {
						if (order1.equals(order)) {
							if (rs.next()) {
								try {
									return car = cmi.findCarById(rs.getInt("carID"));
								} catch (CarManagerException cex) {
									throw new OrderingManagerException(cex);
								}
							}
						}

					}
				} catch (OrderManagerException expc) {
					throw new OrderingManagerException(expc);
				}
			} catch (SQLException ex) {
				throw new OrderingManagerException(ex);
			}

		}
		return car;
	}

	/**
	 * Assigns an <code>Order<code/> to a <code>Customer</code> and puts them into the database
	 *
	 * @param order <code>Order<code/> to be assigned
	 * @param customer <code>Customer</code> to whom the <code>Order<code/> should be assigned
	 */
	public void assignOrderToCustomer(Order order, Customer customer) throws OrderingManagerException {
		//TODO change customer and order state
		DBManager db = new DBManager();
		if (db.connect()) { //connecting to the database was successfull
			if (createTable(db)) { //TODO remove table creation on assignCarToOrder call and replace it by database initialisation at program start up
				PreparedStatement st = db.getSelectFromTableStatement("ORDERING", "*", "orderID = " + order.getId());
				try {
					ResultSet rs = st.executeQuery();
					CustomerManagerImpl cmi = new CustomerManagerImpl();
					OrderManagerImpl omi = new OrderManagerImpl();
					try {
						Customer customer1 = cmi.findCustomerByID(customer.getId());
						Order order1 = omi.findOrderById(order.getId());
						if ((customer1 != null) && (order1 != null)) {
							if ((customer1.equals(customer)) && (order1.equals(order))) {
								if (!rs.next()) {
									//INSERT
									st.clearParameters();
									st = db.getInsertIntoTableStatement("ORDERING", "orderID", "customerID");
									st.setInt(1, order.getId());
									st.setInt(2, customer.getId());
									st.executeUpdate();
								} else {
									//UPDATE
									st.clearParameters();
									st = db.getUpdateTableStatement("ORDERING", "customerID");
									st.setInt(1, customer.getId());
									st.setInt(2, order.getId());
									st.executeUpdate();
								}
							}
						}
					} catch (CustomerManagerException expc) {
						throw new OrderingManagerException(expc);
					} catch (OrderManagerException expc) {
						throw new OrderingManagerException(expc);
					}
				} catch (SQLException ex) {
					throw new OrderingManagerException(ex);
				}
			}
			db.disconnect();
		}
	}

	/**
	 * Assigns a <code>Car<code/> to an <code>Order<code/> and puts them into the database
	 *
	 * @param car <code>Car<code/> to be assigned
	 * @param order <code>Order<code/> to whom the <code>Car<code/> should be assigned
	 */
	public void assignCarToOrder(Car car, Order order) throws OrderingManagerException {
		//TODO change car and order state
		DBManager db = new DBManager();
		if (db.connect()) { //connecting to the database was successfull
			if (createTable(db)) { //TODO remove table creation on assignCarToOrder call and replace it by database initialisation at program start up
				PreparedStatement st = db.getSelectFromTableStatement("ORDERING", "*", "orderID = " + order.getId());
				try {
					ResultSet rs = st.executeQuery();
					CarManagerImpl cmi = new CarManagerImpl();
					OrderManagerImpl omi = new OrderManagerImpl();
					try {
						Car car1 = cmi.findCarById(car.getId());
						Order order1 = omi.findOrderById(order.getId());
						if ((car1 != null) && (order1 != null)) {
							if ((car1.equals(car)) && (order1.equals(order))) {
								if (!rs.next()) {
									//INSERT
									st.clearParameters();
									st = db.getInsertIntoTableStatement("ORDERING", "orderID", "carID");
									st.setInt(1, order.getId());
									st.setInt(2, car.getId());
									st.executeUpdate();
								} else {
									//UPDATE
									st.clearParameters();
									st = db.getUpdateTableStatement("ORDERING", "carID");
									st.setInt(1, car.getId());
									st.setInt(2, order.getId());
									st.executeUpdate();
								}
							}
						}
					} catch (CarManagerException expc) {
						throw new OrderingManagerException(expc);
					} catch (OrderManagerException expc) {
						throw new OrderingManagerException(expc);
					}
				} catch (SQLException ex) {
					throw new OrderingManagerException(ex);
				}
			}
			db.disconnect();
		}
	}

	public void assign(Car car, Order order, Customer customer) throws OrderingManagerException {
		//TODO change car and order state
		DBManager db = new DBManager();
		if (db.connect()) { //connecting to the database was successfull
			if (createTable(db)) { //TODO remove table creation on assignCarToOrder call and replace it by database initialisation at program start up
				PreparedStatement st = db.getSelectFromTableStatement("ORDERING", "*", "orderID = " + order.getId());
				try {
					ResultSet rs = st.executeQuery();
					CarManagerImpl cmi = new CarManagerImpl();
					CustomerManagerImpl ccc = new CustomerManagerImpl();
					OrderManagerImpl omi = new OrderManagerImpl();
					try {
						Car car1 = cmi.findCarById(car.getId());
						Order order1 = omi.findOrderById(order.getId());
						Customer cust1 = ccc.findCustomerByID(customer.getId());
						if ((car1 != null) && (order1 != null)) {
							if ((car1.equals(car)) && (cust1.equals(cust1)) && (order1.equals(order))) {
								if (!rs.next()) {
									//INSERT
									st.clearParameters();
									st = db.getInsertIntoTableStatement("ORDERING", "orderID", "carID", "customerID");
									st.setInt(1, order.getId());
									st.setInt(2, car.getId());
									st.setInt(3, customer.getId());
									st.executeUpdate();
								} else {
									//UPDATE
									st.clearParameters();
									st = db.getUpdateTableStatement("ORDERING", "carID", "customerID");
									st.setInt(1, car.getId());
									st.setInt(2, order.getId());
									st.setInt(3, customer.getId());
									st.executeUpdate();
								}
							}
						}
					} catch (CustomerManagerException expc) {
						throw new OrderingManagerException(expc);
					} catch (CarManagerException expc) {
						throw new OrderingManagerException(expc);
					} catch (OrderManagerException expc) {
						throw new OrderingManagerException(expc);
					}
				} catch (SQLException ex) {
					throw new OrderingManagerException(ex);
				}
			}
			db.disconnect();
		}
	}

	/**
	 * Removes a <code>Customer</code>'s <code>Order<code/> from the database
	 * If the orderID would have stayed "alone" (customerID and carID is null in the row)
	 * then this row is deleted
	 *
	 * @param order <code>Order<code/> to be removed
	 * @param customer <code>Customer</code> to whom the <code>Order<code/> should be removed
	 */
	public void removeOrderFromCustomer(Order order, Customer customer) throws OrderingManagerException {
		CustomerManagerImpl cmi = new CustomerManagerImpl();
		OrderManagerImpl omi = new OrderManagerImpl();
		try {
			if ((order != null) && customer != null) {
				try {
					if ((cmi.findCustomerByID(customer.getId())).equals(customer));
					if ((omi.findOrderById(order.getId())).equals(order));
				} catch (CustomerManagerException expc) {
					throw new OrderingManagerException(expc);
				} catch (OrderManagerException expc) {
					throw new OrderingManagerException(expc);
				}
				DBManager db = new DBManager();
				if (db.connect()) { //connecting to the database was successfull
					PreparedStatement st = db.getUpdateTableStatement("ORDERING", "customerID");
					st.setNull(1, java.sql.Types.INTEGER);
					st.setInt(2, order.getId());
					st.executeUpdate();
					deleteRowIfNull(order.getId());
				}
			}
		} catch (SQLException e) {
			throw new OrderingManagerException(e);
		}

	}

	/**
	 * Removes a <code>Car<code/> from <code>Order<code/> and also removes it from the database
	 * If the orderID would have stayed "alone" (customerID and carID is null in the row)
	 * then this row is deleted
	 *
	 * @param car <code>Car<code/> to be removed
	 * @param order <code>Order<code/> to whom the <code>Car<code/> should be removed
	 */
	public void removeCarFromOrder(Car car, Order order) throws OrderingManagerException {
		CarManagerImpl cmi = new CarManagerImpl();
		OrderManagerImpl omi = new OrderManagerImpl();
		try {
			if ((order != null) && car != null) {
				try {
					if ((cmi.findCarById(car.getId())).equals(car));
					if ((omi.findOrderById(order.getId())).equals(order));
				} catch (CarManagerException expc) {
					throw new OrderingManagerException(expc);
				} catch (OrderManagerException expc) {
					throw new OrderingManagerException(expc);
				}
				DBManager db = new DBManager();
				if (db.connect()) { //connecting to the database was successfull
					PreparedStatement st = db.getUpdateTableStatement("ORDERING", "carID");
					st.setNull(1, java.sql.Types.INTEGER);
					st.setInt(2, order.getId());
					st.executeUpdate();
					deleteRowIfNull(order.getId());
				}
			}
		} catch (SQLException e) {
			throw new OrderingManagerException(e);
		}
	}

	/**
	 * creates new table if it's not already in the database
	 * 
	 * @param db <code>DBManager</code> that handles db connection and table creation
	 * @return true if successfull creation;
	 *         false if the database respond was unsuccessfull for some reason
	 */
	public static final boolean createTable(DBManager db) {
		String columns = "ID				INTEGER NOT NULL"
				+ "				PRIMARY KEY GENERATED ALWAYS AS IDENTITY"
				+ "				(START WITH 1, INCREMENT BY 1),"
				+ "customerID	INTEGER,"
				+ "orderID		INTEGER UNIQUE,"
				+ "carID		INTEGER";
		return db.createTable("ORDERING", columns);
	}

	/**
	 * Handles the remove methods. Deletes the row if only orderID stays not null
	 * 
	 * @param id orderID identifying the row
	 * @throws OrderingManagerException 
	 */
	private static final void deleteRowIfNull(int id) throws OrderingManagerException {
		if (id < 1) {
			throw new IllegalArgumentException();
		}
		DBManager db = new DBManager();
		PreparedStatement st = db.getSelectFromTableStatement("ORDERING", "*", "orderID=" + id);
		try {
			ResultSet rs = st.executeQuery();
			rs.next();
			if ((rs.getInt("customerID") == 0) && ((rs.getInt("carID") == 0))) {
				db.deleteRow("ORDERING", id);
			}
		} catch (SQLException ex) {
			throw new OrderingManagerException(ex);
		}
	}
}
