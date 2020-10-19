package repository;

import entities.User;
import enums.Actives;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ApiRepository implements Repository {
    private static final String[] stocks = new String[]{"AMD", "IBM", "AAPl",
            "INTC", "BABA", "TSLA", "AIR.PA"};

    @Override
    public String getQuote(Actives quoteName) {
        return null;
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
    public User createUser(long ID) {
        return null;
    }

    @Override
    public User getUser(long ID) {
        return null;
    }
}
