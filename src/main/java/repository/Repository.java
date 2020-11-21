package repository;

import entities.User;
import enums.State;
import yahoofinance.Stock;

import java.io.IOException;
import java.util.Collection;

public interface Repository {
    Stock getQuote(String quoteName) throws IOException;
    Collection<Stock> getQuotes() throws IOException;
    User createUser(long ID);
    User getUser(long ID);
    void setUserState(long ID, State state);
    State getUserState(long ID);
}

