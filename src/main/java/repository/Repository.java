package repository;

import entities.User;
import enums.Actives;
import enums.States;
import yahoofinance.Stock;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface Repository {
    Stock getQuote(String quoteName);
    Collection<Stock> getQuotes();
    User createUser(long ID);
    User getUser(long ID);
    void setUserState(long ID, States state);
    States getUserState(long ID);
}

