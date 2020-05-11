package repository;

import java.sql.*;
import java.util.Random;

public class CRUDimpl implements CRUD {
    private Random random = new Random();
    @Override
    public void selectAllAccounts(Connection connection) {
        System.out.println("[SELECT]");
        final String QUERY = "SELECT * FROM Account";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY);
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + " - " + resultSet.getString("money") + "PLN");
            }
        } catch (SQLException e) {
            System.out.println("Nie mozna odczytac wszystkich kont.");
            e.printStackTrace();
        }
    }

    @Override
    public void insertRandomAccount(Connection connection) {
        System.out.println("[INSERT]");
        final String QUERY = String.format("INSERT INTO Account (money) VALUES (%d);", random.nextInt() % 2000);
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Nie mozna zapisac nowego konta.");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Connection connection) {
        System.out.println("[UPDATE]");
        final String QUERY = String.format("UPDATE Account SET money=%d;", random.nextInt() % 2000);
        try {
            PreparedStatement statement = connection.prepareStatement(QUERY);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("Nie mozna zaktualizowac konta.");
            e.printStackTrace();
        }
    }
}
