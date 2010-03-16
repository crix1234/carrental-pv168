package carrental.managers;

import carrental.entities.Address;
import java.util.Collection;
/**
 *
 * @author Pavel Mican
 */
public interface AddressManager {
	public Address createNewAddress(int id, int houseNumber, String street, String town, String state, String zipcode);
	public void editAddress(Address address);
	public Address findAddressByID(int id);
	public Collection<Address> findAllAddresses();
}
