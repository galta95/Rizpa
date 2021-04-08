package engine.stockMarket;

import dataManager.jaxb.generated.RseStock;
import engine.transaction.Deal;
import engine.transaction.Trade;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;

public class Stock {
    private final List<Deal> deals;
    private final List<Trade> sells;
    private final List<Trade> buys;
    private String symbol;
    private String companyName;
    private int price;
    private int dealsCount;
    private int cycle;

    public Stock(String symbol, String companyName, int price) {
        this.symbol = symbol.toUpperCase();
        this.companyName = companyName.toUpperCase();
        this.price = price;
        this.deals = new LinkedList<>();
        this.sells = new LinkedList<>();
        this.buys = new LinkedList<>();
        this.dealsCount = 0;
        this.cycle = 0;
    }

    public Stock(RseStock stock) {
        this.symbol = stock.getRseSymbol().toUpperCase();
        this.companyName = stock.getRseCompanyName().toUpperCase();
        this.price = stock.getRsePrice();
        this.deals = new LinkedList<>();
        this.sells = new LinkedList<>();
        this.buys = new LinkedList<>();
        this.dealsCount = 0;
        this.cycle = 0;
        validations();
    }

    private void validations() {
        if (this.symbol.contains(" ")) {
            throw new InputMismatchException(this.symbol + " -  contains spaces");
        }
        if (this.companyName.startsWith(" ") || this.companyName.endsWith(" ")) {
            throw new InputMismatchException(this.companyName + " -  contains spaces");
        }
        if (this.price <= 0) {
            throw new InputMismatchException(this.symbol + " - price is not positive decimal");
        }
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

    public void addSellToSellsList(Trade trade) {
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

    public void addBuyToBuysList(Trade trade) {
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
        for (Trade currSell : this.buys) {
            if (currSell.getPrice() >= trade.getPrice()) {
                makeDeal(trade, currSell, this.buys);
                if (trade.getNumOfShares() == 0)
                    break;
            } else
                break;
        }
        return trade;
    }

    public Trade buy(Trade trade) {
        for (Trade currSell : this.sells) {
            if (currSell.getPrice() <= trade.getPrice()) {
                makeDeal(trade, currSell, this.sells);
                if (trade.getNumOfShares() == 0)
                    break;
            } else
                break;
        }
        return trade;
    }

    private void makeDeal(Trade consumer, Trade producer, List<Trade> stockTradeList) {
        int numOfShares = consumer.getNumOfShares();
        int price = producer.getPrice();
        int dealValue = price * numOfShares;
        Deal newDeal = new Deal(consumer.getDate(), numOfShares, price, dealValue);

        if (producer.getNumOfShares() > consumer.getNumOfShares()) {
            consumer.setNumOfShares(0);
            producer.setNumOfShares(producer.getNumOfShares() - numOfShares);
        } else {
            consumer.setNumOfShares(consumer.getNumOfShares() - numOfShares);
            stockTradeList.remove(producer);
        }
        this.deals.add(newDeal); // TODO: make sure it added to the right place.
        this.dealsCount++;
        this.price = price;
        this.cycle += dealValue;
    }

    public String printSummary() {
        int potentialValue = 0;
        String res = "++++++++++++++++++++++++++\n" +
                "Stock symbol: " + this.symbol + "\n" +
                "Stock company name: " + this.companyName + "\n" +
                "Sells waiting list: \n";
        if (sells.size() == 0)
            res += "There are no sell request \n\n";
        else {
            for (Trade sell : this.sells) {
                res += sell + "\n";
                potentialValue += sell.getNumOfShares() * sell.getPrice();
            }
        }
        res += "Total potential sells deals value: " + potentialValue + "\n\n";

        potentialValue = 0;
        res += "Buys waiting list: \n";
        if (buys.size() == 0)
            res += "There are no buy request \n\n";
        else {
            for (Trade buy : this.buys) {
                res += buy + "\n";
                potentialValue += buy.getNumOfShares() * buy.getPrice();
            }
        }
        res += "Total potential buys deals value: " + potentialValue + "\n\n";

        res += "Deals history: \n";
        if (deals.size() == 0)
            res += "There are no deals yet \n\n";
        else {
            for (Deal deal : this.deals)
                res += deal + "\n";
        }
        res += "Stock deals value (cycle): " + this.cycle + "\n";

        res += "++++++++++++++++++++++++++\n";

        return res;
    }

    public String toString() {
        return "Stock symbol: " + this.symbol + "\n" +
                "Stock company name: " + this.companyName + "\n" +
                "Current price: " + this.price + "\n" +
                "Deals until now: " + this.dealsCount + "\n" +
                "Stock cycle: " + this.cycle;
    }
}
