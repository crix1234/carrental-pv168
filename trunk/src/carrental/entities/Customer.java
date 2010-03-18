package carrental.entities;

/**
 *
 * @author Pavel Mican
 */
public class Customer {
	private int id;
	private String name;
	private String surname;
	private Address address;

	public Customer(int id, String name, String surname, Address address) throws IllegalArgumentException {
		setId(id);
		setName(name);
		setSurname(surname);
		setAddress(address);
	}


	/**
	 * @return <code>id</code> integer
	 */
	public int getId() {
		return id;
	}

	/**
	 * sets customers <code>id</code>. Should not be changed from outside
	 * (Addres id reflects the database image).
	 * @param id new unique car id.
	 * @throws IllegalArgumentException defined id is already in use
	 */
	private void setId(int id) {
		if (id > 0) {
			this.id = id;
		} else {
			throw new IllegalArgumentException("ID should be positive integer.");
		}
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

	/**
	 *
	 * @return <code>address</code> object <code>Address</code>.
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * sets customers <code>surname</code>
	 * @param surname not null String
	 * @throws IllegalArgumentException
	 */
	public void setAddress(Address address) throws IllegalArgumentException {
		if (address != null) {
			this.address = address;
		} else {
			throw new IllegalArgumentException("Customers address can not be set to null.");
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Customer other = (Customer) obj;
		if (this.id != other.id) {
			return false;
		}
		if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
			return false;
		}
		if ((this.surname == null) ? (other.surname != null) : !this.surname.equals(other.surname)) {
			return false;
		}
		if (this.address != other.address && (this.address == null || !this.address.equals(other.address))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 67 * hash + this.id;
		hash = 67 * hash + (this.name != null ? this.name.hashCode() : 0);
		hash = 67 * hash + (this.surname != null ? this.surname.hashCode() : 0);
		hash = 67 * hash + (this.address != null ? this.address.hashCode() : 0);
		return hash;
	}


}
