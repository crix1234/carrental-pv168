/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental;

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
				return "Forename";
			case 2:
				return "Surname";
			case 3:
				return "Street";
			case 4:
				return "House number";
			case 5:
				return "Town";
			case 6:
				return "State";
			case 7:
				return "Zipcode";
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
			fireTableRowsInserted(customers.size(),customers.size());
		}
	}

	public void deleteCustomer(Customer customer) {
		if (customer != null) {
			CustomerManagerImpl cmi = new CustomerManagerImpl();
			int index = customers.indexOf(customer);
			if (index >=0) {
				try {
					cmi.deleteCustomer(customer);
					customers.remove(index);
					fireTableRowsDeleted(index, index);
				} catch (CustomerManagerException ex) {
					Logger.getLogger(CustomersTableModel.class.getName()).log(Level.SEVERE, null, ex);
					JOptionPane.showMessageDialog(null, "Customer deletion didn't succeed","Car error",JOptionPane.ERROR_MESSAGE); //TODO localization
				} catch (IllegalArgumentException ex) {
					Logger.getLogger(CustomersTableModel.class.getName()).log(Level.SEVERE, null, ex);
					JOptionPane.showMessageDialog(null, "Customer deletion didn't succeed","Car error",JOptionPane.ERROR_MESSAGE); //TODO localization
				}
			}
		}
	}

	/**
	 * edits the <code>Customer</code> in the table and updates the database
	 * @param customer the <code>Custoer</code> you'd like to place in the position of the original one
	 * @param index the position in <code>customers</code> into which the new <code>Customer</code> should be placed
	 */
	public void editCustomer(Customer customer, int index) {
		if (customer != null) {
			CustomerManagerImpl cmi = new CustomerManagerImpl();
			try {
				cmi.editCustomer(customer);
				customers.set(index,customer);
				fireTableRowsUpdated(index, index);
			} catch (CustomerManagerException ex) {
				Logger.getLogger(CarRentalFrame.class.getName()).log(Level.SEVERE, null, ex);
				JOptionPane.showMessageDialog(null, "Customer edition didn't succeed","Car error",JOptionPane.ERROR_MESSAGE); //TODO localization
			} catch (IllegalArgumentException ex) {
				Logger.getLogger(CarRentalFrame.class.getName()).log(Level.SEVERE, null, ex);
				JOptionPane.showMessageDialog(null, "Customer edition didn't succeed","Car error",JOptionPane.ERROR_MESSAGE); //TODO localization
			}
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
}
