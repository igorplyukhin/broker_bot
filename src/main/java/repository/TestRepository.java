package repository;

import entities.User;
import enums.Actives;

import java.util.HashMap;


public class TestRepository implements Repository {

    private static final HashMap<Long, User> users = new HashMap<>();
    private static final HashMap<Actives, String> quotes = new HashMap<>() {{
        put(Actives.AAPL, "19$");
        put(Actives.GOOGL, "1$");
    }};


    @Override
    public String getQuote(Actives quoteName) {
        return quoteName.toString() + " : " + quotes.get(quoteName);
    }

    public double getQuotes(Actives activesFrom, Actives activesTo) {
        if (activesTo == activesFrom)
            return 1;
        if (activesFrom == Actives.RUB && activesTo == Actives.USD) {
            return 77;
        }
        if (activesFrom == Actives.USD && activesTo == Actives.RUB) {
            return 1 / 77;
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
