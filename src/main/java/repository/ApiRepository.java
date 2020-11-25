package repository;

import db.UsersTableController;
import db.exceptions.SQLNoDataFoundException;
import entities.User;
import entities.transaction.Transaction;
import enums.Stock;
import enums.UserState;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;

public class ApiRepository implements Repository {
    private static final HashMap<Long, User> users = new HashMap<>();
    private static final HashMap<Long, UserState> states = new HashMap<>();
    private static final String[] stocks = Stock.getNames();
    private final UsersTableController usersTableController;

    public ApiRepository(UsersTableController controller) {
        usersTableController = controller;
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
        System.out.println("new user");
        try {
            usersTableController.addUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User getUser(long userID) {
        if (users.containsKey(userID)) {
            System.out.println("from list");
            return users.get(userID);
        }

        try {
            var user = usersTableController.getUser(userID);
            System.out.println("from db");
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
        var f = new SimpleDateFormat(
                "yyyy-MM-dd kk:mm:ss");
        System.out.println(f.format(transaction.getDate()));
        var stock = transaction.getStock();
        var count = transaction.getCount();
        var price = transaction.getPrice();
        var user = getUser(transaction.getUserID());
        switch (transaction.getType()) {
            case BUY -> {
                var res = user.buyStock(stock, count, price);
                if (res)
                    saveUserToBD(user);
                return res;
            }
            case SELL -> {
                var res = user.sellStock(stock, count, price);
                if (res)
                    saveUserToBD(user);
                return res;
            }
            default -> {
                return false;
            }
        }
    }

    private void saveUserToBD(User user) {
        try {
            usersTableController.updateUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
