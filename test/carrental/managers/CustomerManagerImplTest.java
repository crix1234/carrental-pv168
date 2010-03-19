package carrental.managers;

import carrental.entities.Address;
import carrental.entities.Customer;
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
public class CustomerManagerImplTest {

    public CustomerManagerImplTest() {
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
	 * Test of createNewCustomer method, of class CustomerManagerImpl.
	 */
	@Test
	public void testCreateNewCustomer() {
		//initialize database
		initializeDatabase();
		//new customer generation
		CustomerManagerImpl custm = new CustomerManagerImpl();
		Address addr1 = new Address(1,13, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		Address addr2 = new Address(2,234, "Elisky Krasnohorske", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		Address addr3 = new Address(3,157, "Kluka Chlupateho", "Tábor", "Čechy", "123 48");
		Address addr4 = new Address(4,77400, "Žluťouličatá řepa", "Šílené koňské měchy", "Bangladéš", "238 88");

		try {
			Customer cust1 = custm.createNewCustomer("Karolina", "Světlá",addr1);
			Customer cust2 = custm.createNewCustomer("Eliška","Krásnohorská",addr2);
			Customer cust3 = custm.createNewCustomer("Klučina","Chlupatý",addr3);
			Customer cust4 = custm.createNewCustomer("Vopičákyně","Zagorová",addr4);

			Customer expResult = new Customer(1,"Karolina", "Světlá",addr1);
			assertEquals(expResult, cust1);
			expResult = new Customer(2,"Eliška","Krásnohorská",addr2);
			assertEquals(expResult, cust2);
			expResult = new Customer(3,"Klučina","Chlupatý",addr3);
			assertEquals(expResult, cust3);
			expResult = new Customer(4,"Vopičákyně","Zagorová",addr4);
			assertEquals(expResult, cust4);

			expResult = new Customer(5,"Vopičákyně","Zagorová",addr1);
			cust2 = custm.createNewCustomer(expResult);
			assertEquals(expResult, cust2);

			try {
				custm.createNewCustomer(null);
				fail();
			} catch (CustomerManagerException e) {
			}

			try {
				cust1 = custm.createNewCustomer(null, null, null);
				fail();
			} catch (CustomerManagerException e) {
			}
			try {
				cust1 = custm.createNewCustomer("íéahécý=+ěcřě19++/19+/*+", null, addr3);
				fail();
			} catch (CustomerManagerException e) {
			}try {
				cust1 = custm.createNewCustomer("&#B)*!@)_!@$LJLJKK? ? L:", "(#@ce n8pqn90//1*/?? ?, null);", null);
				fail();
			} catch (CustomerManagerException e) {
			}
			try { //too long argument
				cust1 = custm.createNewCustomer("01234567890123456789012345678901234567890123456789012345678901234567890123456789", "(#@ce n8pqn90//1*/?? ?, null);", addr4);
				assertEquals(CustomerManagerImpl.MAXLENGTH_NAME, cust1.getName().length());
			} catch (CustomerManagerException e){
				fail();
			}
			try { //too long argument
				cust1 = custm.createNewCustomer("01234567890123456789012345678901234567890123456789012345678901234567890123456789", "01234567890123456789012345678901234567890123456789012345678901234567890123456789",addr2);
				assertEquals(CustomerManagerImpl.MAXLENGTH_SURNAME, cust1.getName().length());
			} catch (CustomerManagerException e){
				fail();
			}

		} catch (CustomerManagerException ex) {
			ex.printStackTrace();
			fail();
		}
	}

	/**
	 * Test of editCustomer method, of class CustomerManagerImpl.
	 */
	@Test
	public void testEditCustomer() {
		System.out.println("editCustomer");
		CustomerManagerImpl instance = new CustomerManagerImpl();
		instance.editCustomer();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of deleteCustomer method, of class CustomerManagerImpl.
	 */
	@Test
	public void testDeleteCustomer() {
		System.out.println("deleteCustomer");
		CustomerManagerImpl instance = new CustomerManagerImpl();
		instance.deleteCustomer();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findCustomerByID method, of class CustomerManagerImpl.
	 */
	@Test
	public void testFindCustomerByID() {
		System.out.println("findCustomerByID");
		int id = 0;
		CustomerManagerImpl instance = new CustomerManagerImpl();
		Customer expResult = null;
		Customer result = instance.findCustomerByID(id);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findCustomerByName method, of class CustomerManagerImpl.
	 */
	@Test
	public void testFindCustomerByName() {
		System.out.println("findCustomerByName");
		String name = "";
		CustomerManagerImpl instance = new CustomerManagerImpl();
		Customer expResult = null;
		Customer result = instance.findCustomerByName(name);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findCustomerBySurname method, of class CustomerManagerImpl.
	 */
	@Test
	public void testFindCustomerBySurname() {
		System.out.println("findCustomerBySurname");
		String surname = "";
		CustomerManagerImpl instance = new CustomerManagerImpl();
		Customer expResult = null;
		Customer result = instance.findCustomerBySurname(surname);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findAllCustomers method, of class CustomerManagerImpl.
	 */
	@Test
	public void testFindAllCustomers() {
		System.out.println("findAllCustomers");
		CustomerManagerImpl instance = new CustomerManagerImpl();
		Collection expResult = null;
		Collection result = instance.findAllCustomers();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}


	private static final void initializeDatabase(){
		DBManager dbm = new DBManager();
		dbm.connect();
		dbm.dropTable("ADDRESS");
		dbm.dropTable("CUSTOMER");
		dbm.disconnect();
	}
}