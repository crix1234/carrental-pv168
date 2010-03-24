package carrental.entities;

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
public class CustomerTest {

    public CustomerTest() {
    }

	@BeforeClass
	public static void setUpClass() throws Exception {
		System.out.println("CUSTOMER TESTS:");
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
	 * Test of getId method, of class Customer.
	 */
	@Test
	public void testGetId() {
		System.out.println("getId");
		int expResult = 88;
		Address address = new Address(1,31, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		Customer customer = new Customer(expResult,"Pavel","Mičan",address);
		int result = customer.getId();
		assertEquals(expResult, result);
	}

	/**
	 * Test of getName method, of class Customer.
	 */
	@Test
	public void testGetName() {
		String expResult = "Šimčířňák!";
		Address address = new Address(1,8493, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		Customer customer = new Customer(88,expResult,"Mičan",address);
		String result = customer.getName();
		assertEquals(expResult, result);
	}

	/**
	 * Test of setName method, of class Customer.
	 */
	@Test
	public void testSetName() {
		System.out.println("setName");
		Address address = new Address(1,12, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		String expResult = "Eliškyš";
		Customer instance = new Customer(88,expResult,"Mičan",address);
		instance.setName(expResult);
		String result = instance.getName();
		assertEquals(expResult,result);
		try {
			instance.setName(null);
			fail();
		} catch(IllegalArgumentException ex) {
		}

		instance.setName("123456789012345678901234567890123456789012345678901234567890");
		result = instance.getName();
		assertEquals("123456789012345678901234567890123456789012345678901234567890", result);
	}

	/**
	 * Test of getSurname method, of class Customer.
	 */
	@Test
	public void testGetSurname() {
		System.out.println("getSurname");
		String expResult = "Ňuma ňumi ňuhňamáš";
		Address address = new Address(1,8493, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		Customer customer = new Customer(88,"?Ovoce",expResult,address);
		String result = customer.getSurname();
		assertEquals(expResult, result);
	}

	/**
	 * Test of setSurname method, of class Customer.
	 */
	@Test
	public void testSetSurname() {
		System.out.println("setSurname");
		Address address = new Address(1,12, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		String expResult = "Mičan";
		Customer instance = new Customer(88,"Eliškyš",expResult,address);
		instance.setSurname(expResult);
		String result = instance.getSurname();
		assertEquals(expResult,result);
		try {
			instance.setSurname(null);
			fail();
		} catch(IllegalArgumentException ex) {
		}

		instance.setSurname("123456789012345678901234567890123456789012345678901234567890");
		result = instance.getSurname();
		assertEquals("123456789012345678901234567890123456789012345678901234567890", result);
	}

	/**
	 * Test of getAddress method, of class Customer.
	 */
	@Test
	public void testGetAddress() {
		System.out.println("getAddress");
		Address expResult = new Address(1,8493, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		Address address = new Address(1,8493, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		Customer customer = new Customer(88,"?Ovoce","Zeh#lenina",address);
		Address result = customer.getAddress();
		assertEquals(expResult, result);
	}

	/**
	 * Test of setAddress method, of class Customer.
	 */
	@Test
	public void testSetAddress() {
		System.out.println("setAddress");
		Address address = new Address(1,12, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		Address expResult = new Address(879,12, "Karohžíéqěcébžžqř", "19š1čmýáýá--_/908ˇů!''12¨¨¨", "Czech\"\" Republic¨¨¨¨''Z\\34q5", "544 \"\"\"}}__)()()))01");
		Customer instance = new Customer(88,"Eliškyš","adsfpíáýféíasdfaklndvla",address);
		instance.setAddress(expResult);
		Address result = instance.getAddress();
		assertEquals(expResult,result);
		try {
			instance.setAddress(null);
			fail();
		} catch(IllegalArgumentException ex) {
		}
	}

	/**
	 * Test of equals method, of class Customer.
	 */
	@Test
	public void testEquals() {
		System.out.println("equals");
		// define couples of identical addresses
		Address addr1 = new Address(2,43545, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		Address addr2 = new Address(79,43545, "Alžbjety přeříznuté", "Ulanbhattar", "Planet X", "Xy xero žero");
		Customer customer1 = new Customer(5,"Elisška","Krásnohorská",addr1);
		Customer customer1_2 = new Customer(5,"Elisška","Krásnohorská",addr1);
		Customer customer2 = new  Customer(7,"+čqw´+mj´+ě´m+ýénná=míxpnxůfa¨¨a)íčšř54e","asdfhasfjklělškěýáščéyiopsdasda576nyžáíé++žáíé",addr2);
		Customer customer2_2 = new  Customer(7,"+čqw´+mj´+ě´m+ýénná=míxpnxůfa¨¨a)íčšř54e","asdfhasfjklělškěýáščéyiopsdasda576nyžáíé++žáíé",addr2);
		Customer customer3 = new  Customer(5213,"El+šč543543+0ř546isška","Krásn+16879+šč87++řš87+ohorská",addr1);
		Customer customer3_2 = new  Customer(5213,"El+šč543543+0ř546isška","Krásn+16879+šč87++řš87+ohorská",addr1);
		Customer customer4 = new  Customer(579897,"EÝÁÉ+ĚŽ=ÍĚŠČ%23523b69%5%b689%c3b6%2lisška","Krásnohor+ščřafyípdÝÁ=Ěská",addr2);
		Customer customer4_2 = new  Customer(579897,"EÝÁÉ+ĚŽ=ÍĚŠČ%23523b69%5%b689%c3b6%2lisška","Krásnohor+ščřafyípdÝÁ=Ěská",addr2);
		// test for couples identity
		assertEquals(customer1, customer1_2);
		assertEquals(customer2, customer2_2);
		assertEquals(customer3, customer3_2);
		assertEquals(customer4, customer4_2);
		// basic test of difference (non-equality) of totally different addresses
		assertNotSame(customer1, customer2);
		assertNotSame(customer1, customer3);
		assertNotSame(customer1, customer4);
		assertNotSame(customer2, customer3);
		assertNotSame(customer2, customer4);
		assertNotSame(customer3, customer4);
		// test for difference (non-equality) slightly modified addresses (everytime only one argument is somehow modified
		customer2 = new Customer(5,"El isška","Krásnohorská",addr1);
		customer3 = new Customer(5,"Elisška","Krásnohorskáa",addr1);
		customer4 = new Customer(5,"Elisška","Krásnohorská",addr2);
		assertNotSame(customer1, customer2);
		assertNotSame(customer1, customer3);
		assertNotSame(customer1, customer4);
	}

	/**
	 * Test of hashCode method, of class Customer.
	 */
	@Test
	public void testHashCode() {
		System.out.println("hashCode");
		int result1;
		int result2;
		Address addr1 = new Address(2,43545, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		Address addr2 = new Address(79,43545, "Alžbjety přeříznuté", "Ulanbhattar", "Planet X", "Xy xero žero");
		Customer customer1 = new Customer(5,"Elisška","Krásnohorská",addr1);
		Customer customer1_2 = new Customer(5,"Elisška","Krásnohorská",addr1);
		Customer customer2 = new  Customer(7,"+čqw´+mj´+ě´m+ýénná=míxpnxůfa¨¨a)íčšř54e","asdfhasfjklělškěýáščéyiopsdasda576nyžáíé++žáíé",addr2);
		Customer customer2_2 = new  Customer(7,"+čqw´+mj´+ě´m+ýénná=míxpnxůfa¨¨a)íčšř54e","asdfhasfjklělškěýáščéyiopsdasda576nyžáíé++žáíé",addr2);
		result1 = customer1.hashCode();
		result2 = customer1_2.hashCode();
		assertEquals(result1, result2);
		result1 = customer2.hashCode();
		result2 = customer2_2.hashCode();
		assertEquals(result1, result2);

		customer1 = new Customer(59511329,"Elisěšžě+ě+ýíé+ě+ěščěřžěě876+šř879šbvžá879šb*bvš*ř2čžčnmžíšbméěvž=ěšvž,ška","Krásn879ěšč/-sc16879vj zě1š213+213213b213méí3213řž213ěšč+ěšc+ěšřýá +ě+´+ě+*ě*/-(/'vhjaohorská",addr1);
		customer1_2 = new Customer(59511329,"Elisěšžě+ě+ýíé+ě+ěščěřžěě876+šř879šbvžá879šb*bvš*ř2čžčnmžíšbméěvž=ěšvž,ška","Krásn879ěšč/-sc16879vj zě1š213+213213b213méí3213řž213ěšč+ěšc+ěšřýá +ě+´+ě+*ě*/-(/'vhjaohorská",addr1);
		customer2 = new  Customer(1,"+čqw´+mj´+ě´m+ýénná=míxpnxůfa¨¨a)íčšř54e","asdfhasfjklělškěýáščéyiopsdasda576nyžáíé++žáíé",addr2);
		customer2_2 = new  Customer(1,"+čqw´+mj´+ě´m+ýénná=míxpnxůfa¨¨a)íčšř54e","asdfhasfjklělškěýáščéyiopsdasda576nyžáíé++žáíé",addr2);
		result1 = customer1.hashCode();
		result2 = customer1_2.hashCode();
		assertEquals(result1, result2);
		result1 = customer2.hashCode();
		result2 = customer2_2.hashCode();
		assertEquals(result1, result2);
	}

}