package engine;

import java.util.Date;

public class Deal {
    Date date;
    int numOfShares;
    int soldPrice;
    int dealValue;

    public Date getDate() {
        return date;
    }

    public int getDealValue() {
        return dealValue;
    }

    public int getSoldPrice() {
        return soldPrice;
    }

    public int getNumOfShares() {
        return numOfShares;
    }
}
