package engine.dto;

import engine.stockMarket.Stock;

public class DTOStock {
    private final String companyName;
    private final String symbol;
    private final int price;
    private final int dealsCount;
    private final int cycle;

    public DTOStock(Stock stock) {
        this.companyName = stock.getCompanyName();
        this.symbol = stock.getSymbol();
        this.price = stock.getPrice();
        this.dealsCount = stock.getDealsCount();
        this.cycle = stock.getCycle();
    }

    public int getCycle() {
        return cycle;
    }

    public int getDealsCount() {
        return dealsCount;
    }

    public int getPrice() {
        return price;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getSymbol() {
        return symbol;
    }
}
