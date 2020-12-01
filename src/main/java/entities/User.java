package entities;


import yahoofinance.Stock;

import java.lang.reflect.Array;
import java.util.*;

public class User {
    public final ArrayList<String> previousReplies = new ArrayList<>(){{add("");}};
    public final Boolean isVip;
    private final long id;
    private double usdBalance;
    private double rubBalance;
    private final HashMap<String, Integer> portfolio;
    private final HashSet<String> extraQuotes;


    public User(long id, double usdBalance, HashMap<String, Integer> portfolio, boolean isVip, HashSet<String> extraQuotes) {
        this.id = id;
        this.usdBalance = usdBalance;
        this.portfolio = portfolio;
        this.isVip = isVip;
        this.extraQuotes = extraQuotes;
    }


    public User(long id) {
        this.id = id;
        this.usdBalance = 5000;
        this.portfolio = new HashMap<>();
        this.isVip = false;
        this.extraQuotes = null;
    }

    public long getId() {
        return id;
    }

    public double getUsdBalance() {
        return usdBalance;
    }

    public void setUsdBalance(double usdBalance) {
        this.usdBalance = usdBalance;
    }

    public HashMap<String, Integer> getPortfolio() {
        return portfolio;
    }

    public String toStringBalance() {
        return String.format("Твой баланс %.2f$", usdBalance);
    }

    public HashSet<String> getExtraQuotes() {
        return extraQuotes;
    }

    public void addExtraQuote(String quote){
        if (extraQuotes != null)
            extraQuotes.add(quote);
    }

    public boolean buyStock(String stock, int count, double price) {
        if (usdBalance - count * price < 0)
            return false;

        usdBalance -= count * price;
        if (portfolio.containsKey(stock)) {
            portfolio.put(stock, portfolio.get(stock) + count);
        } else {
            portfolio.put(stock, count);
        }

        return true;
    }

    public boolean sellStock(String stock, int count, double price) {
        if (!portfolio.containsKey(stock) || portfolio.get(stock) < count)
            return false;

        usdBalance += count * price;
        portfolio.put(stock, portfolio.get(stock) - count);
        if (portfolio.get(stock) == 0)
            portfolio.remove(stock);
        return true;
    }
}