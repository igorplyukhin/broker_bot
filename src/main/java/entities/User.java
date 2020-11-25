package entities;

import enums.Active;

import java.util.HashMap;

public class User {
    private final long id;
    private double rubBalance;
    private double eurBalance;
    private double usdBalance;
    private HashMap<Active, Integer> portfolio = new HashMap<>();

    public User(long id, double rubBalance, double eurBalance, double usdBalance) {
        this.id = id;
        this.rubBalance = rubBalance;
        this.eurBalance = eurBalance;
        this.usdBalance = usdBalance;
    }

    public User(long id) {
        this.id = id;
        this.rubBalance = 0;
        this.eurBalance = 0;
        this.usdBalance = 100000;
    }

    public long getId() {
        return id;
    }

    public double getRubBalance() {
        return rubBalance;
    }

    public void setRubBalance(double rubBalance) {
        this.rubBalance = rubBalance;
    }

    public double getEurBalance() {
        return eurBalance;
    }

    public void setEurBalance(double eurBalance) {
        this.eurBalance = eurBalance;
    }

    public double getUsdBalance() {
        return usdBalance;
    }

    public void setUsdBalance(double usdBalance) {
        this.usdBalance = usdBalance;
    }

    public java.lang.String toStringBalance() {
        return java.lang.String.format("RUB: %s\nUSD: %s\nEUR: %s", rubBalance, usdBalance, eurBalance);
    }

    public boolean buyStock(Active stock, int count, double price) {
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

    public boolean sellStock(Active stock, int count, double price) {
        if (!portfolio.containsKey(stock) || portfolio.get(stock) < count)
            return false;

        usdBalance += count * price;
        portfolio.put(stock, portfolio.get(stock) - count);
        if (portfolio.get(stock) == 0)
            portfolio.remove(stock);
        return true;
    }
}
