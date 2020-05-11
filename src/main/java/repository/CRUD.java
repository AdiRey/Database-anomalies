package repository;

import java.sql.Connection;

public interface CRUD {
    void selectAllAccounts(Connection connection);
    void insertRandomAccount(Connection connection);
    void update(Connection connection);
}
