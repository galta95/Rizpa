package engine.dto;

public class DTODeal {
    private final int dealValue;
    private final String date;
    private final int numOfShares;
    private final int price;

    public DTODeal(int dealValue, String date, int numOfShares, int price) {
        this.dealValue = dealValue;
        this.date = date;
        this.numOfShares = numOfShares;
        this.price = price;
    }

    public int getDealValue() {
        return dealValue;
    }

    public String getDate() {
        return date;
    }

    public int getPrice() {
        return price;
    }

    public int getNumOfShares() {
        return numOfShares;
    }
}
