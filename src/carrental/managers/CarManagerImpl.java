/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package carrental.managers;

import carrental.entities.Car;
import java.util.Collection;

/**
 *
 * @author Matej Cizik
 */
public class CarManagerImpl implements CarManager {

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
                DBManager db = new DBManager();
		Car car = null;
		if (db.connect()) { //connecting to the database was successfull
			if (createTable(db)) { //TODO remove table creation on createNewAddress call and replace it by database initialisation at program start up
				int id = -1;
				PreparedStatement st = db.getInsertIntoTableStatement("CARS", "houseNumber", "street", "town", "state", "zipcode");
				try {
					st.clearParameters();
					st.setInt(1, houseNumber);
					st.setString(2, street);
					st.setString(3, town);
					st.setString(4, state);
					st.setString(5, zipcode);
					st.executeUpdate();
					ResultSet results = st.getGeneratedKeys();
					if (results.next()) {
						id = results.getInt(1);
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					throw new AddressManagerException(ex);
				}
				try {
					addr = new Address(id, houseNumber, street, town, state, zipcode);
				} catch (IllegalArgumentException ex) {
					ex.printStackTrace();
					//TODO sql Address insertion succeeded, but class creation doesnt so it's necessarry to remove created row from the database again;
					throw new AddressManagerException(ex);
				}
			}
			db.disconnect();
		}
		return addr;
	}
        }

        /**
         * Edits existing <code>Car</code> accessed in the database by ID
         *
         * @param car <code>Car</code> that should be inserted into the database.
         * @throws CarManagerException on SQL queries failure
         * @throws IllegalArgumentException on failure accessing given <code>Car</code>'s <code>id</code> in the database
	 *                                  or <code>id</code> < 1
         */
	public void editCar(Car car) throws CarManagerException, IllegalArgumentException{

        }

        /**
         * Finds all <code>Cars</code> in the database and returns the <code>List</code>
         * containing instances of found <code>Cars</code>.
         *
         * @return ArrayList<Car> containing all the found values
         * @throws CarManagerException on SQL query failure
         */
	public Collection<Car> findAllCars() throws CarManagerException {

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

        }

        /**
         * Finds all <code>Cars</code> with given state in the database and returns
         * the <code>List</code> containing instances of found <code>Cars</code>.
         *
         * @param state <code>state</code> of wanted cars
         * @return ArrayList<Car> containing all the found values
         * @throws CarManagerException on SQL query failure
         */
	public Collection<Car> findCarByState(String state) throws CarManagerException {
        //TODO ma to vlastne vracat kolekciu, alebo len auto?
        }

        /**
         * Finds <code>Car</code> with a given <code>name</code> in the database
         *
         * @param name <code>Car</code> <code>name</code> in the database
         * @return <code>Car</code> with a given <code>name</code> or <code>null</code>
         * @throws CarManagerException on SQL query failure
         */
	public Car findCarByName(String name) throws CarManagerException {

        }

        /**
         * Deletes <code>Car</code> with the given id from the database
         *
         * @param id the <code>id</code> of the <code>Car</code> record in the database
	 *           that should be removed
         * @throws CarManagerException on car deletion failure
         *         IllegalArgumentException if argument is null or car id < 1
         */
        public void deleteCar(int id) throws CarManagerException {

        }

}
