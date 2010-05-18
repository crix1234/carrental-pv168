/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.models;

import carrental.entities.Customer;
import carrental.entities.Order;
import carrental.managers.OrderManagerException;
import carrental.managers.OrderManagerImpl;
import carrental.managers.OrderingManagerException;
import carrental.managers.OrderingManagerImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jerrycek
 */
public class ShowOrdersTableModel extends AbstractTableModel {

	private List<Order> orders = new ArrayList<Order>();

	public void loadOrders(Customer cust) {
		OrderingManagerImpl cmi = new OrderingManagerImpl();
		try {
			orders = cmi.getAllOrdersByCustomer(cust);
			//for (Order order : orders) {
			//	System.out.println(order.toString());
			//}
		} catch (OrderingManagerException ex) {
			Logger.getLogger(ShowOrdersTableModel.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void addOrder(Order order) {
		orders.add(order);
	}

	public Order getOrderAt(int i) {
		return orders.get(i);
	}

	public int getRowCount() {
		return orders.size();
	}

	public int getColumnCount() {
		return 6;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Order order = orders.get(rowIndex);
		OrderingManagerImpl omi = new OrderingManagerImpl();
		switch (columnIndex) {
			case 0:
				return order.getId();
			case 1:
				return order.getBookedFrom();
			case 2:
				return order.getBookedTo();
			case 3:
				return order.getOrderState();
			case 4:
				try {
					return omi.getCarByOrder(order).getName();
				} catch (OrderingManagerException ex) {
					Logger.getLogger(ShowOrdersTableModel.class.getName()).log(Level.SEVERE, null, ex);
				}
			case 5:
				try {
					return omi.getCarByOrder(order).getLicencePlate();
				} catch (OrderingManagerException ex) {
					Logger.getLogger(ShowOrdersTableModel.class.getName()).log(Level.SEVERE, null, ex);
				}
			default:
				throw new IllegalArgumentException("columnIndex");
		}
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
			case 0:
				//return "Id";
				return "Id";
			case 1:
				//return "Name";
				return "Booked from";
			case 2:
				//return "Licence plate";
				return "Booked to";
			case 3:
				//return "State";
				return "State";
			case 4:
				//return "Type";
				return "Car name";
			case 5:
				return "Car licence plate";
			default:
				throw new IllegalArgumentException("columnIndex");
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return Integer.class;
			case 1:
			case 2:
				return Date.class;
			case 3:
			case 4:
			case 5:
				return String.class;
			default:
				throw new IllegalArgumentException("columnIndex");
		}
	}
}
