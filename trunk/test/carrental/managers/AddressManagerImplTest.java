package carrental.managers;

import carrental.entities.Address;
import java.util.ArrayList;
import java.util.Collection;
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
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findAllAddresses method, of class AddressManagerImpl.
	 */
	@Test
	public void testFindAllAddresses() {
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
	}

	public void initializeDatabase(){
		System.out.println("droping existing address table:");
		DBManager dbm = new DBManager();
		dbm.connect();
		dbm.dropTable("ADDRESS");
		dbm.disconnect();
	}

}