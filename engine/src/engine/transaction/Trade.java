package engine.transaction;

import engine.stockMarket.users.User;

public class Trade {
    public enum OrderType {
        LMT, MKT, FOK, IOC
    }
    private String date;
    private int numOfShares;
    private int price;
    private OrderType orderType;
    private User user;

    public Trade(String date, int numOfShares, int price, OrderType orderType, User user) {
        this.date = date;
        this.numOfShares = numOfShares;
        this.price = price;
        this.orderType = orderType;
        this.user = user;
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

    public User getUser() {
        return user;
    }
}
