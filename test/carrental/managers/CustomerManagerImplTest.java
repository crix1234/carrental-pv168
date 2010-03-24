package carrental.managers;

import carrental.entities.Address;
import carrental.entities.Customer;
import java.util.ArrayList;
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
		initializeDatabase();
    }

    @After
    public void tearDown() {
    }

	/**
	 * Test of createNewCustomer method, of class CustomerManagerImpl.
	 */
	@Test
	public void testCreateNewCustomer() {
		System.out.println("createNewCustomer");
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
			
			//4 address slots are already taken, so next address will have database ID 5; the same applies to the Customer
			addr1 = new Address(5,13, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
			expResult = new Customer(5,"Vopičákyně","Zagorová",addr1);
			cust2 = custm.createNewCustomer(expResult); //test of creating record using Customer instance instead of passing separate arguments
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

		} catch (CustomerManagerException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Test of editCustomer method, of class CustomerManagerImpl.
	 */
	@Test
	public void testEditCustomer() {
		System.out.println("editCustomer");
		//new customer generation
		CustomerManagerImpl custm = new CustomerManagerImpl();
		try {
			Address addr1 = new Address(1,13, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
			Address addr2 = new Address(2,157, "Kluka Chlupateho", "Tábor", "Čechy", "123 48");
			Address addr3 = new Address(1,77400, "Žluťouličatá řepa", "Šílené koňské měchy", "Bangladéš", "238 88");
			custm.createNewCustomer("Sir","Glorg",addr1);
			custm.createNewCustomer("Jára!","Cimrman!!",addr2);
			Customer custommer = new Customer(2,"Nouhwej","Customér",addr3);
			//edit existing customer
			custm.editCustomer(custommer);
			Customer result = custm.findCustomerByID(2);
			assertEquals(result, custommer);

			//try to edit nonexisting Customer ID
			custommer =  new Customer(3,"Nonexisting","Customer",addr2);
			try {
				custm.editCustomer(custommer);
				fail();
			} catch (IllegalArgumentException e) {
			}
		} catch (CustomerManagerException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Test of deleteCustomer method, of class CustomerManagerImpl.
	 */
	@Test
	public void testDeleteCustomer() {
		System.out.println("deleteCustomer");
		//new customer generation
		CustomerManagerImpl custm = new CustomerManagerImpl();
		AddressManagerImpl addrm = new AddressManagerImpl();
		try {
			Address addr1 = new Address(1,13, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
			Address addr2 = new Address(2,157, "Kluka Chlupateho", "Tábor", "Čechy", "123 48");
			Address addr3 = new Address(3,77400, "Žluťouličatá řepa", "Šílené koňské měchy", "Bangladéš", "238 88");
			Customer cust1 = custm.createNewCustomer("Sir","Glorg",addr1);
			Customer cust2 = custm.createNewCustomer("Jára!","Cimrman!!",addr2);
			Customer cust3 = custm.createNewCustomer("Nouhwej","Customér",addr3);
			Customer deleted = custm.deleteCustomer(cust2);
			ArrayList<Customer> results = custm.findAllCustomers();
			assertEquals(2,results.size());
			assertEquals(cust2,deleted);
			assertEquals(cust1, results.get(0));
			assertEquals(cust3, results.get(1));
			assertNotSame(cust2, results.get(0));
			assertNotSame(cust2, results.get(1));
			assertNull(addrm.findAddressByID(addr2.getId()));	//should be deleted too

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Test of findCustomerByID method, of class CustomerManagerImpl.
	 */
	@Test
	public void testFindCustomerByID() {
		System.out.println("findCustomerByID");
		//new customer generation
		CustomerManagerImpl custm = new CustomerManagerImpl();
		try {
			Address addr1 = new Address(1,13, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
			Address addr2 = new Address(2,157, "Kluka Chlupateho", "Tábor", "Čechy", "123 48");
			Address addr3 = new Address(3,77400, "Žluťouličatá řepa", "Šílené koňské měchy", "Bangladéš", "238 88");
			Customer cust1 = new Customer(1,"Sir","Glorg",addr1);
			Customer cust2 = new Customer(2,"Jára!","Cimrman!!",addr2);
			Customer cust3 = new Customer(3,"Nouhwej","Customér",addr3);
			custm.createNewCustomer(cust1);
			custm.createNewCustomer(cust2);
			custm.createNewCustomer(cust3);
			Customer found1 = custm.findCustomerByID(1);
			Customer found2 = custm.findCustomerByID(2);
			Customer found3 = custm.findCustomerByID(3);
			assertEquals(cust1, found1);
			assertEquals(cust2, found2);
			assertEquals(cust3, found3);
			assertNotSame(cust1, found2);
			assertNotSame(cust1, found3);
			assertNotSame(cust2, found3);
			assertNull(custm.findCustomerByID(4)); // try to reach customer, that is not in the database
			assertNotNull(custm.findCustomerByID(3)); //last in database
		} catch (CustomerManagerException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Test of findCustomerByName method, of class CustomerManagerImpl.
	 */
	@Test
	public void testFindCustomerByName() {
		System.out.println("findCustomerByName");
		//new customer generation
		CustomerManagerImpl custm = new CustomerManagerImpl();
		try {
			Address addr1 = new Address(1,13, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
			Address addr2 = new Address(2,157, "Kluka Chlupateho", "Tábor", "Čechy", "123 48");
			Address addr3 = new Address(3,77400, "Žluťouličatá řepa", "Šílené koňské měchy", "Bangladéš", "238 88");
			Customer cust1 = new Customer(1,"Sir","Glorg",addr1);
			Customer cust2 = new Customer(2,"Jára!","Cimrman!!",addr2);
			Customer cust3 = new Customer(3,"Nouhwej","Customér",addr3);
			custm.createNewCustomer(cust1);
			custm.createNewCustomer(cust2);
			custm.createNewCustomer(cust3);
			custm.createNewCustomer(new Customer(1,"Sir","Olin",addr2));
			custm.createNewCustomer(new Customer(879,"Sir","Felixys",addr3));
			ArrayList<Customer> found1 = custm.findCustomerByName("Sir");
			ArrayList<Customer> found2 = custm.findCustomerByName("Jára!");
			ArrayList<Customer> found3 = custm.findCustomerByName("Customér");
			for (Customer cust : found1) {
				assertEquals("Sir", cust.getName());
			}
			assertEquals(3,found1.size());	// there should be 3 Customers found

			for (Customer cust : found2) {
				assertEquals("Jára!", cust.getName());
			}
			assertEquals(1,found2.size());
			assertEquals(0,found3.size());	// there should be no successful search

			try {
				found1 = custm.findCustomerByName(null);
				fail();
			} catch (IllegalArgumentException e) {
			}
		} catch (CustomerManagerException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Test of findCustomerBySurname method, of class CustomerManagerImpl.
	 */
	@Test
	public void testFindCustomerBySurname() {
		System.out.println("findCustomerBySurname");
		//new addresses generation
		CustomerManagerImpl custm = new CustomerManagerImpl();
		try {
			Address addr1 = new Address(1,13, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
			Address addr2 = new Address(2,157, "Kluka Chlupateho", "Tábor", "Čechy", "123 48");
			Address addr3 = new Address(3,77400, "Žluťouličatá řepa", "Šílené koňské měchy", "Bangladéš", "238 88");
			Customer cust1 = new Customer(1,"Sir","Glorg",addr1);
			Customer cust2 = new Customer(2,"Jára!","Cimrman!!",addr2);
			Customer cust3 = new Customer(3,"Nouhwej","Customér",addr3);
			custm.createNewCustomer(cust1);
			custm.createNewCustomer(cust2);
			custm.createNewCustomer(cust3);
			custm.createNewCustomer(new Customer(1,"Olej","Cimrman!!",addr2));
			custm.createNewCustomer(new Customer(879,"Krtek","Cimrman!!",addr3));
			ArrayList<Customer> found1 = custm.findCustomerBySurname("Cimrman!!");
			ArrayList<Customer> found2 = custm.findCustomerBySurname("Customér");
			ArrayList<Customer> found3 = custm.findCustomerBySurname("Nouhwej");
			for (Customer cust : found1) {
				assertEquals("Cimrman!!", cust.getSurname());
			}
			assertEquals(3,found1.size());	// there should be 3 Customers found

			for (Customer cust : found2) {
				assertEquals("Customér", cust.getSurname());
			}
			assertEquals(1,found2.size());
			assertEquals(0,found3.size());	// there should be no successful search

			try {
				custm.findCustomerBySurname(null);
				fail();
			} catch (IllegalArgumentException e) {
			}
		} catch (CustomerManagerException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Test of findAllCustomers method, of class CustomerManagerImpl.
	 */
	@Test
	public void testFindAllCustomers() {
		System.out.println("findAllCustomers");
		//new addresses generation
		CustomerManagerImpl custm = new CustomerManagerImpl();
		try {
			Address addr1 = new Address(1,13, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
			Address addr2 = new Address(2,157, "Kluka Chlupateho", "Tábor", "Čechy", "123 48");
			Address addr3 = new Address(3,77400, "Žluťouličatá řepa", "Šílené koňské měchy", "Bangladéš", "238 88");
			Address addr4 = new Address(4,77400, "1v35/*53", "avb6006y", "atva535", "236548 88");
			Address addr5 = new Address(5,77400, "59483 řepa", "1345ab00af0ac6y", "Banglafs34q", "2asdf6546");
			Customer cust1 = new Customer(1,"Sir","Glorg",addr1);
			Customer cust2 = new Customer(2,"Jára!","Cimrman!!",addr2);
			Customer cust3 = new Customer(3,"Nouhwej","Customér",addr3);
			Customer cust4 = new Customer(4,"Olej","Cimrman!!",addr4);
			Customer cust5 = new Customer(5,"Krtek","Cimrman!!",addr5);
			custm.createNewCustomer(cust1);
			custm.createNewCustomer(cust2);
			custm.createNewCustomer(cust3);
			custm.createNewCustomer(cust4);
			custm.createNewCustomer(cust5);
			ArrayList<Customer> found = custm.findAllCustomers();
			assertEquals(5,found.size());	// there should be 3 Customers found
			assertEquals(cust1,found.get(0));
			assertEquals(cust2,found.get(1));
			assertEquals(cust3,found.get(2));
			assertEquals(cust4,found.get(3));
			assertEquals(cust5,found.get(4));
			
			assertNotSame(cust1, found.get(1)); //totaly different
			assertNotSame(cust2, found.get(0)); //totaly different
			assertNotSame(cust3, found.get(4)); //totaly different
			assertNotSame(cust4, found.get(2)); //totaly different
			assertNotSame(cust5, found.get(3)); //totaly different
		} catch (CustomerManagerException e) {
			e.printStackTrace();
			fail();
		}
	}


	private static final void initializeDatabase(){
		DBManager dbm = new DBManager();
		dbm.connect();
		dbm.dropTable("ADDRESS");
		dbm.dropTable("CUSTOMER");
		dbm.disconnect();
	}
}