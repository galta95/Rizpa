package engine;

import dataManager.jaxb.generated.RseStock;
import dataManager.jaxb.generated.RseStocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stocks {
    Map<String, Stock> stocks;

    public Stocks(RseStocks rseStocks) {
        List<RseStock> rseStock = rseStocks.getRseStock();
        this.stocks = new HashMap<>();

        for (RseStock item : rseStock) {
            Stock stock = new Stock(item);
            this.stocks.put(stock.getSymbol(), stock);
        }
    }

    public Map<String, Stock> getStocks() {
        return this.stocks;
    }

    public Stock gerStockBySymbol(String symbol) {
        return stocks.get(symbol);
    }

    public String stocksSummary() {
        String res = "";

        for (Map.Entry<String, Stock> stock : stocks.entrySet()) {
            res += stock.getValue().printSummary() + "\n";
        }

        return res;
    }
}
