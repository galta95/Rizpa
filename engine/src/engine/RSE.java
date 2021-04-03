package engine;

import dataManager.jaxb.generated.RizpaStockExchangeDescriptor;


public class RSE {
    private Stocks stocks;

    public RSE(RizpaStockExchangeDescriptor rsed) {
        this.stocks = new Stocks(rsed.getRseStocks());
    }

    public Stocks getStocks() {
        return this.stocks;
    }

    public Trade buyTrade(Trade trade, String symbol) {
        Stock stock = this.stocks.gerStockBySymbol(symbol);
        if (stock == null)
            return null;

        if (trade.getPrice() == 0) // price stock that is 0, means null
            trade.setPrice(stock.getPrice());

        return stock.buy(trade);
    }

    public void addToBuyList(Trade trade, String symbol) {
        Stock stock = this.stocks.gerStockBySymbol(symbol);
        if (stock != null) {
            stock.addBuy(trade);
        }
    }

    public Trade sellTrade(Trade trade, String symbol) {
        Stock stock = this.stocks.gerStockBySymbol(symbol);
        if (stock == null)
            return null;

        if (trade.getPrice() == 0) // price stock that is 0, means null
            trade.setPrice(stock.getPrice());

        return stock.sell(trade);
    }

    public void addToSellList(Trade trade, String symbol) {
        Stock stock = this.stocks.gerStockBySymbol(symbol);
        if (stock != null) {
            stock.addSell(trade);
        }
    }
}
