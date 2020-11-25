package entities;

import enums.Active;
import org.checkerframework.checker.units.qual.A;

import java.util.HashMap;

public class User {
    private final long id;
    private double rubBalance;
    private double eurBalance;
    private double usdBalance;
    private final HashMap<Active, Integer> portfolio;

    public User(long id, double usdBalance, HashMap<Active, Integer> portfolio) {
        this.id = id;
        this.usdBalance = usdBalance;
        this.portfolio = portfolio;
    }

    public User(long id) {
        this.id = id;
        this.usdBalance = 1000;
        this.portfolio = new HashMap<>();
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

    public HashMap<Active, Integer> getPortfolio() {
        return portfolio;
    }

    public java.lang.String toStringBalance() {
        return String.format("Your balance is %.2f $", usdBalance);
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