package carrental.managers;

import carrental.entities.Address;
import java.util.ArrayList;
import java.util.Collection;
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
		AddressManager addm = new AddressManagerImpl();
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


			addr1 = addm.createNewAddress(-3,  "Elisky Krasnohorske", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
			assertNull(addr1);
			addr1 = addm.createNewAddress(23, null, null, null, null);
			assertNull(addr1);
			addr1 = addm.createNewAddress(23, "abbcdd", null, "hugachagga", "ajeje");
			assertNull(addr1);
			addr1 = addm.createNewAddress(23, "abbcdd", "asdf", null, "ajeje");
			assertNull(addr1);
			addr1 = addm.createNewAddress(23, "abbcdd", "asdf", "weuior", null);
			assertNull(addr1);
		} catch (AddressManagerException ex) {
			ex.printStackTrace();
		}


		// TODO review the generated test code and remove the default call to fail.
		//fail("The test case is a prototype.");
	}

	/**
	 * Test of editAddress method, of class AddressManagerImpl.
	 */
	@Test
	public void testEditAddress() {
		try {
			Address address = null;
			AddressManagerImpl instance = new AddressManagerImpl();
			instance.editAddress(address);
			// TODO review the generated test code and remove the default call to fail.
			fail("The test case is a prototype.");
		} catch (AddressManagerException ex) {
			Logger.getLogger(AddressManagerImplTest.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Test of findAddressByID method, of class AddressManagerImpl.
	 */
	@Test
	public void testFindAddressByID() {
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
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

	public void initializeDatabase(){
		System.out.println("droping existing address table:");
		DBManager dbm = new DBManager();
		dbm.connect();
		dbm.dropTable("ADDRESS");
		dbm.disconnect();
	}

}