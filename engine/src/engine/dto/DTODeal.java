package engine.dto;

import engine.transaction.Trade;

public class DTODeal {
    private final int dealValue;
    private final String date;
    private final int numOfShares;
    private final int price;
    private Trade.OrderType orderType;

    public DTODeal(int dealValue, String date, int numOfShares, int price, Trade.OrderType orderType) {
        this.dealValue = dealValue;
        this.date = date;
        this.numOfShares = numOfShares;
        this.price = price;
        this.orderType = orderType;
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

    public Trade.OrderType getOrderType() {
        return orderType;
    }
}
