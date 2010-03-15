package carrental;

import java.sql.*;

/**
 *
 * @author Pavel Mican
 */
public class DatabaseTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
	String driver = "org.apache.derby.jdbc.ClientDriver";
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
	}
}
