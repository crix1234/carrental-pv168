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
public class AddressTest {

    public AddressTest() {
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
	 * Test of getId method, of class Address.
	 */
	@Test
	public void testGetId() {
		int expResult = 88;
		Address instance = new Address(expResult,776, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		int result = instance.getId();
		assertEquals(expResult, result);
	}

	/**
	 * Test of getHouseNumber method, of class Address.
	 */
	@Test
	public void testGetHouseNumber() {
		int expResult = 88;
		Address instance = new Address(1,expResult, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		int result = instance.getHouseNumber();
		assertEquals(expResult, result);
	}

	/**
	 * Test of setHouseNumber method, of class Address.
	 */
	@Test
	public void testSetHouseNumber() {
		Address instance = new Address(1,12, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		int expResult = 88;
		instance.setHouseNumber(expResult);
		int result = instance.getHouseNumber();
		assertEquals(expResult,result);
		try {
			instance.setHouseNumber(0);
			fail();
		} catch(IllegalArgumentException ex) {
		}

		try {
			instance.setHouseNumber(-5);
			fail();
		} catch(IllegalArgumentException ex){
		}

		try {
			instance.setHouseNumber(Integer.MIN_VALUE);
			fail();
		} catch(IllegalArgumentException ex){
		}
		
		try {
			instance.setHouseNumber(Integer.MAX_VALUE);
			result = instance.getHouseNumber();
			assertEquals(Integer.MAX_VALUE,result);
		} catch (Exception ex) {
			fail();
		}
	}

	/**
	 * Test of getStreet method, of class Address.
	 */
	@Test
	public void testGetStreet() {
		String expResult = "Karoliny Svetle";
		Address instance = new Address(1,8488, expResult, "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		String result = instance.getStreet();
		assertEquals(expResult, result);
	}

	/**
	 * Test of setStreet method, of class Address.
	 */
	@Test
	public void testSetStreet() {
		Address instance = new Address(1,12, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		String expResult = "Elišky Krásnohorské";
		instance.setStreet(expResult);
		String result = instance.getStreet();
		assertEquals(expResult,result);
		try {
			instance.setStreet(null);
			fail();
		} catch(IllegalArgumentException ex) {
		}

		instance.setStreet("123456789012345678901234567890123456789012345678901234567890");
		result = instance.getStreet();
		assertEquals("1234567890123456789012345678901234567890", result);
	}

	/**
	 * Test of getTown method, of class Address.
	 */
	@Test
	public void testGetTown() {
		String expResult = "Dvur Kralove nad Labem";
		Address instance = new Address(1,8488, "Karoliny Svetle", expResult, "Czech Republic", "544 01");
		String result = instance.getTown();
		assertEquals(expResult, result);
	}

	/**
	 * Test of setTown method, of class Address.
	 */
	@Test
	public void testSetTown() {
		Address instance = new Address(1,12, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		String expResult = "Liberec";
		instance.setTown(expResult);
		String result = instance.getTown();
		assertEquals(expResult,result);
		try {
			instance.setTown(null);
			fail();
		} catch(IllegalArgumentException ex) {
		}

		instance.setTown("123456789012345678901234567890123456789012345678901234567890");
		result = instance.getTown();
		assertEquals("1234567890123456789012345678901234567890", result); // maxlength 40
	}

	/**
	 * Test of getState method, of class Address.
	 */
	@Test
	public void testGetState() {
		String expResult = "Czech Republic";
		Address instance = new Address(1,8488, "Karoliny Svetle", "Dvur Kralove nad Labem", expResult, "544 01");
		String result = instance.getState();
		assertEquals(expResult, result);
	}

	/**
	 * Test of setState method, of class Address.
	 */
	@Test
	public void testSetState() {
		Address instance = new Address(1,12, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		String expResult = "United States of America";
		instance.setState(expResult);
		String result = instance.getState();
		assertEquals(expResult,result);
		try {
			instance.setState(null);
			fail();
		} catch(IllegalArgumentException ex) {
		}

		instance.setState("123456789012345678901234567890123456789012345678901234567890");
		result = instance.getState();
		assertEquals("1234567890123456789012345678901234567890", result); // maxlength 40
	}

	/**
	 * Test of getZipcode method, of class Address.
	 */
	@Test
	public void testGetZipcode() {
		String expResult = "544 01";
		Address instance = new Address(1,8488, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", expResult);
		String result = instance.getZipcode();
		assertEquals(expResult, result);
	}

	/**
	 * Test of setZipcode method, of class Address.
	 */
	@Test
	public void testSetZipcode() {
		Address instance = new Address(1,12, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		String expResult = "123 45/500";
		instance.setZipcode(expResult);
		String result = instance.getZipcode();
		assertEquals(expResult,result);
		try {
			instance.setZipcode(null);
			fail();
		} catch(IllegalArgumentException ex) {
		}

		instance.setZipcode("123456789012345678901234567890123456789012345678901234567890");
		result = instance.getZipcode();
		assertEquals("12345678901234567890", result); // maxlength 20
	}

	/**
	 * Test of hashCode method, of class Address.
	 */
	@Test
	public void testHashCode() {
		int result1;
		int result2;
		Address addr1 = new Address(2,43545, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		Address addr1_2 = new Address(2,43545, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		Address addr2 = new Address(5,1231276823, "Elisky Krasnohorske", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		Address addr2_2 = new Address(5,1231276823, "Elisky Krasnohorske", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		result1 = addr1.hashCode();
		result2 = addr1_2.hashCode();
		assertEquals(result1, result2);
		result1 = addr2.hashCode();
		result2 = addr2_2.hashCode();
		assertEquals(result1, result2);

		addr1 = new Address(789,423645, "Klu`2p89-asgd2+45-*3v5y-+4wv5y 1-04+w4689134b689125689- q34890 q45yqery5-*-*q35y-ka Chlupateho", "Táb51jx23x -2913c412-xc2173-23 4123or", "Čec[ki8 [12pwp[asaw[aa=235hy", "123 48");
		addr1_2 = new Address(789,423645, "Klu`2p89-asgd2+45-*3v5y-+4wv5y 1-04+w4689134b689125689- q34890 q45yqery5-*-*q35y-ka Chlupateho", "Táb51jx23x -2913c412-xc2173-23 4123or", "Čec[ki8 [12pwp[asaw[aa=235hy", "123 48");
		addr2 = new Address(1346023783,77400, "Žluťouličat23a5,.mn .sasi ya985= 32 75=1 7asi bz[3á řepa", "Šílené koňské měchy", "Banglasd uasdopu fa9s- 9asd6f 0a0w 3k41n  [paI}{adéš", "238-92379xn72-1472-3976t-7-;AEawe'awc=w]=a2[3035[017831c12xcvb1578o0546 88");
		addr2_2 = new Address(1346023783,77400, "Žluťouličat23a5,.mn .sasi ya985= 32 75=1 7asi bz[3á řepa", "Šílené koňské měchy", "Banglasd uasdopu fa9s- 9asd6f 0a0w 3k41n  [paI}{adéš", "238-92379xn72-1472-3976t-7-;AEawe'awc=w]=a2[3035[017831c12xcvb1578o0546 88");
		result1 = addr1.hashCode();
		result2 = addr1_2.hashCode();
		assertEquals(result1, result2);
		result1 = addr2.hashCode();
		result2 = addr2_2.hashCode();
		assertEquals(result1, result2);
	}

	/**
	 * Test of equals method, of class Address.
	 */
	@Test
	public void testEquals() {
		// define couples of identical addresses
		Address addr1 = new Address(2,43545, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		Address addr1_2 = new Address(2,43545, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		Address addr2 = new Address(5,1231276823, "Elisky Krasnohorske", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		Address addr2_2 = new Address(5,1231276823, "Elisky Krasnohorske", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		Address addr3 = new Address(789,423645, "Klu`2p89-asgd2+45-*3v5y-+4wv5y 1-04+w4689134b689125689- q34890 q45yqery5-*-*q35y-ka Chlupateho", "Táb51jx23x -2913c412-xc2173-23 4123or", "Čec[ki8 [12pwp[asaw[aa=235hy", "123 48");
		Address addr3_2 = new Address(789,423645, "Klu`2p89-asgd2+45-*3v5y-+4wv5y 1-04+w4689134b689125689- q34890 q45yqery5-*-*q35y-ka Chlupateho", "Táb51jx23x -2913c412-xc2173-23 4123or", "Čec[ki8 [12pwp[asaw[aa=235hy", "123 48");
		Address addr4 = new Address(1346023783,77400, "Žluťouličat23a5,.mn .sasi ya985= 32 75=1 7asi bz[3á řepa", "Šílené koňské měchy", "Banglasd uasdopu fa9s- 9asd6f 0a0w 3k41n  [paI}{adéš", "238-92379xn72-1472-3976t-7-;AEawe'awc=w]=a2[3035[017831c12xcvb1578o0546 88");
		Address addr4_2 = new Address(1346023783,77400, "Žluťouličat23a5,.mn .sasi ya985= 32 75=1 7asi bz[3á řepa", "Šílené koňské měchy", "Banglasd uasdopu fa9s- 9asd6f 0a0w 3k41n  [paI}{adéš", "238-92379xn72-1472-3976t-7-;AEawe'awc=w]=a2[3035[017831c12xcvb1578o0546 88");
		// test for couples identity
		assertEquals(addr1, addr1_2);
		assertEquals(addr2, addr2_2);
		assertEquals(addr3, addr3_2);
		assertEquals(addr4, addr4_2);
		// basic test of difference (non-equality) of totally different addresses
		assertNotSame(addr1, addr2);
		assertNotSame(addr1, addr3);
		assertNotSame(addr1, addr4);
		assertNotSame(addr2, addr3);
		assertNotSame(addr2, addr4);
		assertNotSame(addr3, addr4);
		// test for difference (non-equality) slightly modified addresses (everytime only one argument is somehow modified
		addr1 = new Address(2,43545, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		addr2 = new Address(2,435745, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		addr3 = new Address(2,43545, "Karoliny Svetlee", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		addr4 = new Address(2,43545, " Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		assertNotSame(addr1, addr2);
		assertNotSame(addr1, addr3);
		assertNotSame(addr1, addr4);
		addr2 = new Address(8,43545, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		addr3 = new Address(2,43545, "Karoliny Svetle", "Dvur Kralove  nad Labem", "Czech Republic", "544 01");
		addr4 = new Address(2,43545, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Repu blic", "544 01");
		assertNotSame(addr1, addr2);
		assertNotSame(addr1, addr3);
		assertNotSame(addr1, addr4);
		addr2 = new Address(2,43545, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 101");
		addr3 = new Address(2,43545, "Karoliny Svetle", "", "Czech Republic", "544 01");
		assertNotSame(addr1, addr2);
		assertNotSame(addr1, addr3);
	}

}