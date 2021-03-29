package engine;

import dataManager.jaxb.generated.RseStock;

import java.util.ArrayList;
import java.util.List;

public class Stock {
    private String symbol;
    private String companyName;
    private int price;
    private List<Deal> deals;
    private int dealsCount;
    private int cycle;

    public Stock(String symbol, String companyName, int price) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
        this.deals = new ArrayList<Deal>();
        this.dealsCount = 0;
        this.cycle = 0;
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

    public List<Deal> getDeals() { return deals; }

    public int getDealsCount() { return dealsCount; }

    public int getCycle() { return cycle; }

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
