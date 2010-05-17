/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.models;

import carrental.entities.Order;
import carrental.managers.OrderManagerException;
import carrental.managers.OrderManagerImpl;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jerrycek
 */
public class OrdersTableModel extends AbstractTableModel {

	private List<Order> orders = new ArrayList<Order>();

	//public void addCar(Car car) {
	//	cars.add(car);
	//}
	public void loadOrders() {
		OrderManagerImpl omi = new OrderManagerImpl();
		try {
			orders = omi.findAllOrders();
		} catch (OrderManagerException cme) {
			cme.printStackTrace();
		}
	}

	@Override
	public int getRowCount() {
		return orders.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
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
				return String.class;
			default:
				throw new IllegalArgumentException("columnIndex");
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Order order = orders.get(rowIndex);
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
			default:
				throw new IllegalArgumentException("columnIndex");
		}
	}
}
