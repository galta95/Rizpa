package engine.transaction;

import engine.stockMarket.users.User;

public class Deal extends Trade {
    private int dealValue;
    private User consumer;
    private User producer;

    public Deal(String date, int numOfShares, int price, int dealValue, OrderType orderType,
                User consumer, User producer) {
        super(date, numOfShares, price, orderType, null);
        this.dealValue = dealValue;
        this.consumer = consumer;
        this.producer = producer;
    }

    public int getDealValue() {
        return dealValue;
    }

    public User getConsumer() {
        return consumer;
    }

    public User getProducer() {
        return producer;
    }
}
