package engine;

import dataManager.jaxb.generated.RseStock;
import dataManager.jaxb.generated.RseStocks;

import java.util.HashMap;
import java.util.List;

public class Stocks {
    HashMap<String, Stock> stock;

    public Stocks(RseStocks rseStocks) {
        List<RseStock> rseStock = rseStocks.getRseStock();
        this.stock = new HashMap<>();

        for (RseStock item : rseStock) {
            Stock stock = new Stock(item);
            this.stock.put(stock.getSymbol(), stock);
        }
    }
}
