package repository;

import entities.User;
import enums.State;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public class ApiRepository implements Repository {
    private static final HashMap<Long, User> users = new HashMap<>();
    private static final HashMap<Long, State> states = new HashMap<>();
    private static final String[] stocks = new String[]{"AMD", "IBM", "AAPl",
            "INTC", "BABA", "TSLA", "AIR.PA"};

    @Override
    public Stock getQuote(String quoteName) {
        try {
            return YahooFinance.get(quoteName);
        }catch (IOException e){
            return null;
        }
    }

    @Override
    public Collection<Stock> getQuotes(){
        try{
           return YahooFinance.get(stocks).values();
        } catch (IOException e)
        {
            return null;
        }
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
