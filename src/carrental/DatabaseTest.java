/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package carrental;

import java.sql.*;

/**
 *
 * @author S
 */
public class DatabaseTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        Connection c = DriverManager.getConnection("jdbc:derby://localhost:1527/CAR_RENTAL");
        PreparedStatement st = c.prepareStatement(
                "SELECT * FROM CUSTOMERS WHERE id = ?");
        st.setInt(1, 10);
        ResultSet rs = st.executeQuery();
        while(rs.next()) {
            System.out.println("id=" + rs.getInt("id")
                    +" name=" + rs.getString("name"));
        }
    }
}
