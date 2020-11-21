package repository;

import entities.User;
import entities.transaction.Transaction;
import enums.Active;
import enums.State;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public class ApiRepository implements Repository {
    private static final HashMap<Long, User> users = new HashMap<>();
    private static final HashMap<Long, State> states = new HashMap<>();
    private static final java.lang.String[] stocks = Active.getNames();

    @Override
    public Stock getQuote(java.lang.String quoteName) throws IOException {
        return YahooFinance.get(quoteName);
    }

    @Override
    public Collection<Stock> getQuotes() throws IOException {
        return YahooFinance.get(stocks).values();
    }

    @Override
    public User createUser(long userID) {
        if (users.containsKey(userID))
            return null;
        users.put(userID, new User(userID));
        states.put(userID, State.DEFAULT);
        return users.get(userID);
    }

    @Override
    public User getUser(long userID) {
        var user = users.get(userID);
        if (user == null)
            user = createUser(userID);
        return user;
    }

    @Override
    public void setUserState(long ID, State state) {
        states.put(ID, state);
    }

    @Override
    public State getUserState(long ID) {
        return states.get(ID);
    }

    @Override
    public boolean proceedTransaction(Transaction transaction) {
        var stock = transaction.getStock();
        var count = transaction.getCount();
        var price = transaction.getPrice();
        switch (transaction.getType()) {
            case BUY -> {
                return getUser(transaction.getUserID()).buyStock(stock, count, price);
            }
            case SELL -> {
                return getUser(transaction.getUserID()).sellStock(stock, count, price);
            }
            default -> {
                return false;
            }
        }
    }
}
