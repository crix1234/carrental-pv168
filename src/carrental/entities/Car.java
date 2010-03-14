/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package carrental.entities;

/**
 *
 * @author Pavel Mican
 */
public class Car {
	private int id;
	private String name;
	private String licencePlate;
	private String state;

	public Car(int id, String name, String licencePlate, String state) throws IllegalArgumentException {
		setId(id);
		setName(name);
		setLicencePlate(licencePlate);
		setState(state);
	}

	/**
	 *
	 * @return <code>name</code> String.
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets cars <code>name</code>
	 * @param name not null String
	 * @throws IllegalArgumentException
	 */
	public void setName(String name) throws IllegalArgumentException {
		if (name != null) {
			this.name = name;
		} else {
			throw new IllegalArgumentException("String name of the car can not be set to null.");
		}
	}

	/**
	 * @return <code>licencePlate</code> String
	 */
	public String getLicencePlate() {
		return licencePlate;
	}

	/**
	 * sets cars <code>licencePlate</code>
	 * @param licencePlate not null String
	 * @throws IllegalArgumentException
	 */
	public void setLicencePlate(String licencePlate) throws IllegalArgumentException {
		if (licencePlate != null) {
			this.licencePlate = licencePlate;
		} else {
			throw new IllegalArgumentException("String licencePlate of the car can not be set to null.");
		}
	}


	/**
	 * @return <code>state</code> String
	 */
	public String getState() {
		return state;
	}

	/**
	 * sets cars <code>state</code>
	 * @param state not null String
	 * @throws IllegalArgumentException
	 */
	public void setState(String state) throws IllegalArgumentException {
		if (state != null) {
			this.state = state;
		} else {
			throw new IllegalArgumentException("String state of the car can not be set to null.");
		}
	}

	/**
	 * @return <code>id</code> String
	 */
	public int getId() {
		return id;
	}

	/**
	 * sets cars <code>id</code>. Should not be directly changed.
	 * @param id new unique car id.
	 * @throws IllegalArgumentException defined id is already in use
	 */
	private void setId(int id) {
		//TODO check for id existence
		this.id = id;
	}
}
