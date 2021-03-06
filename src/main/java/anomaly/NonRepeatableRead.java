package anomaly;

import connection.DbUtil;
import repository.CRUDimpl;

import java.sql.Connection;
import java.sql.SQLException;

public class NonRepeatableRead {
    private CRUDimpl crud;

    public NonRepeatableRead(CRUDimpl crud) {
        this.crud = crud;
    }

    public void startReadUncommitted() {
        try {
            initConn();
            DbUtil.getConnection1().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            DbUtil.getConnection2().setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            run();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void startReadCommitted() {
        try {
            initConn();
            DbUtil.getConnection1().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            DbUtil.getConnection2().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            run();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void startRepeatableRead() {
        try {
            initConn();
            DbUtil.getConnection1().setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            DbUtil.getConnection2().setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            run();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void startSerializable() {
        try {
            initConn();
            DbUtil.getConnection1().setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            DbUtil.getConnection2().setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            run();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void run() {
        Thread thread1 = new Thread(() -> {
            try {
                System.out.println("- BEGIN TRANSACTION 1 -");
                Thread.sleep(2000);
                crud.update(DbUtil.getConnection1());
                DbUtil.getConnection1().commit();
                DbUtil.getConnection1().close();
                System.out.println("- END TRANSACTION 1 -");
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                System.out.println("- BEGIN TRANSACTION 2 -");
                crud.selectAllAccounts(DbUtil.getConnection2());
                Thread.sleep(4000);
                crud.selectAllAccounts(DbUtil.getConnection2());
                DbUtil.getConnection2().commit();
                DbUtil.getConnection2().close();
                System.out.println("- END TRANSACTION 2 -");
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        });

        thread2.start();
        thread1.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void initConn() throws SQLException {
        DbUtil.createNewConnections();
        DbUtil.getConnection1().setAutoCommit(false);
        DbUtil.getConnection2().setAutoCommit(false);
    }

}
