package engine.dto;

import engine.stockMarket.stocks.Stock;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DTOStocks implements Iterable<DTOStock> {
    private final List<DTOStock> stocks;

    public DTOStocks(Map<String, Stock> stocks) {
        this.stocks = new LinkedList<>();
        stocks.forEach((symbol, stock) -> {
           this.stocks.add(new DTOStock(stock));
        });
    }

    public List<DTOStock> getDeals() {
        return stocks;
    }

    @Override
    public Iterator<DTOStock> iterator() {
        return stocks.iterator();
    }
}
