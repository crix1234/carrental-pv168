package carrental.managers;

import carrental.entities.Car;
import carrental.entities.CarType;
import java.util.ArrayList;

/**
 *
 * @author SirGlorg
 */
public interface CarManager {
	public Car createNewCar(String name, String licensePlate, String state, CarType carType) throws CarManagerException;
	public void editCar(Car newCar) throws CarManagerException, IllegalArgumentException;
	public ArrayList<Car> findAllCars() throws CarManagerException;
	public Car findCarById(int id) throws CarManagerException, IllegalArgumentException;
	public ArrayList<Car> findCarByState(String state) throws CarManagerException;
	public Car findCarByName(String name) throws CarManagerException;
        public void deleteCar(int id) throws CarManagerException, IllegalArgumentException;
}
