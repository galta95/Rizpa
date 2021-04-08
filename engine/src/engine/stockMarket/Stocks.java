package engine.stockMarket;

import Errors.ConstraintError;
import Errors.NotFoundError;
import dataManager.jaxb.generated.RseStock;
import dataManager.jaxb.generated.RseStocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stocks {
    private Map<String, Stock> stocks;
    private Map<String, String> companyToSymbole;

    public Stocks(RseStocks rseStocks) throws ConstraintError {
        List<RseStock> rseStock = rseStocks.getRseStock();

        this.stocks = new HashMap<>();
        this.companyToSymbole = new HashMap<>();

        for (RseStock item : rseStock) {
            Stock stock = new Stock(item);

            if (this.stocks.get(stock.getSymbol()) != null) {
                throw new ConstraintError(stock.getSymbol());
            }
            if (this.companyToSymbole.get(stock.getCompanyName()) != null) {
                throw new ConstraintError(stock.getCompanyName());
            }

            this.stocks.put(stock.getSymbol(), stock);
            this.companyToSymbole.put(stock.getCompanyName(), stock.getSymbol());
        }
    }

    public Map<String, Stock> getStocks() {
        return this.stocks;
    }

    public Stock getStockByName(String name) throws NotFoundError {
        String symbol = companyToSymbole.get(name);
        Stock stock = stocks.get(name);

        if (symbol == null && stock == null) {
            throw new NotFoundError(name);
        }

        if (symbol != null ) {
            stock = stocks.get(symbol);
        }

        return stock;
    }

    public String stocksSummary() {
        String res = "";

        for (Map.Entry<String, Stock> stock : stocks.entrySet()) {
            res += stock.getValue().printSummary() + "\n";
        }

        return res;
    }
}
