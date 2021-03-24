package engine;

import dataManager.jaxb.generated.RseStock;

public class Stock {
    private String symbol;
    private String companyName;
    private int price;

    public Stock(String symbol, String companyName, int price) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
    }

    public Stock(RseStock stock) {
        this.symbol = stock.getRseSymbol();
        this.companyName = stock.getRseCompanyName();
        this.price = stock.getRsePrice();
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

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
