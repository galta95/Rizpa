package engine.dto;

import engine.transaction.Trade;

public class DTODeal {
    private final int dealValue;
    private final String date;
    private final int numOfShares;
    private final int price;
    private Trade.OrderType orderType;
    private final String consumer;
    private final String producer;

    public DTODeal(int dealValue, String date, int numOfShares, int price, Trade.OrderType orderType,
                   String consumer, String producer) {
        this.dealValue = dealValue;
        this.date = date;
        this.numOfShares = numOfShares;
        this.price = price;
        this.orderType = orderType;
        this.consumer = consumer;
        this.producer = producer;
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

    public String getConsumer() {
        return consumer;
    }

    public String getProducer() {
        return producer;
    }
}
