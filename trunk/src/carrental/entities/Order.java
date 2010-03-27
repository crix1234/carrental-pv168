package carrental.entities;

import java.util.Date;

/**
 *
 * @author Matej Cizik
 */
public class Order {

	private int id;
	private Date bookedFrom;
	private Date bookedTo;
	private String orderState;

	public Order(int id, Date bookedFrom, Date bookedTo, String orderState) {
		setId(id);
		setBookedFrom(bookedFrom);
		setBookedTo(bookedTo);
		setOrderState(orderState);
	}

	/**
	 * sets orders <code>bookedFrom</code>
	 * @param name not null Date
	 * @throws IllegalArgumentException
	 */
	public void setBookedFrom(Date bookedFrom) throws IllegalArgumentException {
		if (bookedFrom != null) {
			this.bookedFrom = bookedFrom;
		} else {
			throw new IllegalArgumentException("Date bookedFrom can not be set to null.");
		}
	}

	/**
	 *
	 * @return <code>bookedFrom</code> Date.
	 */
	public Date getBookedFrom() {
		return bookedFrom;
	}

	/**
	 * sets orders <code>bookedTo</code>
	 * @param name not null Date
	 * @throws IllegalArgumentException
	 */
	public void setBookedTo(Date bookedTo) throws IllegalArgumentException {
		if (bookedTo != null) {
			this.bookedTo = bookedTo;
		} else {
			throw new IllegalArgumentException("Date bookedTo can not be set to null.");
		}
	}

	/**
	 *
	 * @return <code>bookedTo</code> Date.
	 */
	public Date getBookedTo() {
		return bookedTo;
	}

	/**
	 * sets orders <code>orderState</code>
	 * @param name not null String
	 * @throws IllegalArgumentException
	 */
	public void setOrderState(String orderState) throws IllegalArgumentException {
		if (orderState != null) {
			this.orderState = orderState;
		} else {
			throw new IllegalArgumentException("String orderstate can not be set to null.");
		}
	}

	/**
	 *
	 * @return <code>orderState</code> String.
	 */
	public String getOrderState() {
		return orderState;
	}

	/**
	 * @return <code>id</code> integer
	 */
	public int getId() {
		return id;
	}

	/**
	 * sets orders <code>id</code>. Should not be directly changed.
	 * @param id new unique order id.
	 * @throws IllegalArgumentException defined id is already in use
	 */
	private void setId(int id) {
		//TODO check for id existence
		this.id = id;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 29 * hash + this.id;
		hash = 29 * hash + (this.bookedFrom != null ? this.bookedFrom.hashCode() : 0);
		hash = 29 * hash + (this.bookedTo != null ? this.bookedTo.hashCode() : 0);
		hash = 29 * hash + (this.orderState != null ? this.orderState.hashCode() : 0);
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
		final Order other = (Order) obj;
		if (this.id != other.id) {
			return false;
		}
		if (this.bookedFrom != other.bookedFrom && (this.bookedFrom == null || !this.bookedFrom.equals(other.bookedFrom))) {
			return false;
		}
		if (this.bookedTo != other.bookedTo && (this.bookedTo == null || !this.bookedTo.equals(other.bookedTo))) {
			return false;
		}
		if ((this.orderState == null) ? (other.orderState != null) : !this.orderState.equals(other.orderState)) {
			return false;
		}
		return true;
	}



	@Override
	public String toString() {
		return "Order: ID: " + id + ", Booked from: " + bookedFrom +
				", Booked to: " + bookedTo + ", Order state: " + orderState + ".";
	}
}
