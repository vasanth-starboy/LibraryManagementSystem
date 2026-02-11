package in.kce.book.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {

    public static Connection getDbConnection() {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            String URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
            String user = "bank";
            String pass = "root";

            return DriverManager.getConnection(URL, user, pass);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
