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
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jerrycek
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
}
