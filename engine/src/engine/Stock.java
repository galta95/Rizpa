package engine;

import dataManager.jaxb.generated.RseStock;

import java.util.LinkedList;
import java.util.List;

public class Stock {
    private String symbol;
    private String companyName;
    private int price;
    private List<Deal> deals;
    private List<Trade> sells;
    private List<Trade> buys;
    private int dealsCount;
    private int cycle;

    public Stock(String symbol, String companyName, int price) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
        this.deals = new LinkedList<>();
        this.sells = new LinkedList<>();
        this.buys = new LinkedList<>();
        this.dealsCount = 0;
        this.cycle = 0;
    }

    public Stock(RseStock stock) {
        this.symbol = stock.getRseSymbol();
        this.companyName = stock.getRseCompanyName();
        this.price = stock.getRsePrice();
        this.deals = new LinkedList<>();
        this.sells = new LinkedList<>();
        this.buys = new LinkedList<>();
        this.dealsCount = 0;
        this.cycle = 0;
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

    public List<Deal> getDeals() {
        return deals;
    }

    public List<Trade> getSells() {
        return sells;
    }

    public List<Trade> getBuys() {
        return buys;
    }

    public int getDealsCount() {
        return dealsCount;
    }

    public int getCycle() {
        return cycle;
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

    public void addSell(Trade trade) {
        Trade curr;

        for (int i = 0; i < this.sells.size(); i++) {
            curr = this.sells.get(i);

            if (trade.getPrice() < curr.getPrice()) {
                this.sells.add(i, trade);
                return;
            }
        }

        this.sells.add(trade);
    }

    public void addBuy(Trade trade) {
        Trade curr;

        for (int i = 0; i < this.buys.size(); i++) {
            curr = this.buys.get(i);

            if (trade.getPrice() > curr.getPrice()) {
                this.buys.add(i, trade);
                return;
            }
        }

        this.buys.add(trade);
    }

    public Trade sell(Trade trade) {
        for (Trade currBuy : this.buys) {
            if (currBuy.getPrice() >= trade.getPrice())
                makeDeal(trade, currBuy, buys);
        }

        return trade;
    }

    public Trade buy(Trade trade) {
        for (Trade currSell : this.sells) {
            if (currSell.getPrice() <= trade.getPrice())
                makeDeal(trade, currSell, sells);
        }

        return trade;
    }

    private void makeDeal(Trade trade, Trade currSell, List<Trade> sells) {
        int numOfShares;
        int price;
        int dealValue;
        Deal newDeal;

        if (currSell.getNumOfShares() >= trade.getNumOfShares()) {
            numOfShares = trade.getNumOfShares();
            price = currSell.getPrice();
            dealValue = price * numOfShares;

            trade.setNumOfShares(0);
            if (currSell.getNumOfShares() == numOfShares) {
                sells.remove(currSell);
            } else
                currSell.setNumOfShares(currSell.getNumOfShares() - numOfShares);
        } else {
            numOfShares = currSell.getNumOfShares();
            price = currSell.getPrice();
            dealValue = price * numOfShares;

            trade.setNumOfShares(trade.getNumOfShares() - numOfShares);
            sells.remove(currSell);
        }

        newDeal = new Deal(trade.getDate(), numOfShares, price, dealValue);
        this.deals.add(newDeal); // TODO: make sure it added to the right place.
        this.dealsCount++;
        this.price = price;
        this.cycle += dealValue;

    }

    public String toString() {
        return "Stock symbol: " + this.symbol + "\n" +
                "Stock company name: " + this.companyName + "\n" +
                "Current price: " + this.price + "\n" +
                "Deals until now: " + this.dealsCount + "\n" +
                "Stock cycle: " + this.cycle;
    }
}
