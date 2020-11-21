package repository;

import entities.User;
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
    private static final String[] stocks = Active.getNames();

    @Override
    public Stock getQuote(String quoteName) throws IOException {
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
        return users.get(userID);
    }

    @Override
    public void setUserState(long ID, State state) {
        states.put(ID, state);
    }

    @Override
    public State getUserState(long ID) {
        return states.get(ID);
    }
}
