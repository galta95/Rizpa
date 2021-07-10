package engine.stockMarket.stocks;

import dataManager.generated.RseStock;
import engine.stockMarket.users.Holdings;
import engine.stockMarket.users.User;
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
        this.symbol = symbol;
        this.companyName = companyName.toUpperCase();
        this.price = price;
        this.deals = new LinkedList<>();
        this.sells = new LinkedList<>();
        this.buys = new LinkedList<>();
        this.dealsCount = 0;
        this.cycle = 0;
    }

    public Stock(RseStock stock) {
        this.symbol = stock.getRseSymbol();
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
            throw new InputMismatchException(this.symbol + " - contains spaces");
        }
        if (this.companyName.startsWith(" ") || this.companyName.endsWith(" ")) {
            throw new InputMismatchException(this.companyName + " - contains spaces");
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
            if (currSell.getPrice() >= trade.getPrice() && trade.getNumOfShares() > 0) {
                makeDeal(trade, currSell, this.buys);
                return trade;
            } else
                break;
        }
        return trade;
    }

    public Trade buy(Trade trade) {
        for (Trade currSell : this.sells) {
            if (currSell.getPrice() <= trade.getPrice() && trade.getNumOfShares() > 0) {
                makeDeal(trade, currSell, this.sells);
                return trade;
            } else
                break;
        }
        return trade;
    }

    private void makeDeal(Trade consumer, Trade producer, List<Trade> stockTradeList) {
        int numOfShares;
        int price = producer.getPrice();

        if (producer.getNumOfShares() > consumer.getNumOfShares()) {
            numOfShares = consumer.getNumOfShares();

            consumer.setNumOfShares(0);
            producer.setNumOfShares(producer.getNumOfShares() - numOfShares);
        } else {
            numOfShares = producer.getNumOfShares();

            consumer.setNumOfShares(consumer.getNumOfShares() - numOfShares);
            stockTradeList.remove(producer);
        }

        int dealValue = price * numOfShares;
        Deal newDeal = new Deal(consumer.getDate(), numOfShares, price, dealValue,
                consumer.getOrderType(), consumer.getUser(), producer.getUser());

        consumer.getUser().updateHoldings(symbol, numOfShares);
        producer.getUser().updateHoldings(symbol, numOfShares);

        this.deals.add(0, newDeal);
        this.dealsCount++;
        this.price = price;
        this.cycle += dealValue;
    }
}
