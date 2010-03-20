package carrental.managers;

import carrental.entities.Address;
import java.util.Collection;
/**
 *
 * @author Pavel Mican
 */
public interface AddressManager {
	public Address createNewAddress(int houseNumber, String street, String town, String state, String zipcode) throws AddressManagerException;
	public Address createNewAddress(Address address) throws AddressManagerException;
	public void editAddress(Address address) throws AddressManagerException;
	public Address deleteAddress(Address address) throws AddressManagerException, IllegalArgumentException;
	public Address deleteAddress(int id) throws AddressManagerException, IllegalArgumentException;
	public Address findAddressByID(int id) throws AddressManagerException;
	public Collection<Address> findAllAddresses() throws AddressManagerException;
}
