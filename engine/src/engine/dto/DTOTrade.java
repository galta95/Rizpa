package engine.dto;

import engine.transaction.Trade;

public class DTOTrade {
    private final String date;
    private final int numOfShares;
    private final int price;
    private Trade.OrderType orderType;
    private String userName;

    public DTOTrade(String date, int numOfShares, int price, Trade.OrderType orderType, String userName) {
        this.date = date;
        this.numOfShares = numOfShares;
        this.price = price;
        this.orderType = orderType;
        this.userName = userName;
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

    public Trade.OrderType getOrderType() {
        return orderType;
    }

    public String getUserName() {
        return userName;
    }
}
