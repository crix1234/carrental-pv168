package carrental.managers;

import carrental.entities.Car;
import carrental.entities.CarType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Matej Cizik
 */
public class CarManagerImpl implements CarManager {

	public static final int MAXLENGTH_NAME = 40;
	public static final int MAXLENGTH_LICENSE_PLATE = 40;
	public static final int MAXLENGTH_STATE = 40;

	/**
	 * Creates new <code>Car</code> and saves it into the database
	 *
	 * @param name String containing car name. Longer than database capacity names will be reduced
	 * @param licensePlate String containing car licensePlate. Longer than database capacity names will be reduced
	 * @param state String containing car state. Longer than database capacity names will be reduced
	 * @param carType type of the car
	 * @return Car generated <code>Car</code> reflecting input parameters;
	 *         null if <code>Car</code> generating was unsuccessfull
	 * @throws CarManagerException if car creating was unsuccessfull
	 */
	public Car createNewCar(String name, String licensePlate, String state, CarType carType) throws CarManagerException {
		//initialize db connection
		name = DBManager.reduceLongString(name, MAXLENGTH_NAME);
		licensePlate = DBManager.reduceLongString(licensePlate, MAXLENGTH_LICENSE_PLATE);
		state = DBManager.reduceLongString(state, MAXLENGTH_STATE);
		DBManager db = new DBManager();
		Car car = null;
		if (db.connect()) { //connecting to the database was successfull
			if (createTable(db)) { //TODO remove table creation on createNewAddress call and replace it by database initialisation at program start up
				int id = -1;
				PreparedStatement st = db.getInsertIntoTableStatement("CARS", "name", "licensePlate", "state", "carType");
				try {
					st.clearParameters();
					st.setString(1, name);
					st.setString(2, licensePlate);
					st.setString(3, state);
					st.setString(4, carType.name());
					st.executeUpdate();
					ResultSet results = st.getGeneratedKeys();
					if (results.next()) {
						id = results.getInt(1);
					}
				} catch (SQLException ex) {
					throw new CarManagerException(ex);
				}
				try {
					car = new Car(id, name, licensePlate, state, carType);
				} catch (IllegalArgumentException ex) {
					//TODO sql Address insertion succeeded, but class creation doesnt so it's necessarry to remove created row from the database again;
					throw new CarManagerException(ex);
				}
			}
			db.disconnect();
		}
		return car;
	}

	/**
	 * Calls createNewCar with values gathered from given parameter <code>car</code>
	 * @param car <code>Car</code> containing values to be set into the database
	 * @return Car generated <code>Car</code> reflecting input parameters;
	 *         null if <code>Car</code> generating was unsuccessfull
	 * @throws CarManagerException if address creating was unsuccessfull
	 */
	public Car createNewCar(Car car) throws CarManagerException {
		if (car != null) {
			return createNewCar(car.getName(), car.getLicencePlate(), car.getState(), car.getCarType());
		} else {
			throw new CarManagerException("createNewCar argument should be an existing instance of Car, not null.");
		}
	}

	/**
	 * Edits existing <code>Car</code> accessed in the database by ID
	 *
	 * @param newCar <code>Car</code> that should be inserted into the database.
	 * @throws CarManagerException on SQL queries failure
	 * @throws IllegalArgumentException on failure accessing given <code>Car</code>'s <code>id</code> in the database
	 *                                  or <code>id</code> < 1
	 */
	public void editCar(Car newCar) throws CarManagerException, IllegalArgumentException {
		if (newCar.getId() < 1) {
			throw new IllegalArgumentException("Can't find Car with id < 1");
		}
		//initialize db connection
		DBManager db = new DBManager();
		if (db.connect()) { //connecting to the database was successfull
			if (db.tableExists("CAR")) {
				try {
					PreparedStatement st = db.getUpdateTableStatement("CAR", "name", "licensePlate", "state", "carType");
					st.setString(1, newCar.getName());
					st.setString(2, newCar.getLicencePlate());
					st.setString(3, newCar.getState());
					st.setString(4, newCar.getCarType().name());
					st.setInt(5, newCar.getId()); //sets ID value into the statement condition (WHERE ID = ?)
					int updates = st.executeUpdate();
					if (updates < 1) {
						throw new IllegalArgumentException("Given ID was not found during update");
					}
				} catch (SQLException ex) {
					throw new CarManagerException(ex);
				}
				return;
			}
			throw new CarManagerException("Could not find CAR table.");
		}
		throw new CarManagerException("Database connection was not reached.");
	}

	/**
	 * Finds all <code>Cars</code> in the database and returns the <code>List</code>
	 * containing instances of found <code>Cars</code>.
	 *
	 * @return ArrayList<Car> containing all the found values
	 * @throws CarManagerException on SQL query failure
	 */
	public ArrayList<Car> findAllCars() throws CarManagerException {
		//initialize db connection
		DBManager db = new DBManager();
		ArrayList<Car> car = new ArrayList<Car>();
		if (db.connect()) { //connecting to the database was successfull
			if (db.tableExists("CAR")) {
				PreparedStatement st = db.getSelectFromTableStatement("CAR", "*");
				try {
					ResultSet rs = st.executeQuery();
					car.addAll(getCarFromResultSet(rs));
				} catch (SQLException ex) {
					throw new CarManagerException(ex);
				}
			}
			db.disconnect();
		}
		return car;
	}

