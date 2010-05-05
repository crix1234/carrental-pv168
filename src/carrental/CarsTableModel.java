/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental;

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
public class CarsTableModel extends AbstractTableModel {

	private List<Car> cars = new ArrayList<Car>();

	//public void addCar(Car car) {
	//	cars.add(car);
	//}
	public void loadCars() {
		CarManagerImpl cmi = new CarManagerImpl();
		try {
			cars = cmi.findAllCars();
		} catch (CarManagerException cme) {
			cme.printStackTrace();
		}
	}

	@Override
	public int getRowCount() {
		return cars.size();
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
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
				return "Id";
			case 1:
				return "Name";
			case 2:
				return "Licence plate";
			case 3:
				return "State";
			case 4:
				return "Type";
			default:
				throw new IllegalArgumentException("columnIndex");
		}
	}
}
