/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package carrental.entities;

/**
 *
 * @author Pavel Mican
 */
public class Customer {
	private int id;
	private String name;
	private String surname;
	//private Adress adress;

	public Customer(int id, String name, String surname) {
		setId(id);
		setName(name);
		setSurname(surname);
	}


	/**
	 * @return <code>id</code> String
	 */
	public int getId() {
		return id;
	}

	/**
	 * sets customers <code>id</code>. Should not be directly changed.
	 * @param id new unique car id.
	 * @throws IllegalArgumentException defined id is already in use
	 */
	private void setId(int id) {
		//TODO check for id existence
		this.id = id;
	}

	/**
	 *
	 * @return <code>name</code> String.
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets customers <code>name</code>
	 * @param name not null String
	 * @throws IllegalArgumentException
	 */
	public void setName(String name) throws IllegalArgumentException {
		if (name != null) {
			this.name = name;
		} else {
			throw new IllegalArgumentException("String name of the customer can not be set to null.");
		}
	}


	/**
	 *
	 * @return <code>surname</code> String.
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * sets customers <code>surname</code>
	 * @param surname not null String
	 * @throws IllegalArgumentException
	 */
	public void setSurname(String surname) throws IllegalArgumentException {
		if (surname != null) {
			this.surname = surname;
		} else {
			throw new IllegalArgumentException("String surname of the customer can not be set to null.");
		}
	}
}
