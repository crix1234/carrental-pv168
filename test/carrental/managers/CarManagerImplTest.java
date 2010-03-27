package carrental.managers;

import carrental.entities.Car;
import carrental.entities.CarType;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Matej Cizik
 */
public class CarManagerImplTest {

	public CarManagerImplTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		System.out.println("CAR MANAGER TESTS:");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
		DBManager dbm = new DBManager();
		dbm.connect();
		dbm.dropTable("CAR");
		dbm.disconnect();
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test of createNewCar method, of class CarManagerImpl.
	 */
	@Test
	public void testCreateNewCar_4args() throws Exception {
		System.out.println("createNewCar");
		try {
			String name = "Honda";
			String licencePlate = "KK-392-AI";
			String state = "OK";
			CarType carType = CarType.MICROBUS;
			CarManagerImpl instance = new CarManagerImpl();
			Car expResult = new Car(1, name, licencePlate, state, carType);
			Car result = instance.createNewCar(name, licencePlate, state, carType);
			assertEquals(expResult, result);

			name = "Mazda";
			licencePlate = "BA-392-AI";
			state = "DAMAGED";
			carType = CarType.SPORTCAR;
			instance = new CarManagerImpl();
			expResult = new Car(2, name, licencePlate, state, carType);
			result = instance.createNewCar(name, licencePlate, state, carType);
			assertEquals(expResult, result);

			try {
				result = instance.createNewCar(name, licencePlate, state, carType);
				fail();
			} catch (CarManagerException ex) {
			}

			try {
				result = instance.createNewCar(null);
				fail();
			} catch (CarManagerException ex) {
			}

			try {
				result = instance.createNewCar("12345678901234567890123456789012345678901234567890", "12345678901234567890123456789012345678901234567890", "1234567890123456789012345678901234567890", carType);
				assertEquals(CarManagerImpl.MAXLENGTH_NAME, result.getName().length());
			} catch (CarManagerException ex) {
				fail();
			}

			try {
				result = instance.createNewCar("12345678901234567890123456789012345678901234567890", "012345678901234567890123456789012345678901234567890", "1234567890123456789012345678901234567890", carType);
				assertEquals(CarManagerImpl.MAXLENGTH_LICENCE_PLATE, result.getLicencePlate().length());
			} catch (CarManagerException ex) {
				fail();
			}

			try {
				result = instance.createNewCar("12345678901234567890123456789012345678901234567890", "0012345678901234567890123456789012345678901234567890", "1234567890123456789012345678901234567890", carType);
				assertEquals(CarManagerImpl.MAXLENGTH_STATE, result.getState().length());
			} catch (CarManagerException ex) {
				fail();
			}

		} catch (CarManagerException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Test of createNewCar method, of class CarManagerImpl.
	 */
	@Test
	public void testCreateNewCar_Car() throws Exception {
		System.out.println("createNewCar");
		Car car = new Car(1, "BMW", "BA-123-AB", "TERMINATED", CarType.PERSONAL);
		CarManagerImpl instance = new CarManagerImpl();
		Car expResult = car;
		try {
			Car result = instance.createNewCar(car);
			assertEquals(expResult, result);
		} catch (CarManagerException e) {
			fail();
		}
	}

	/**
	 * Test of editCar method, of class CarManagerImpl.
	 */
	@Test
	public void testEditCar() throws Exception {
		System.out.println("editCar");
		try {
			Car newCar = new Car(1, "BMW", "BA-123-AB", "TERMINATED", CarType.PERSONAL);
			Car newCar2 = new Car(2, "Bugatti Veyron", "PO-728-AR", "OK", CarType.SPORTCAR);
			Car newCar3 = new Car(2, "Bugatti", "PO-722-AR", "DAMAGED", CarType.SPORTCAR);
			Car newCar4 = new Car(22, "Bugatti Veyron", "PO-728-AR", "OK", CarType.SPORTCAR);
			CarManagerImpl instance = new CarManagerImpl();
			instance.createNewCar(newCar);
			instance.createNewCar(newCar2);
			//edit existing car
			try {
				instance.editCar(newCar3);
			} catch (CarManagerException e) {
				fail();
			}
			//edit nonexisting car
			try {
				instance.editCar(newCar4);
				fail();
			} catch (IllegalArgumentException e) {
			}
		} catch (CarManagerException ex) {
			ex.printStackTrace();
			fail();
		}
	}

	/**
	 * Test of findAllCars method, of class CarManagerImpl.
	 */
	@Test
	public void testFindAllCars() throws Exception {
		System.out.println("findAllCars");
		try {
			CarManagerImpl instance = new CarManagerImpl();
			ArrayList<Car> expResult = new ArrayList<Car>();
			ArrayList<Car> result = instance.findAllCars();
			assertEquals(expResult, result);

			Car newCar = new Car(1, "BMW", "BA-123-AB", "TERMINATED", CarType.PERSONAL);
			Car newCar2 = new Car(2, "Bugatti Veyron", "PO-728-AR", "OK", CarType.SPORTCAR);
			Car newCar3 = new Car(3, "Bugatti", "PO-722-AR", "DAMAGED", CarType.SPORTCAR);
			expResult.add(newCar);
			expResult.add(newCar2);
			expResult.add(newCar3);
			instance.createNewCar(newCar);
			instance.createNewCar(newCar2);
			instance.createNewCar(newCar3);
			result = instance.findAllCars();
			assertEquals(newCar, result.get(0));
			assertEquals(newCar2, result.get(1));
			assertEquals(newCar3, result.get(2));

			assertNotSame(newCar, result.get(1)); //totaly different
			assertNotSame(newCar2, result.get(2)); //totaly different
			assertNotSame(newCar3, result.get(0)); //totaly different
		} catch (CarManagerException ex) {
			ex.printStackTrace();
			fail();
		}

	}

	/**
	 * Test of findCarById method, of class CarManagerImpl.
	 */
	@Test
	public void testFindCarById() throws Exception {
		System.out.println("findCarById");
		try {
			int id = 0;
			CarManagerImpl instance = new CarManagerImpl();
			Car expResult = null;
			try {
				Car result = instance.findCarById(id);
				fail();
			} catch (IllegalArgumentException e) {
			}

			id = 1;
			Car car = new Car(1, "BMW", "BA-123-AB", "TERMINATED", CarType.PERSONAL);
			Car car2 = new Car(2, "Bugatti Veyron", "PO-728-AR", "OK", CarType.SPORTCAR);
			Car car3 = new Car(3, "Bugatti", "PO-722-AR", "DAMAGED", CarType.SPORTCAR);
			instance.createNewCar(car);
			instance.createNewCar(car2);
			instance.createNewCar(car3);
			Car result = instance.findCarById(1);
			Car result2 = instance.findCarById(2);
			Car result3 = instance.findCarById(3);
			assertEquals(car, result);
			//not in database
			result = instance.findCarById(4);
			assertNull(result);
			//in database
			assertEquals(car2, result2);
			assertEquals(car3, result3);
			assertNotSame(car3, result2);
			assertNotSame(car3, result);
		} catch (CarManagerException ex) {
			ex.printStackTrace();
			fail();
		}
	}

	/**
	 * Test of findCarByState method, of class CarManagerImpl.
	 */
	@Test
	public void testFindCarByState() throws Exception {
		System.out.println("findCarByState");
		try {
			String state = "";
			CarManagerImpl instance = new CarManagerImpl();
			ArrayList<Car> expResult = new ArrayList<Car>();
			ArrayList<Car> result = instance.findCarByState(state);
			assertEquals(expResult, result);

			Car car = new Car(1, "BMW", "BA-123-AB", "TERMINATED", CarType.PERSONAL);
			Car car2 = new Car(2, "Bugatti Veyron", "PO-728-AR", "OK", CarType.SPORTCAR);
			Car car3 = new Car(3, "Bugatti", "PO-722-AR", "OK", CarType.SPORTCAR);
			instance.createNewCar(car);
			instance.createNewCar(car2);
			instance.createNewCar(car3);
			result = instance.findCarByState("TERMINATED");
			expResult.add(car);
			assertEquals(expResult, result);
			result = instance.findCarByState("OK");
			expResult.clear();
			expResult.add(car2);
			expResult.add(car3);
			assertEquals(expResult, result);
		} catch (CarManagerException ex) {
			ex.printStackTrace();
			fail();
		}
	}

	/**
	 * Test of findCarByName method, of class CarManagerImpl.
	 */
	@Test
	public void testFindCarByName() throws Exception {
		System.out.println("findCarByName");
		try {
			String name = "";
			CarManagerImpl instance = new CarManagerImpl();
			Car expResult = null;
			Car result = instance.findCarByName(name);
			assertEquals(expResult, result);

			Car car = new Car(1, "BMW", "BA-123-AB", "TERMINATED", CarType.PERSONAL);
			Car car2 = new Car(2, "Bugatti Veyron", "PO-728-AR", "OK", CarType.SPORTCAR);
			Car car3 = new Car(3, "Bugatti", "PO-722-AR", "OK", CarType.SPORTCAR);
			instance.createNewCar(car);
			instance.createNewCar(car2);
			instance.createNewCar(car3);
			result = instance.findCarByName("BMW");
			expResult = car;
			assertEquals(expResult, result);
			result = instance.findCarByName("Bugatti");
			expResult = car3;
			assertEquals(expResult, result);
			//not in database
			result = instance.findCarByName("Ferrari");
			assertNull(result);
		} catch (CarManagerException ex) {
			ex.printStackTrace();
			fail();
		}
	}

	/**
	 * Test of deleteCar method, of class CarManagerImpl.
	 */
	@Test
	public void testDeleteCar_Car() throws Exception {
		System.out.println("deleteCar");
		try {
			Car car = null;
			CarManagerImpl instance = new CarManagerImpl();
			Car expResult = null;
			try {
				Car result = instance.deleteCar(car);
				fail();
			} catch (IllegalArgumentException e) {
			}
			car = new Car(1, "BMW", "BA-123-AB", "TERMINATED", CarType.PERSONAL);
			Car car2 = new Car(2, "Bugatti Veyron", "PO-728-AR", "OK", CarType.SPORTCAR);
			instance.createNewCar(car);
			Car result = instance.deleteCar(car);
			expResult = car;
			assertEquals(expResult, result);
			ArrayList<Car> list = instance.findAllCars();
			ArrayList<Car> expList = new ArrayList<Car>();
			assertEquals(expList, list);

			DBManager dbm = new DBManager();
			dbm.connect();
			dbm.dropTable("CAR");
			dbm.disconnect();

			instance.createNewCar(car);
			instance.createNewCar(car2);
			result = instance.deleteCar(car2);
			expResult = car2;
			assertEquals(expResult, result);
			list = instance.findAllCars();
			expList = new ArrayList<Car>();
			expList.add(car);
			assertEquals(expList, list);

		} catch (CarManagerException ex) {
			ex.printStackTrace();
			fail();
		}
	}

	/**
	 * Test of deleteCar method, of class CarManagerImpl.
	 */
	@Test
	public void testDeleteCar_int() throws Exception {
		System.out.println("deleteCar");
		try {
			int id = 0;
			CarManagerImpl instance = new CarManagerImpl();
			try {
				Car result = instance.deleteCar(id);
				fail();
			} catch (IllegalArgumentException e) {
			}
			Car result = instance.deleteCar(2);
			assertNull(result);

			Car car = new Car(1, "BMW", "BA-123-AB", "TERMINATED", CarType.PERSONAL);
			Car car2 = new Car(2, "Bugatti Veyron", "PO-728-AR", "OK", CarType.SPORTCAR);
			instance.createNewCar(car);
			instance.createNewCar(car2);
			result = instance.deleteCar(1);
			assertEquals(car, result);
			ArrayList<Car> expList = new ArrayList<Car>();
			expList.add(car2);
			ArrayList<Car> list = instance.findAllCars();
			assertEquals(expList, list);

			result = instance.deleteCar(1);
			assertNull(result);
			result = instance.deleteCar(2);
			Car car3 = new Car(2, "Bugatti Veyron", "PO-728-AR", "OK", CarType.SPORTCAR);
			assertEquals(car3, result);
			expList.clear();
			list = instance.findAllCars();
			assertEquals(expList, list);
		} catch (CarManagerException ex) {
			ex.printStackTrace();
			fail();
		}
	}
}
