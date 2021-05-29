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
}
