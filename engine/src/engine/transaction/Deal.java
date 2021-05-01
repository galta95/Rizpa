package engine.transaction;

public class Deal extends Trade {
    private int dealValue;

    public Deal(String date, int numOfShares, int price, int dealValue, OrderType orderType) {
        super(date, numOfShares, price, orderType);
        this.dealValue = dealValue;
    }

    public int getDealValue() {
        return dealValue;
    }
}
