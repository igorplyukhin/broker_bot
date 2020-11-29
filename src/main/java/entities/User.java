package entities;

import enums.Stock;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class User {
    public ArrayList<String> previousReplies = new ArrayList<>(){{add("");}};
    private final long id;
    private double usdBalance;
    private final HashMap<Stock, Integer> portfolio;

    public User(long id, double usdBalance, HashMap<Stock, Integer> portfolio) {
        this.id = id;
        this.usdBalance = usdBalance;
        this.portfolio = portfolio;
    }

    public User(long id) {
        this.id = id;
        this.usdBalance = 5000;
        this.portfolio = new HashMap<>();
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

    public HashMap<Stock, Integer> getPortfolio() {
        return portfolio;
    }

    public java.lang.String toStringBalance() {
        return String.format("Your balance is %.2f$", usdBalance);
    }

    public boolean buyStock(Stock stock, int count, double price) {
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

    public boolean sellStock(Stock stock, int count, double price) {
        if (!portfolio.containsKey(stock) || portfolio.get(stock) < count)
            return false;

        usdBalance += count * price;
        portfolio.put(stock, portfolio.get(stock) - count);
        if (portfolio.get(stock) == 0)
            portfolio.remove(stock);
        return true;
    }
}