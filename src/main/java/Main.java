import anomaly.DirtyRead;
import anomaly.NonRepeatableRead;
import anomaly.Phantom;
import connection.DbUtil;
import model.BankAccountSql;
import repository.CRUDimpl;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        new DbUtil();
        CRUDimpl crud = new CRUDimpl();
        DbUtil.createNewConnections();
        DirtyRead dirtyRead = new DirtyRead(crud);
        NonRepeatableRead nonRepeatableRead = new NonRepeatableRead(crud);
        Phantom phantom = new Phantom(crud);
        BankAccountSql.createTable(DbUtil.getConnection1());
        Scanner scanner = new Scanner(System.in);
        String choice;
        while (true) {
            printOptions();
            choice = scanner.nextLine();
            System.out.println("*****************************************");
            System.out.println("Poczatkowy stan bazy:");
            crud.selectAllAccounts(DbUtil.getConnection1());
            DbUtil.getConnection1().commit();
            System.out.println("------");
            switch (choice) {
                case "d1":
                    dirtyRead.startReadUncommitted();
                    break;
                case "d2":
                    dirtyRead.startReadCommitted();
                    break;
                case "d3":
                    dirtyRead.startRepeatableRead();
                    break;
                case "d4":
                    dirtyRead.startSerializable();
                    break;
                case "n1":
                    nonRepeatableRead.startReadUncommitted();
                    break;
                case "n2":
                    nonRepeatableRead.startReadCommitted();
                    break;
                case "n3":
                    nonRepeatableRead.startRepeatableRead();
                    break;
                case "n4":
                    nonRepeatableRead.startSerializable();
                    break;
                case "p1":
                    phantom.startReadUncommitted();
                    break;
                case "p2":
                    phantom.startReadCommitted();
                    break;
                case "p3":
                    phantom.startRepeatableRead();
                    break;
                case "p4":
                    phantom.startSerializable();
                    break;
                case "0":
                    System.out.println("BYE BYE!");
                    return;
                default:
                    break;
            }
            DbUtil.createNewConnections();
            System.out.println("------");
            System.out.println("Koncowy stan bazy:");
            crud.selectAllAccounts(DbUtil.getConnection1());
            DbUtil.getConnection1().commit();
            BankAccountSql.resetTable(DbUtil.getConnection1());
            System.out.println("*****************************************");
        }
    }

    private static void printOptions() {
        System.out.println("d1) dirty read - read uncommitted");
        System.out.println("d2) dirty read - read committed");
        System.out.println("d3) dirty read - repeatable read");
        System.out.println("d4) dirty read - serializable");
        System.out.println("n1) non-repeatable read - read uncommitted");
        System.out.println("n2) non-repeatable read - read committed");
        System.out.println("n3) non-repeatable read - repeatable read");
        System.out.println("n4) non-repeatable read - serializable");
        System.out.println("p1) phantoms - read uncommitted");
        System.out.println("p2) phantoms - read committed");
        System.out.println("p3) phantoms - repeatable read");
        System.out.println("p4) phantoms - serializable");
        System.out.println("0) wyjdz z programu");
        System.out.print("Twoja opcja: ");
    }
}
