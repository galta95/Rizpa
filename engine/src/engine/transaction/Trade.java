package engine.transaction;

public class Trade {
    public enum OrderType {
        LMT, MKT
    }
    private String date;
    private int numOfShares;
    private int price;
    private OrderType orderType;

    public Trade(String date, int numOfShares, int price, OrderType orderType) {
        this.date = date;
        this.numOfShares = numOfShares;
        this.price = price;
        this.orderType = orderType;
    }

    public String getDate() {
        return date;
    }

    public int getNumOfShares() {
        return numOfShares;
    }

    public int getPrice() {
        return price;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setNumOfShares(int numOfShares) {
        this.numOfShares = numOfShares;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
