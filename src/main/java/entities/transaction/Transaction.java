package entities.transaction;

import enums.Active;
import enums.TransactionType;

import java.util.Date;

public interface Transaction {
    long getUserID();

    Active getStock();

    double getPrice();

    Date getDate();

    TransactionType getType();

    int getCount();
}
