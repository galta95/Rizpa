package engine.stockMarket.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import dataManager.generated.RseHoldings;
import dataManager.generated.RseItem;
import engine.stockMarket.stocks.Stocks;
import errors.NotFoundError;

public class Holdings {
    private final Map<String, Item> items;
    private int totalHoldings;

    public Holdings(RseHoldings rseHoldings, Stocks stocks) {
        List<RseItem> rseHolding = rseHoldings.getRseItem();
        this.items = new HashMap<>();

        for (RseItem item : rseHolding) {
            Item currItem = new Item(item);
            String symbol = currItem.getSymbol();
            this.isSymbolExists(symbol, stocks);
            this.items.put(symbol, currItem);
        }
        this.totalHoldings = items.size();
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
}
