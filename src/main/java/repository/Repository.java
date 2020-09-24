package repository;

import entities.User;
import enums.Currency;

public interface Repository {
    public double getQuote(Currency currencyFrom, Currency currencyTo);
    public User getUser(long userID);
    public User createUser(long userID);
}
