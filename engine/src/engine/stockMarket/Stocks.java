package engine.stockMarket;

import Errors.ConstraintError;
import Errors.NotFoundError;
import Errors.NotUpperCaseError;
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
        char ch;

        this.stocks = new HashMap<>();
        this.companyToSymbole = new HashMap<>();

        for (RseStock item : rseStock) {
            Stock stock = new Stock(item);
            String stockSymbol = stock.getSymbol();
            String stockCompanyName = stock.getCompanyName();

            for (int i = 0; i < stockSymbol.length(); i++) {
                ch = stockSymbol.charAt(i);
                if (!Character.isUpperCase(ch))
                {
                    throw new NotUpperCaseError(stockSymbol);
                }
            }
            
            if (this.stocks.get(stockSymbol) != null) {
                throw new ConstraintError(stockSymbol);
            }
            if (this.companyToSymbole.get(stockCompanyName) != null) {
                throw new ConstraintError(stockCompanyName);
            }

            this.stocks.put(stockSymbol, stock);
            this.companyToSymbole.put(stockCompanyName, stockSymbol);
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
