package entities.transaction;

import enums.Stock;
import enums.TransactionType;

import java.util.Date;

public interface Transaction {
    long getUserID();

    Stock getStock();

    double getPrice();

    Date getDate();

    TransactionType getType();

    int getCount();
}
