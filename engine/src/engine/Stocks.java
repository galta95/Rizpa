package engine;

import dataManager.jaxb.generated.RseStock;
import dataManager.jaxb.generated.RseStocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stocks {
    Map<String, Stock> stock;

    public Stocks(RseStocks rseStocks) {
        List<RseStock> rseStock = rseStocks.getRseStock();
        this.stock = new HashMap<>();

        for (RseStock item : rseStock) {
            Stock stock = new Stock(item);
            this.stock.put(stock.getSymbol(), stock);
        }
    }

    public Map<String, Stock> getStocks() {
        return this.stock;
    }
}
