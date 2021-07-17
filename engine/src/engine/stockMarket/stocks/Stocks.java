package engine.stockMarket.stocks;

import dataManager.generated.RseStock;
import dataManager.generated.RseStocks;
import errors.ConstraintError;
import errors.NotFoundError;
import errors.NotUpperCaseError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stocks {
    private Map<String, Stock> stocks;
    private Map<String, String> companyToSymbole;

    public Stocks() {
        stocks = new HashMap<>();
        companyToSymbole = new HashMap<>();
    }

    public void addStocksFromXml(RseStocks rseStocks) throws NotUpperCaseError {
        List<RseStock> rseStock = rseStocks.getRseStock();
        char ch;

        for (RseStock item : rseStock) {
            Stock stock = new Stock(item);
            String stockSymbol = stock.getSymbol();
            String stockCompanyName = stock.getCompanyName();

            for (int i = 0; i < stockSymbol.length(); i++) {
                ch = stockSymbol.charAt(i);
                if (!Character.isUpperCase(ch)) {
                    throw new NotUpperCaseError(stockSymbol);
                }
            }

            if (this.stocks.get(stockSymbol) != null || this.companyToSymbole.get(stockCompanyName) != null) {
                continue;
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

    public Stock getStockBySymbol(String symbol) throws NotFoundError {
        Stock stock = stocks.get(symbol);

        if (symbol == null) {
            throw new NotFoundError(symbol);
        }

        return stock;
    }

    public boolean isStockExists(String symbol) {
        return getStockBySymbol(symbol.toUpperCase()) != null;
    }

    public void addStock(Stock newStock) throws ConstraintError {
        if (isStockExists(newStock.getSymbol())) {
            throw new ConstraintError(newStock.getSymbol());
        }
        this.stocks.put(newStock.getSymbol(), newStock);
        this.companyToSymbole.put(newStock.getCompanyName(), newStock.getSymbol());
    }
}
