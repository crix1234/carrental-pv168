/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.models;

import carrental.entities.Order;
import carrental.managers.OrderManagerException;
import carrental.managers.OrderManagerImpl;
import carrental.managers.OrderingManagerException;
import carrental.managers.OrderingManagerImpl;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jerrycek
 */
public class OrdersTableModel extends AbstractTableModel {

	private List<Order> orders = new ArrayList<Order>();

	public void loadOrders() {
		OrderManagerImpl omi = new OrderManagerImpl();
		try {
			orders = omi.findAllOrders();
		} catch (OrderManagerException cme) {
			cme.printStackTrace();
		}
	}

	public void addOrder(Order order) {
		if (order != null) {
			orders.add(order);
			fireTableRowsInserted(orders.size(), orders.size());
		}
	}

	/**
	 * order simple deletion - just for simple usage and testing !
	 * WARNING ! There should be more logic implemented!
	 * Simple Order deletion did not inform other tables about the order disappearence
	 * 
	 * @param order
	 */
	public void deleteOrder(Order order) { //TODO - more logical deletion including Ordering table in the database
		if (order != null) {
			OrderManagerImpl omi = new OrderManagerImpl();
			int index = orders.indexOf(order);
			if (index >= 0) {
				try {
					omi.deleteOrder(order);
					orders.remove(index);
					fireTableRowsDeleted(index, index);
				} catch (OrderManagerException ex) {
					Logger.getLogger(OrdersTableModel.class.getName()).log(Level.SEVERE, null, ex);
					JOptionPane.showMessageDialog(null, "Order deletion didn't succeed", "Order error", JOptionPane.ERROR_MESSAGE); //TODO localization
				} catch (IllegalArgumentException ex) {
					Logger.getLogger(OrdersTableModel.class.getName()).log(Level.SEVERE, null, ex);
					JOptionPane.showMessageDialog(null, "Order deletion didn't succeed", "Order error", JOptionPane.ERROR_MESSAGE); //TODO localization
				}
			}
		}
	}

	/**
	 * edits the <code>Order</code> in the table and updates the database
	 * @param order the <code>Order</code> you'd like to place in the position of the original one
	 * @param index the position in <code>orders</code> into which the new <code>Order</code> should be placed
	 */
	public void editOrder(Order order, int index) {
		if (order != null) {
			OrderManagerImpl omi = new OrderManagerImpl();
			try {
				omi.editOrder(order);
				orders.set(index, order);
				fireTableRowsUpdated(index, index);
			} catch (OrderManagerException ex) {
				Logger.getLogger(OrdersTableModel.class.getName()).log(Level.SEVERE, null, ex);
				JOptionPane.showMessageDialog(null, "Order edition didn't succeed", "Order error", JOptionPane.ERROR_MESSAGE); //TODO localization
			} catch (IllegalArgumentException ex) {
				Logger.getLogger(OrdersTableModel.class.getName()).log(Level.SEVERE, null, ex);
				JOptionPane.showMessageDialog(null, "Order edition didn't succeed", "Order error", JOptionPane.ERROR_MESSAGE); //TODO localization
			}
		}
	}

	@Override
	public int getRowCount() {
		return orders.size();
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return Integer.class;
			case 1:
				return Date.class;
			case 2:
				return DateFormat.class;
			case 3:
			case 4:
			case 5:
				return String.class;
			default:
				throw new IllegalArgumentException("columnIndex");
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Order order = orders.get(rowIndex);
		OrderingManagerImpl omi = new OrderingManagerImpl();
		switch (columnIndex) {
			case 0:
				return order.getId();
			case 1: {
				//DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
				//return df.format(order.getBookedFrom());
				return order.getBookedFrom();
			}
			case 2: {
				DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
				return df.format(order.getBookedTo());
			}
			case 3:
				return order.getOrderState();
//			case 4:
//				try {
//					return omi.getCustomerByOrder(order).getName();
//				} catch (OrderingManagerException ex) {
//					Logger.getLogger(OrdersTableModel.class.getName()).log(Level.SEVERE, null, ex);
//				}
//			case 5:
//				try {
//					return omi.getCustomerByOrder(order).getSurname();
//				} catch (OrderingManagerException ex) {
//					Logger.getLogger(OrdersTableModel.class.getName()).log(Level.SEVERE, null, ex);
//				}
			case 4:
			case 5:
				return "null";
			default:
				throw new IllegalArgumentException("columnIndex");
		}
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return "Id";
			case 1:
				return "Booked from";
			case 2:
				return "Booked to";
			case 3:
				return "State";
			case 4:
				return "Customer name";
			case 5:
				return "Customer surname";
			default:
				throw new IllegalArgumentException("columnIndex");
		}
	}
}
