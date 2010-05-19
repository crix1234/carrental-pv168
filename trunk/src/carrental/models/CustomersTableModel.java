/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.models;

import carrental.entities.Customer;
import carrental.managers.CustomerManagerException;
import carrental.managers.CustomerManagerImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jerrycek & Pavel Mican
 */
public class CustomersTableModel extends AbstractTableModel {

	private List<Customer> customers = new ArrayList<Customer>();

	@Override
	public int getRowCount() {
		return customers.size();
	}

	@Override
	public int getColumnCount() {
		return 8;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Customer customer = customers.get(rowIndex);
		switch (columnIndex) {
			case 0:
				return customer.getId();
			case 1:
				return customer.getName();
			case 2:
				return customer.getSurname();
			case 3:
				return customer.getAddress().getStreet();
			case 4:
				return customer.getAddress().getHouseNumber();
			case 5:
				return customer.getAddress().getTown();
			case 6:
				return customer.getAddress().getState();
			case 7:
				return customer.getAddress().getZipcode();
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
				return "Jméno";
			case 2:
				return "Příjmení";
			case 3:
				return "Ulice";
			case 4:
				return "Číslo domu";
			case 5:
				return "Město";
			case 6:
				return "Stát";
			case 7:
				return "PSČ";
			default:
				throw new IllegalArgumentException("columnIndex");
		}
	}

	public void loadCustomers() {
		CustomerManagerImpl cmi = new CustomerManagerImpl();
		try {
			customers = cmi.findAllCustomers();
		} catch (CustomerManagerException cme) {
			cme.printStackTrace();
		}
	}

	public void actualize() {
		fireTableRowsInserted(customers.size(),customers.size());
	}

	public void addCustomer(Customer customer) {
		if (customer != null) {
			customers.add(customer);
			this.fireTableDataChanged();
		}
	}

	/**
	 * Deletes <code>Customer</code> from the database and the table
	 * @param customer the <code>Customer</code>, that should be deleted
	 * @throws CustomerManagerException on customer deletion failure
	 * @throws IllegalArgumentException on customer id lower than 1
	 */
	public void deleteCustomer(Customer customer) throws CustomerManagerException,IllegalArgumentException {
		if (customer != null) {
			CustomerManagerImpl cmi = new CustomerManagerImpl();
			int index = customers.indexOf(customer);
			if (index >=0) {
				cmi.deleteCustomer(customer);
				customers.remove(index);
				this.fireTableDataChanged();
			}
		}
	}

	/**
	 * edits the <code>Customer</code> in the table and updates the database
	 * @param customer the <code>Custoer</code> you'd like to place in the position of the original one
	 * @param index the position in <code>customers</code> into which the new <code>Customer</code> should be placed
	 * @throws CustomerManagerException on SQL queries failure
	 * @throws IllegalArgumentException on failure accessing given customers <code>id</code> in the database or id lower than 1
	 */
	public void editCustomer(Customer customer, int index) throws CustomerManagerException, IllegalArgumentException {
		if (customer != null) {
			CustomerManagerImpl cmi = new CustomerManagerImpl();
			cmi.editCustomer(customer); //throws  CustomerManagerException,IllegalArgumentException
			customers.set(index,customer);
			fireTableRowsUpdated(index, index);
		}
	}

	/**
	 * gets the customer placed in the specific <code>row</code>
	 * @param row the row in which the wanted <code>Customer</code> is placed
	 * @return Customer placed in the selected row
	 * @throws ArrayIndexOutOfBoundsException if the row is out of bounds
	 *         ie. lower than 0 or bigger than rowsCount - 1
	 */
	public Customer getCustomer(int row) {
		if ( (row > customers.size()) || (row < 0) ) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return customers.get(row);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return Integer.class;
			case 1:
			case 2:
			case 3:
				return String.class;
			case 4:
				return Integer.class;
			case 5:
			case 6:
			case 7:
				return String.class;
			default:
				throw new IllegalArgumentException("columnIndex");
		}
	}
}
