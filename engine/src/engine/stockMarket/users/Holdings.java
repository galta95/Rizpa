package engine.stockMarket.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.stockMarket.stocks.Stock;
import engine.stockMarket.stocks.Stocks;
import errors.NotFoundError;

public class Holdings {
    private final Map<String, Item> items;
    private int totalHoldings;
    private int totalStocksValue;
    private final Stocks stocks;

    public Holdings() {
        items = new HashMap<>();
        stocks = new Stocks();
    }

    private void isSymbolExists(String symbol, Stocks stocks) throws NotFoundError {
        stocks.getStockBySymbol(symbol);
    }

    public int getTotalHoldings() {
        return totalHoldings;
    }

    public Map<String, Item> getItems() {
        return items;
    }

    public void addTotalHoldings(int totalHoldings) {
        this.totalHoldings += totalHoldings;
    }

    public void subTotalHoldings(int totalHoldings) {
        this.totalHoldings -= totalHoldings;
    }

    public void setTotalHoldings() {
        this.totalHoldings = items.size();
    }

    public Item getItemBySymbol(String symbol) {
        return items.get(symbol);
    }

    public void addItem(String symbol, int numOfShares) {
        Item newItem = new Item(symbol, numOfShares);
        items.put(symbol, newItem);
    }

    public void removeItem(String symbol) {
        items.remove(symbol);
    }

    public void updateTotalStocksValue() {
        totalStocksValue = 0;
        items.forEach((String name, Item item) -> {
            Stock stock = stocks.getStockBySymbol(item.getSymbol());
            totalStocksValue += item.getQuantity() * stock.getPrice();
        });
    }

    public int getTotalStocksValue() {
        return totalStocksValue;
    }
}
