package db.tables;

import db.exceptions.SQLNoDataFoundException;
import entities.User;
import enums.Stock;

import java.sql.*;
import java.util.HashMap;

public class UsersTable {
    private Connection conn = null;
    private static final String tableName = "users";

    public UsersTable(Connection conn) {
        this.conn = conn;
    }

    public User addUser(User user) throws SQLException {
        var stmt = conn.createStatement();
        var query = String.format("INSERT INTO %s VALUES (%d, %f, '{}');", tableName, user.getId(), user.getUsdBalance());
        stmt.executeUpdate(query);
        stmt.close();
        return user;
    }

    public User getUser(long userId) throws SQLException, SQLNoDataFoundException {
        var stmt = conn.createStatement();
        var query = String.format("SELECT * FROM %s WHERE id=%d;", tableName, userId);
        var rs = stmt.executeQuery(query);
        stmt.close();
        if (!rs.next())
            throw new SQLNoDataFoundException("NO such user");

        var arr = (String[]) rs.getArray("portfolio").getArray();
        return new User(rs.getInt("id"), rs.getDouble("balance"), arr2HashMap(arr));
    }

    public void updateUser(User user) throws SQLException {
        var stmt = conn.createStatement();
        var balance = user.getUsdBalance();
        var portfolio = hashMap2String(user.getPortfolio());
        var id = user.getId();
        var query = String.format("UPDATE %s SET balance = %f, portfolio = '%s' WHERE id = %d;",
                tableName, balance, portfolio, id);
        stmt.executeUpdate(query);
        stmt.close();
    }

    private HashMap<Stock, Integer> arr2HashMap(String[] arr) {
        var portfolio = new HashMap<Stock, Integer>();
        for (var stock : arr) {
            var active = Stock.valueOf(stock);
            if (portfolio.containsKey(active))
                portfolio.put(active, portfolio.get(active) + 1);
            else
                portfolio.put(active, 1);
        }
        return portfolio;
    }

    private String hashMap2String(HashMap<Stock, Integer> map) {
        var sb = new StringBuilder().append('{');
        for (var key : map.keySet()) {
            var count = map.get(key);
            for (var i = 0; i < count; i++) {
                sb.append(String.format("\"%s\",", key.toString()));
            }
        }
        if (sb.length() > 1)
            sb.deleteCharAt(sb.length() - 1);
        sb.append('}');
        return sb.toString();
    }
}