	/**
	 * Finds <code>Car</code> with a given <code>id</code> in the database
	 *
	 * @param id <code>Car</code> <code>id</code> in the database
	 * @return <code>Car</code> with a given <code>id</code> or <code>null</code>
	 * @throws CarManagerException on SQL query failure
	 * @throws IllegalArgumentException on <code>id</code> out of range (if <code>id</code> < 1)
	 */
	public Car findCarById(int id) throws CarManagerException, IllegalArgumentException {
		if (id < 1) {
			throw new IllegalArgumentException("Can't find Car with id < 1");
		}
		//initialize db connection
		DBManager db = new DBManager();
		Car car = null;
		if (db.connect()) { //connecting to the database was successfull
			if (db.tableExists("CAR")) {
				PreparedStatement st = db.getSelectFromTableStatement("CAR", "*", "id = "+id);
				try {
					ResultSet rs = st.executeQuery();
					ArrayList<Car> queryResult = getCarFromResultSet(rs);
					if (queryResult.size() > 0) {
						car = queryResult.get(0);
					}
				} catch (SQLException ex) {
					throw new CarManagerException(ex);
				}
			}
		}
		return car;
	}

	/**
	 * Finds all <code>Cars</code> with given state in the database and returns
	 * the <code>List</code> containing instances of found <code>Cars</code>.
	 *
	 * @param state <code>state</code> of wanted cars
	 * @return ArrayList<Car> containing all the found values
	 * @throws CarManagerException on SQL query failure
	 */
	public ArrayList<Car> findCarByState(String state) throws CarManagerException {
		//initialize db connection
		DBManager db = new DBManager();
		ArrayList<Car> car = new ArrayList<Car>();
		if (db.connect()) { //connecting to the database was successfull
			if (db.tableExists("CAR")) {
				PreparedStatement st = db.getSelectFromTableStatement("CAR", "*", "state = "+state);
				try {
					ResultSet rs = st.executeQuery();
					car.addAll(getCarFromResultSet(rs));
				} catch (SQLException ex) {
					throw new CarManagerException(ex);
				}
			}
		}
		return car;
	}

	/**
	 * Finds <code>Car</code> with a given <code>name</code> in the database
	 *
	 * @param name <code>Car</code> <code>name</code> in the database
	 * @return <code>Car</code> with a given <code>name</code> or <code>null</code>
	 * @throws CarManagerException on SQL query failure
	 */
	public Car findCarByName(String name) throws CarManagerException {
		//initialize db connection
		DBManager db = new DBManager();
		Car car = null;
		if (db.connect()) { //connecting to the database was successfull
			if (db.tableExists("CAR")) {
				PreparedStatement st = db.getSelectFromTableStatement("CAR", "*", "name = "+name);
				try {
					ResultSet rs = st.executeQuery();
					ArrayList<Car> queryResult = getCarFromResultSet(rs);
					if (queryResult.size() > 0) {
						car = queryResult.get(0);
					}
				} catch (SQLException ex) {
					throw new CarManagerException(ex);
				}
			}
		}
		return car;
	}

	/**
	 * Deletes given <code>Car</code> from the database
	 *
	 * @param car the <code>Car</code> record in the database that should be removed
	 * @return Car representation of the deleted <code>Car</code>
	 * @throws CarManagerException on car deletion failure
	 *         IllegalArgumentException if argument is null or car id < 1
	 */
	public Car deleteCar(Car car) throws CarManagerException, IllegalArgumentException {
		if (car != null) {
			return deleteCar(car.getId());
		} else {
			throw new IllegalArgumentException("Can't delete car that is null");
		}
	}

	/**
	 * Deletes <code>Car</code> with the given id from the database
	 *
	 * @param id the <code>id</code> of the <code>Car</code> record in the database
	 *           that should be removed
	 * @return Car representation of the deleted <code>Car</code>
	 * @throws CarManagerException on car deletion failure
	 *         IllegalArgumentException if argument is null or car id < 1
	 */
	public Car deleteCar(int id) throws CarManagerException, IllegalArgumentException {
		if (id > 0) {
			Car deletedCar = findCarById(id);
			if (deletedCar != null) {
				//initialize db connection
				DBManager db = new DBManager();
				if (db.connect()) { //connecting to the database was successfull
					try {
						if (db.deleteRow("CAR", id) == 0) {
							return null;	// no car was actually deleted
						}
					} catch (SQLException ex) {
						throw new CarManagerException(ex);
					}
				}
			}
			return deletedCar;
		} else {
			throw new IllegalArgumentException("Car id should be positive integer!");
		}
	}

	/**
	 * creates new table if it's not already in the database
	 *
	 * @param db <code>DBManager</code> that handles db connection and table creation
	 * @return true if successfull creation;
	 *         false if the database respond was unsuccessfull for some reason
	 */
	private static final boolean createTable(DBManager db) {
		String columns = "ID				INTEGER NOT NULL" +
						 "					PRIMARY KEY GENERATED ALWAYS AS IDENTITY" +
						 "					(START WITH 1, INCREMENT BY 1)," +
						 "name				VARCHAR(" + MAXLENGTH_NAME + ")," +
						 "licensePlate		VARCHAR(" + MAXLENGTH_LICENSE_PLATE + ")," +
					     "state				VARCHAR(" + MAXLENGTH_STATE + ")," +
						 "carType			VARCHAR";
		return db.createTable("CAR", columns);
	}

	/**
	 * Handles creating <code>Car</code> instances from database's <code>ResultSet</code>
	 *
	 * @param rs <code>ResultSet</code> retreaved from the previous database query
	 * @return ArrayList<Car> of all retreaved addresses
	 * @throws SQLException if reading arguments fails
	 */
	private static final ArrayList<Car> getCarFromResultSet(ResultSet rs) throws SQLException {
		ArrayList<Car> cars = new ArrayList<Car>();
		Car newCar;
		while (rs.next()) {
			newCar = new Car(rs.getInt("ID"),
					rs.getString("name"),
					rs.getString("licencePlate"),
					rs.getString("state"),
					CarType.valueOf(rs.getString("carType")));
			cars.add(newCar);
		}
		return cars;
	}
}
