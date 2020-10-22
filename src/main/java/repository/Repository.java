package repository;

import entities.User;
import enums.Actives;
import yahoofinance.Stock;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface Repository {
    Stock getQuote(String quoteName);
    Collection<Stock> getQuotes();
    User createUser(long ID);
    User getUser(long ID);
}

