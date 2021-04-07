package engine;

import java.util.Date;

public class Deal extends Trade {
    private int dealValue;

    public Deal(Date date, int numOfShares, int price, int dealValue) {
        super(date, numOfShares, price);
        this.dealValue = dealValue;
    }

    public int getDealValue() {
        return dealValue;
    }

    public String toString() {
        return super.toString() +
                "Deal value: " + this.dealValue + "\n";
    }
}
