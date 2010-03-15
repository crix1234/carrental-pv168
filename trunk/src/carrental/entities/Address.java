package carrental.entities;

/**
 *
 * @author Pavel Mican
 */
public class Address {
	private int id;
	private int houseNumber;
	private String street;
	private String town;
	private String state;
	private String zipcode;

	public Address(int id, int homeNumber, String street, String town, String zipcode) {
		setId(id);
	}

	/**
	 * @return <code>id</code> integer
	 */
	public int getId() {
		return id;
	}

	/**
	 * sets address <code>id</code>. Should not be directly changed.
	 * @param id new unique car id.
	 * @throws IllegalArgumentException defined id is already in use
	 */
	private void setId(int id) {
		//TODO check for id existence
		this.id = id;
	}


	/**
	 * @return <code>houseNumber</code> integer
	 */
	public int getHouseNumber() {
		return houseNumber;
	}

	/**
	 * sets address <code>houseNumber</code>.
	 * @param houseNumber integer representation of real house identification
	 * @throws IllegalArgumentException <code>houseNumber</code> should be non-zero positive integer
	 */
	public void setHouseNumber(int houseNumber) {
		if (houseNumber > 0) {
			this.houseNumber = houseNumber;
		} else {
			throw new IllegalArgumentException("String houseNumber can not be zero or negative integer");
		}
	}

	/**
	 * @return <code>street</code> String
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * sets address <code>street</code>.
	 * @param street String representation of real houses street
	 * @throws IllegalArgumentException <code>street</code> should not be null
	 */
	public void setStreet(String street) {
		if (town != null) {
			this.street = street;
		} else {
			throw new IllegalArgumentException("String street can not be set to null.");
		}
	}

	/**
	 * @return <code>town</code> String
	 */
	public String getTown() {
		return town;
	}

	/**
	 * sets address <code>town</code>.
	 * @param town String representation of real house town
	 * @throws IllegalArgumentException <code>town</code> should not be null
	 */
	public void setTown(String town) {
		if (town != null) {
			this.town = town;
		} else {
			throw new IllegalArgumentException("String town can not be set to null.");
		}
	}

	/**
	 * @return <code>state</code> String
	 */
	public String getState() {
		return state;
	}

	/**
	 * sets address <code>state</code>.
	 * @param zipcode String representation of real state
	 * @throws IllegalArgumentException <code>state</code> should not be null
	 */
	public void setState(String state) {
		if (zipcode != null) {
			this.state = state;
		} else {
			throw new IllegalArgumentException("String state can not be set to null.");
		}
	}

	/**
	 * @return <code>zipcode</code> String
	 */
	public String getZipcode() {
		return zipcode;
	}

	/**
	 * sets address <code>zipcode</code>.
	 * @param zipcode String representation of real zipcode
	 * @throws IllegalArgumentException <code>zipcode</code> should not be null
	 */
	public void setZipcode(String zipcode) {
		if (zipcode != null) {
			this.zipcode = zipcode;
		} else {
			throw new IllegalArgumentException("String zipcode can not be set to null.");
		}
	}

}
