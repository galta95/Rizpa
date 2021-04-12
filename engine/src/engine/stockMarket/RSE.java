package engine.stockMarket;

import Errors.NotFoundError;
import dataManager.jaxb.generated.RizpaStockExchangeDescriptor;
import engine.transaction.Trade;


public class RSE {
    private Stocks stocks;

    public RSE(RizpaStockExchangeDescriptor rsed) {
        this.stocks = new Stocks(rsed.getRseStocks());
    }

    public Stocks getStocks() {
        return this.stocks;
    }

    public void assertStockExists(String name) throws NotFoundError {
        this.stocks.getStockByName(name);
    }

    // TODO: duplicate code, need to be change
    public Trade buyTrade(Trade trade, String symbol) throws NotFoundError {
        Stock stock = this.stocks.getStockByName(symbol);
        if (trade.getPrice() == 0) { // price stock that is 0, means null
            trade.setPrice(stock.getPrice());
        }
        return stock.buy(trade);
    }

    public void addToBuyList(Trade trade, String symbol) throws NotFoundError {
        Stock stock = this.stocks.getStockByName(symbol);
        stock.addBuyToBuysList(trade);
    }

    public Trade sellTrade(Trade trade, String symbol) throws NotFoundError {
        Stock stock = this.stocks.getStockByName(symbol);
        if (trade.getPrice() == 0) // price stock that is 0, means null
            trade.setPrice(stock.getPrice());

        return stock.sell(trade);
    }

    public void addToSellList(Trade trade, String symbol) throws NotFoundError {
        Stock stock = this.stocks.getStockByName(symbol);
        stock.addSellToSellsList(trade);
    }

    public boolean isStockBuyListEmpty(String symbol) {
        Stock stock = this.stocks.getStockByName(symbol);
        return stock.getBuys().isEmpty();
    }

    public boolean isStockSellListEmpty(String symbol) {
        Stock stock = this.stocks.getStockByName(symbol);
        return stock.getSells().isEmpty();
    }

    public int getStockPrice(String symbol) {
        Stock stock = this.stocks.getStockByName(symbol);
        return stock.getPrice();
    }

    public int getStockBuyPrice(String symbol) {
        Stock stock = this.stocks.getStockByName(symbol);
        return stock.getBuys().get(0).getPrice();
    }

    public int getStockSellPrice(String symbol) {
        Stock stock = this.stocks.getStockByName(symbol);
        return stock.getSells().get(0).getPrice();
    }
}