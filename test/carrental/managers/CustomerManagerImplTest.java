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
		System.out.println("createNewCustomer");
		String name = "";
		String surname = "";
		Address address = null;
		CustomerManagerImpl instance = new CustomerManagerImpl();
		Customer expResult = null;
		Customer result = instance.createNewCustomer(name, surname, address);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
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

}