package repository;

import db.DBController;
import db.tables.TransactionsTable;
import db.tables.UsersTable;
import db.exceptions.SQLNoDataFoundException;
import entities.User;
import entities.transaction.Transaction;
import enums.Stock;
import enums.UserState;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

public class ApiRepository implements Repository {
    private static final HashMap<Long, User> users = new HashMap<>();
    private static final HashMap<Long, UserState> states = new HashMap<>();
    private static final String[] stocks = Stock.getNames();
    private final DBController dbController;
    public ApiRepository(DBController dbController) {
        this.dbController = dbController;
    }

    @Override
    public yahoofinance.Stock getQuote(java.lang.String quoteName) throws IOException {
        return YahooFinance.get(quoteName);
    }

    @Override
    public Collection<yahoofinance.Stock> getQuotes() throws IOException {
        return YahooFinance.get(stocks).values();
    }

    @Override
    public User createUser(long userID) {
        var user = new User(userID);
        users.put(userID, user);
        states.put(userID, UserState.DEFAULT);
        try {
            dbController.usersTable.addUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User getUser(long userID) {
        if (users.containsKey(userID))
            return users.get(userID);

        try {
            var user = dbController.usersTable.getUser(userID);
            users.put(userID, user);
            return user;
        } catch (SQLNoDataFoundException ignored) {
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return createUser(userID);
    }

    @Override
    public void setUserState(long ID, UserState userState) {
        states.put(ID, userState);
    }

    @Override
    public UserState getUserState(long ID) {
        return states.get(ID);
    }

    @Override
    public boolean proceedTransaction(Transaction transaction) {
        var stock = transaction.getStock();
        var count = transaction.getCount();
        var price = transaction.getPrice();
        var user = getUser(transaction.getUserID());
        switch (transaction.getType()) {
            case BUY -> {
                var res = user.buyStock(stock, count, price);
                if (res) {
                    saveUserToBD(user);
                    saveTransactionToBD(transaction);
                }
                return res;
            }
            case SELL -> {
                var res = user.sellStock(stock, count, price);
                if (res) {
                    saveUserToBD(user);
                    saveTransactionToBD(transaction);
                }
                return res;
            }
            default -> {
                return false;
            }
        }
    }

    @Override
    public String getTransactionHistory(long userID){
        try {
            return dbController.transactionsTable.getTransactions(userID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "DB ERROR";
        }
    }

    private void saveUserToBD(User user) {
        try {
            dbController.usersTable.updateUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveTransactionToBD(Transaction tr) {
        try {
            dbController.transactionsTable.saveTransaction(tr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
