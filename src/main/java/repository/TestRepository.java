package repository;

import entities.User;
import enums.State;
import yahoofinance.Stock;
import yahoofinance.quotes.stock.StockQuote;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class TestRepository implements Repository {

    private static final HashMap<Long, User> users = new HashMap<>();
    private static final HashMap<Long, State> states = new HashMap<>();
    private static final Map<String, Stock> quotes = new HashMap<>() {{
        var q = new StockQuote("AAPL");
        q.setPrice(BigDecimal.valueOf(100));
        var a = new Stock("AAPL");
        a.setQuote(q);
        put("AAPL", a);
    }};

    @Override
    public Stock getQuote(String quoteName) {
        return quotes.get("AAPL");
    }

    @Override
    public Collection<Stock> getQuotes() {
        return quotes.values();
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
        return users.get(userID);
    }

    @Override
    public void setUserState(long ID, State state) {
        if (states.get(ID) == null)
            throw new IllegalArgumentException("User does not exist");
        states.put(ID, state);
    }

    @Override
    public State getUserState(long ID) {
        if (states.get(ID) == null)
            throw new IllegalArgumentException("User does not exist");
        return states.get(ID);
    }
}