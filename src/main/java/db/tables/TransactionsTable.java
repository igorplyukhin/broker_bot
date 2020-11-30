package db.tables;

import entities.transaction.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class TransactionsTable {
    private final String table_name = "transactions";
    private Connection conn = null;

    public TransactionsTable(Connection conn) {
        this.conn = conn;
    }

    public void saveTransaction(Transaction tr) throws SQLException {
        var stmt = conn.createStatement();
        var query = String.format("INSERT INTO %s(date, user_id, stock, price, count, type)" +
                        " VALUES(NOW(), %d, '%s', %f, %d, '%s');", table_name,
                tr.getUserID(), tr.getStock().toString(), tr.getPrice(), tr.getCount(), tr.getType());
        stmt.executeUpdate(query);
        stmt.close();
    }

    public String getTransactions(long userID) throws SQLException {
        var sb = new StringBuilder();
        var stmt = conn.createStatement();
        var query = String.format("SELECT date, stock, price, count, type FROM (SELECT * FROM %s WHERE user_id = %d " +
                        "ORDER BY id DESC LIMIT 50) as FOO ORDER BY id ASC;", table_name, userID);
        var rs = stmt.executeQuery(query);
        while (rs.next()) {
            var s = String.format("%s %s %s (%s) за %.2f$\r\n", rs.getDate("date"), rs.getString("type"),
                    rs.getString("count"), rs.getString("stock"), rs.getDouble("price"));
            sb.append(s);
        }
        return sb.toString();
    }
}
