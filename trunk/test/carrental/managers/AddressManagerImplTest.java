package carrental.managers;

import carrental.entities.Address;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pavel Mican
 */
public class AddressManagerImplTest {

    public AddressManagerImplTest() {
    }

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

	/**
	 * Test of createNewAddress method, of class AddressManagerImpl.
	 */
	@Test
	public void testCreateNewAddress() {
		System.out.println("createNewAddress");
		int houseNumber = 1;
		String street = "a";
		String town = "b";
		String state = "c";
		String zipcode = "d";
		AddressManagerImpl instance = new AddressManagerImpl();
		Address expResult = new Address(1,houseNumber,street,town,state,zipcode);
		Address result = instance.createNewAddress(houseNumber, street, town, state, zipcode);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		//fail("The test case is a prototype.");
	}

	/**
	 * Test of editAddress method, of class AddressManagerImpl.
	 */
	@Test
	public void testEditAddress() {
		System.out.println("editAddress");
		Address address = null;
		AddressManagerImpl instance = new AddressManagerImpl();
		instance.editAddress(address);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findAddressByID method, of class AddressManagerImpl.
	 */
	@Test
	public void testFindAddressByID() {
		System.out.println("findAddressByID");
		int id = 0;
		AddressManagerImpl instance = new AddressManagerImpl();
		Address expResult = null;
		Address result = instance.findAddressByID(id);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findAllAddresses method, of class AddressManagerImpl.
	 */
	@Test
	public void testFindAllAddresses() {
		System.out.println("findAllAddresses");
		AddressManagerImpl instance = new AddressManagerImpl();
		List expResult = null;
		List result = instance.findAllAddresses();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

}