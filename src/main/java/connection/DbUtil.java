package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {
    private static Connection connection1;
    private static Connection connection2;

    private static Properties properties;
    private static String url = "jdbc:h2:~/labydb";

    public DbUtil() throws SQLException{
        properties = new Properties();
        properties.put("user", "adi");
        properties.put("password", "adi123");
    }

    public static Connection getConnection1() throws SQLException {
        return connection1;
    }

    public static Connection getConnection2() throws SQLException {
        return connection2;
    }

    public static void close() throws SQLException {
        connection1.close();
        connection2.close();
    }

    public static void createNewConnections() throws SQLException {
        if (connection1 != null && connection2 != null) {
            close();
        }
        createConnections();
    }

    private static void createConnections() throws SQLException {
        connection1 = DriverManager.getConnection(url, properties);
        connection2 = DriverManager.getConnection(url, properties);
    }
}
