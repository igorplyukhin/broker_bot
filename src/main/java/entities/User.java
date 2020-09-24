package entities;

public class User {
    private final long id;
    private double rubBalance;
    private double eurBalance;
    private double usdBalance;

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
        this.usdBalance = 0;
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

    public String toStringBalance(){
        return String.format("RUB: %s\nUSD: %s\nEUR: %s", rubBalance, usdBalance, eurBalance);
    }
}
