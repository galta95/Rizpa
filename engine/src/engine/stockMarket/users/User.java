package engine.stockMarket.users;

import dataManager.generated.RseUser;
import engine.stockMarket.stocks.Stocks;

public class User {
    private final String name;
    private final Holdings holdings;

    public User(RseUser rseUser, Stocks stocks) {
        this.name = rseUser.getName().toUpperCase();
        this.holdings = new Holdings(rseUser.getRseHoldings(), stocks);
    }

    public String getName() {
        return name;
    }

    public Holdings getHoldings() {
        return holdings;
    }

    public void updateHoldings(String symbol, int numOfShares) {
        Item stock = this.holdings.getItemBySymbol(symbol);
        if (stock == null) {
            this.holdings.addItem(symbol, numOfShares);
        } else {
            stock.updateQuantity(numOfShares);
            if (stock.getQuantity() == 0) {
                this.holdings.removeItem(symbol);
            }
        }
        holdings.setTotalHoldings();
        holdings.updateTotalStocksValue();
    }
}
