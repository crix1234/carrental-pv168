package carrental;

import carrental.entities.Address;
import carrental.entities.Customer;
import carrental.managers.*;
import java.sql.*;

/**
 *
 * @author Pavel Mican
 */
public class DatabaseTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		CustomerManager cm = new CustomerManagerImpl() {};
		Address addr = new Address(1, 234, "Karoliny Svetle", "Dvur Kralove nad Labem", "Czech Republic", "544 01");
		Customer newCustomer = cm.createNewCustomer("Kare", "Novak", addr);

		/*String driver = "org.apache.derby.jdbc.ClientDriver";
		Class.forName(driver).newInstance();

		Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/CAR_RENTAL","host","host");
		PreparedStatement st = c.prepareStatement(
			"SELECT * FROM APP.CUSTOMERS WHERE id = ?");
		st.setInt(1, 1);
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
			System.out.println("id=" + rs.getInt("id")
				+" name=" + rs.getString("name"));
		}
		c.close();
		 */
	}
}
