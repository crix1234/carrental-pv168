/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package carrental.managers;
import java.util.Collection;
import carrental.entities.*;

/**
 *
 * @author Pavel Mican
 */

public interface CustomerManager {
	public Car createNewCar();
	public void editCar();
	public Collection<Car> findAllCars();
	public Car findCarById();
	public Car findCarByState();
	public Car findCarByCategory();
	public Car findCarByName();
}