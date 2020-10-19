package repository;

import entities.User;
import enums.Actives;

public class ApiRepository implements Repository{
    @Override
    public String getQuote(Actives quoteName) {
        return null;
    }

    @Override
    public double getQuotes(Actives activesFrom, Actives activesTo) {
        return 0;
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
