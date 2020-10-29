package repository;

import entities.User;
import enums.Actives;
import enums.States;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ApiRepository implements Repository {
    private static final HashMap<Long, User> users = new HashMap<>();
    private static final HashMap<Long, States> states = new HashMap<>();
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
        states.put(userID, States.START);
        return users.get(userID);
    }

    @Override
    public User getUser(long userID) {
        return users.get(userID);
    }

    @Override
    public void setUserState(long ID, States state) {
        if (states.get(ID) == null)
            throw new IllegalArgumentException("User does not exist");
        states.put(ID, state);
    }

    @Override
    public States getUserState(long ID) {
        if (states.get(ID) == null)
            throw new IllegalArgumentException("User does not exist");
        return states.get(ID);
    }
}
