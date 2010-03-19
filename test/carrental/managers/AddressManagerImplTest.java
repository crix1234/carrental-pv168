package carrental.managers;

import carrental.entities.Address;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
		//initialize database
		initializeDatabase();
		//new addresses generation
		AddressManagerImpl addm = new AddressManagerImpl();
		try {
			Address addr1 = addm.createNewAddress(13, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
			Address addr2 = addm.createNewAddress(234, "Elisky Krasnohorske", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
			Address addr3 = addm.createNewAddress(157, "Kluka Chlupateho", "Tábor", "Čechy", "123 48");
			Address addr4 = addm.createNewAddress(77400, "Žluťouličatá řepa", "Šílené koňské měchy", "Bangladéš", "238 88");

			Address expResult = new Address(1,13, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
			assertEquals(expResult, addr1);
			expResult = new Address(2,234, "Elisky Krasnohorske", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
			assertEquals(expResult, addr2);
			expResult = new Address(3,157, "Kluka Chlupateho", "Tábor", "Čechy", "123 48");
			assertEquals(expResult, addr3);
			expResult = new Address(4,77400, "Žluťouličatá řepa", "Šílené koňské měchy", "Bangladéš", "238 88");
			assertEquals(expResult, addr4);

			//create address with passing Address instance
			expResult = new Address(5,234, "Elisky Krasnohorske", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
			addr1 = addm.createNewAddress(expResult);
			assertEquals(expResult, addr1);

			try {
				addr1 = addm.createNewAddress(null);
				fail();
			} catch (AddressManagerException e) {
				e.printStackTrace();
			}

			try {
				addr1 = addm.createNewAddress(-3,  "Elisky Krasnohorske", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
				fail();
			} catch (AddressManagerException e) {
			}
			try {
				addr1 = addm.createNewAddress(0,  "Elisky Krasnohorske", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
				fail();
			} catch (AddressManagerException e) {
			}
			try {
				addr1 = addm.createNewAddress(23, null, null, null, null);
				fail();
			} catch (AddressManagerException e) {
			}
			try {
				addr1 = addm.createNewAddress(23, "abbcdd", null, "hugachagga", "ajeje");
				fail();
			} catch (AddressManagerException e) {
			}
			try {
				addr1 = addm.createNewAddress(23, "abbcdd", "asdf", null, "ajeje");
				fail();
			} catch (AddressManagerException e) {
			}
			try {
				addr1 = addm.createNewAddress(23, "abbcdd", "asdf", "weuior", null);
				fail();
			} catch (AddressManagerException e) {
			}
			try { //too long argument
				addr1 = addm.createNewAddress(8, "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz", "b", "c", "d");
				assertEquals(AddressManagerImpl.MAXLENGTH_STREET, addr1.getStreet().length());
			} catch (AddressManagerException e){
				fail();
			}
			try { //too long / precise length
				addr1 = addm.createNewAddress(8, "a", "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz", "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz", "12345678901234567890");
				assertEquals(AddressManagerImpl.MAXLENGTH_TOWN, addr1.getTown().length());
				assertEquals(AddressManagerImpl.MAXLENGTH_STATE, addr1.getState().length());
				assertEquals(AddressManagerImpl.MAXLENGTH_ZIPCODE, addr1.getZipcode().length());
			} catch (AddressManagerException e){
				fail();
			}
		} catch (AddressManagerException ex) {
			ex.printStackTrace();
			fail();
		}
	}

	/**
	 * Test of editAddress method, of class AddressManagerImpl.
	 */
	@Test
	public void testEditAddress() {
		//initialize database
		initializeDatabase();
		//new addresses generation
		AddressManagerImpl addm = new AddressManagerImpl();
		try {
			addm.createNewAddress(13, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
			addm.createNewAddress(157, "Kluka Chlupateho", "Tábor", "Čechy", "123 48");
			Address address = new Address(1,77400, "Žluťouličatá řepa", "Šílené koňské měchy", "Bangladéš", "238 88");
			addm.editAddress(address);
			Address result = addm.findAddressByID(1);
			assertEquals(result, address);

			//try to edit non existing address id
			address =  new Address(3,77400, "Žluťouličatá řepa", "Šílené koňské měchy", "Bangladéš", "238 88");
			try {
				addm.editAddress(address);
				fail();
			} catch (IllegalArgumentException ex) {
			}
		} catch (AddressManagerException ex) {
			Logger.getLogger(AddressManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
			fail();
		}
	}

	/**
	 * Test of findAddressByID method, of class AddressManagerImpl.
	 */
	@Test
	public void testFindAddressByID() {
		try {
			//initialize database
			initializeDatabase();
			//new addresses generation
			AddressManagerImpl addm = new AddressManagerImpl();
			Address addr1 = addm.createNewAddress(13, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
			Address addr2 = addm.createNewAddress(234, "Elisky Krasnohorske", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
			addm.createNewAddress(157, "Kluka Chlupateho", "Tábor", "Čechy", "123 48");
			addm.createNewAddress(77400, "Žluťouličatá řepa", "Šílené koňské měchy", "Bangladéš", "238 88");
			//tests
			Address foundAddress = addm.findAddressByID(1);
			assertEquals(addr1, foundAddress);
			assertNotSame(addr2, foundAddress);

			foundAddress = addm.findAddressByID(2);
			assertNotSame(addr1, foundAddress);

			assertNull(addm.findAddressByID(5)); // out of range
			assertNotNull(addm.findAddressByID(4)); //last in range
		} catch (AddressManagerException ex) {
			Logger.getLogger(AddressManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Test of findAllAddresses method, of class AddressManagerImpl.
	 */
	@Test
	public void testFindAllAddresses() {
		try {
			//initialize database
			initializeDatabase();
			//new addresses generation
			AddressManager addm = new AddressManagerImpl();
			Address addr1 = addm.createNewAddress(13, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
			Address addr2 = addm.createNewAddress(234, "Elisky Krasnohorske", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
			Address addr3 = addm.createNewAddress(157, "Kluka Chlupateho", "Tábor", "Čechy", "123 48");
			Address addr4 = addm.createNewAddress(77400, "Žluťouličatá řepa", "Šílené koňské měchy", "Bangladéš", "238 88");
			ArrayList<Address> foundAddresses = (ArrayList<Address>) addm.findAllAddresses();
			//tests
			assertEquals(4, foundAddresses.size());
			assertEquals(addr1, foundAddresses.get(0));
			assertEquals(addr2, foundAddresses.get(1));
			assertEquals(addr3, foundAddresses.get(2));
			assertEquals(addr4, foundAddresses.get(3));
			assertNotSame(addr1, foundAddresses.get(1));
			assertNotSame(addr1, foundAddresses.get(2));
			assertNotSame(addr1, foundAddresses.get(3));
		} catch (AddressManagerException ex) {
			Logger.getLogger(AddressManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private static final void initializeDatabase(){
		DBManager dbm = new DBManager();
		dbm.connect();
		dbm.dropTable("ADDRESS");
		dbm.disconnect();
	}

}