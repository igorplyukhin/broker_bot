package repository;

import entities.User;
import enums.Currency;

import java.util.HashMap;

public class TestRepositoryImpl implements Repository {

    private HashMap<Long, User> users = new HashMap<>();

    @Override
    public double getQuote(Currency currencyFrom, Currency currencyTo) {
        if (currencyTo == currencyFrom)
            return 1;
        if (currencyFrom == Currency.RUB && currencyTo == Currency.USD){
            return 77;
        }
        if (currencyFrom == Currency.USD && currencyTo == Currency.RUB){
            return 1/77;
        }
        return 0;
    }

    @Override
    public User createUser(long userID) {
        if (users.containsKey(userID))
            return null;

        users.put(userID, new User(userID));
        return users.get(userID);
    }

    @Override
    public User getUser(long userID) {
        return users.get(userID);
    }
}
