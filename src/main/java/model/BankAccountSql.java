package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BankAccountSql {
    // TABLE_NAME: Account
    //id INT IDENTITY PRIMARY KEY,
    //money INT NOT NULL DEFAULT VALUE 0

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS Account(" +
            "id INT NOT NULL AUTO_INCREMENT," +
            "money INT NOT NULL DEFAULT 0," +
            "PRIMARY KEY(id));";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS Account;";
    private static final String ADD_ACCOUNT = "INSERT INTO Account(money) VALUES(5000)";
    private static final String DELETE_ACCOUNTS = "DELETE FROM Account";



    public static void createTable(Connection connection) {
        try {
            System.err.println("Tworzenie bazy");
            Statement statement = connection.createStatement();
            statement.executeUpdate(DROP_TABLE);
            statement.executeUpdate(CREATE_TABLE);
            statement.executeUpdate(ADD_ACCOUNT);
        } catch (SQLException e) {
            System.err.println("Nie mozna utowrzyc tabeli Account.");
            e.printStackTrace();
        }
    }

    public static void resetTable(Connection connection) {
        try {
            System.out.println("### RESET BAZY ###");
            Statement statement = connection.createStatement();
            statement.executeUpdate(DELETE_ACCOUNTS);
            statement.executeUpdate(ADD_ACCOUNT);
        } catch (SQLException e) {
            System.err.println("Nie mozna zresetowac bazy danych.");
            e.printStackTrace();
        }
    }
}
