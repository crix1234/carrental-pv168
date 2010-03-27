package carrental.managers;

import carrental.entities.Car;
import carrental.entities.CarType;
import java.util.ArrayList;

/**
 *
 * @author Pavel Mican
 */
public interface CarManager {
	public Car createNewCar(String name, String licensePlate, String state, CarType carType) throws CarManagerException;
	public Car createNewCar(Car car) throws CarManagerException;
	public void editCar(Car newCar) throws CarManagerException, IllegalArgumentException;
	public ArrayList<Car> findAllCars() throws CarManagerException;
	public Car findCarById(int id) throws CarManagerException, IllegalArgumentException;
	public ArrayList<Car> findCarByState(String state) throws CarManagerException;
	public Car findCarByName(String name) throws CarManagerException;
    public Car deleteCar(int id) throws CarManagerException, IllegalArgumentException;
	public Car deleteCar(Car car) throws CarManagerException, IllegalArgumentException;
}
