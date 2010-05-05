package carrental.managers;

import carrental.entities.Car;
import carrental.entities.CarType;
import java.util.ArrayList;

public class CarManagerDemo {

	public static void main(String args[]) throws Exception {
		CarType type = CarType.VAN;
		CarManagerImpl carMan = new CarManagerImpl();
		Car car = new Car(1, "BMW", "BA-123-AB", "TERMINATED", CarType.PERSONAL);
		//Car car2 = new Car(2, "Bugatti Veyron", "PO-7228-AR", "OK", CarType.SPORTCAR);
		carMan.createNewCar(car);
		//carMan.createNewCar(car2);
		//carMan.deleteCar(1);
		//System.out.println(carMan.findCarById(1).getName().length());
		//carMan.createNewCar(car);
		//Car c = carMan.findCarById(1);
		//System.out.println(c);
		//System.out.println(c.getName());
		//if (c==null) System.out.println("nezmazane");
		//Car car = carMan.createNewCar("Ferrari","KK-391-AJ","kkt",type);
//		ArrayList<Car> list = carMan.findAllCars();
//		for (Car car : list) {
//			System.out.println(car.getName());
//		}

	}
}
