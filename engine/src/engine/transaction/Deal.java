package engine.transaction;

public class Deal extends Trade {
    private int dealValue;

    public Deal(String date, int numOfShares, int price, int dealValue) {
        super(date, numOfShares, price);
        this.dealValue = dealValue;
    }

    public int getDealValue() {
        return dealValue;
    }
}
