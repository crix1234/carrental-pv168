package carrental.managers;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author SirGlorg
 */
public class DBManagerTest {

    public DBManagerTest() {
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
	 * Test of reduceLongString method, of class ReduceLongString.
	 */
	@Test
	public void testReduceLongString() {
		String value = "";
		int maxLength = 0;
		String expResult = "";
		String result = DBManager.reduceLongString(value, maxLength);
		assertEquals(expResult, result);

		value = null;
		expResult = null;
		result = DBManager.reduceLongString(value, maxLength);
		assertEquals(expResult, result);

		value = "123456789";
		maxLength = 5;
		expResult = "12345";
		result = DBManager.reduceLongString(value, maxLength);
		assertEquals(expResult, result);

		maxLength = 0;
		expResult = "";
		result = DBManager.reduceLongString(value, maxLength);
		assertEquals(expResult, result);

		maxLength = -4;
		expResult = null;
		result = DBManager.reduceLongString(value, maxLength);
		assertEquals(expResult, result);
	}

}