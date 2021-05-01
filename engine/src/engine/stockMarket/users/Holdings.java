package engine.stockMarket.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import dataManager.generated.RseHoldings;
import dataManager.generated.RseItem;
import engine.stockMarket.stocks.Stocks;
import errors.NotFoundError;

public class Holdings {
    private Map<String, Item> items;

    public Holdings(RseHoldings rseHoldings, Stocks stocks) {
        List<RseItem> rseHolding = rseHoldings.getRseItem();
        this.items = new HashMap<>();

        for (RseItem item : rseHolding) {
            Item currItem = new Item(item);
            String symbol = currItem.getSymbol();
            this.isSymbolExsits(symbol, stocks);
            this.items.put(symbol, currItem);
        }
    }

    private void isSymbolExsits(String symbol, Stocks stocks) throws NotFoundError {
        stocks.getStockBySymbol(symbol);
    }
}
