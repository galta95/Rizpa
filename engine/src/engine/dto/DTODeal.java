package engine.dto;

import engine.transaction.Deal;
import engine.transaction.Trade;

public class DTODeal {
    private final int dealValue;
    private final String date;
    private final int numOfShares;
    private final int price;
    private final Trade.OrderType orderType;
    private final String consumer;
    private final String producer;
    private final String symbol;

    public DTODeal(int dealValue, String date, int numOfShares, int price, Trade.OrderType orderType,
                   String consumer, String producer, String symbol) {
        this.dealValue = dealValue;
        this.date = date;
        this.numOfShares = numOfShares;
        this.price = price;
        this.orderType = orderType;
        this.consumer = consumer;
        this.producer = producer;
        this.symbol = symbol;
    }

    public DTODeal(Deal deal) {
        this.dealValue = deal.getDealValue();
        this.date = deal.getDate();
        this.numOfShares = deal.getNumOfShares();
        this.price = deal.getPrice();
        this.orderType = deal.getOrderType();
        this.consumer = deal.getConsumer().getName();
        this.producer = deal.getProducer().getName();
        this.symbol = deal.getSymbol();
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

    public String getSymbol() {
        return symbol;
    }
}
