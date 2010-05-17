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

	/**
	 * 
	 * @param id
	 * @param houseNumber
	 * @param street
	 * @param town
	 * @param state
	 * @param zipcode
	 * @throws IllegalArgumentException
	 */
	public Address(int id, int houseNumber, String street, String town, String state, String zipcode) throws IllegalArgumentException {
		setId(id);
		setHouseNumber(houseNumber);
		setStreet(street);
		setTown(town);
		setState(state);
		setZipcode(zipcode);
	}

	public Address(Address addr) {
		setId(addr.getId());
		setHouseNumber(addr.getHouseNumber());
		setStreet(addr.getStreet());
		setTown(addr.getTown());
		setState(addr.getState());
		setZipcode(addr.getZipcode());
	}

	/**
	 * @return <code>id</code> integer
	 */
	public int getId() {
		return id;
	}

	/**
	 * sets address <code>id</code>. Should not be directly changed.
	 * @param id new unique address id.
	 * @throws IllegalArgumentException defined id is out of range (id >= 0)
	 */
	private void setId(int id) throws IllegalArgumentException {
		if (id >= 0) {
			this.id = id;
		} else {
			throw new IllegalArgumentException("ID should be positive integer.");
		}
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
	public void setHouseNumber(int houseNumber) throws IllegalArgumentException {
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
	 * sets address <code>street</code>. Maximum length of the given <code>String</code>
	 * is defined earlier. Larger names will be shortened up to this length.
	 * @param street String representation of real houses street
	 * @throws IllegalArgumentException <code>street</code> should not be null
	 */
	public void setStreet(String street) throws IllegalArgumentException {
		if (street != null) {
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
	public void setTown(String town) throws IllegalArgumentException {
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
	 * sets address <code>state</code>. Maximum length of the given <code>String</code>
	 * is defined earlier. Larger names will be shortened up to this length.
	 * @param zipcode String representation of real state
	 * @throws IllegalArgumentException <code>state</code> should not be null
	 */
	public void setState(String state) throws IllegalArgumentException {
		if (state != null) {
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
	public void setZipcode(String zipcode) throws IllegalArgumentException {
		if (zipcode != null) {
			this.zipcode = zipcode;
		} else {
			throw new IllegalArgumentException("String zipcode can not be set to null.");
		}
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 29 * hash + this.id;
		hash = 29 * hash + this.houseNumber;
		hash = 29 * hash + (this.street != null ? this.street.hashCode() : 0);
		hash = 29 * hash + (this.town != null ? this.town.hashCode() : 0);
		hash = 29 * hash + (this.state != null ? this.state.hashCode() : 0);
		hash = 29 * hash + (this.zipcode != null ? this.zipcode.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Address other = (Address) obj;
		if (this.id != other.id) {
			return false;
		}
		if (this.houseNumber != other.houseNumber) {
			return false;
		}
		if ((this.street == null) ? (other.street != null) : !this.street.equals(other.street)) {
			return false;
		}
		if ((this.town == null) ? (other.town != null) : !this.town.equals(other.town)) {
			return false;
		}
		if ((this.state == null) ? (other.state != null) : !this.state.equals(other.state)) {
			return false;
		}
		if ((this.zipcode == null) ? (other.zipcode != null) : !this.zipcode.equals(other.zipcode)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Address: ID: " + id + ", House number: " + houseNumber +
				", Street: " + street + ", Town: " + town +
				", State: " + state + ", Zipcode: " + zipcode;
	}

}
