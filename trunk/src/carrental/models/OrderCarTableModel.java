/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.models;

import carrental.entities.Car;
import carrental.managers.CarManagerException;
import carrental.managers.CarManagerImpl;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Jerrycek
 */
public class OrderCarTableModel extends AbstractTableModel {

	private List<Car> cars = new ArrayList<Car>();

	public int getRowCount() {
		return cars.size();
	}

	public int getColumnCount() {
		return 5;
	}

	public void loadCars() {
		CarManagerImpl cmi = new CarManagerImpl();
		List<Car> help = new ArrayList<Car>();
		try {
			help = cmi.findAllCars();
		} catch (CarManagerException cme) {
			cme.printStackTrace();
		}
		for (Car car : help) {
			if (car.getState().equals("Ok")) {
				cars.add(car);
			}
		}
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Car car = cars.get(rowIndex);
		switch (columnIndex) {
			case 0:
				return car.getId();
			case 1:
				return car.getName();
			case 2:
				return car.getLicencePlate();
			case 3:
				return car.getState();
			case 4:
				return car.getCarType();
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
				return "Jméno";
			case 2:
				//return "Licence plate";
				return "SPZ";
			case 3:
				//return "State";
				return "Stav";
			case 4:
				//return "Type";
				return "Typ";
			default:
				throw new IllegalArgumentException("columnIndex");
		}
	}

	public Car getCarAt(int i) {
		return cars.get(i);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case 0:
				return Integer.class;
			case 1:
			case 2:
			case 3:
			case 4:
				return String.class;
			default:
				throw new IllegalArgumentException("columnIndex");
		}
	}
}
