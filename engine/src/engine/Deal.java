package engine;

import java.util.Date;

public class Deal extends Barter {
    private int dealValue;

    public Deal(Date date, int numOfShares, int soldPrice, int dealValue) {
        super(date, numOfShares, soldPrice);
        this.dealValue = dealValue;
    }

    public int getDealValue() {
        return dealValue;
    }

    public String toString() {
        return super.toString() +
                "Deal value: " + this.dealValue;
    }
}
