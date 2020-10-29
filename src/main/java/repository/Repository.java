package repository;

import entities.User;
import enums.State;
import yahoofinance.Stock;

import java.util.Collection;

public interface Repository {
    Stock getQuote(String quoteName);
    Collection<Stock> getQuotes();
    User createUser(long ID);
    User getUser(long ID);
    void setUserState(long ID, State state);
    State getUserState(long ID);
}

