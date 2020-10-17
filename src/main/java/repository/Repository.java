package repository;

import entities.User;
import enums.Actives;

public interface Repository {
    String getQuote(Actives quoteName);
    double getQuotes(Actives activesFrom, Actives activesTo);
    User createUser(long ID);
    User getUser(long ID);
}

