package engine.dto;

import engine.stockMarket.users.User;

import java.util.HashMap;
import java.util.Map;

public class DTOUser {
    private final String name;
    private final int totalHoldings;
    private final int totalStocksValue;
    private final Map<String, Integer> holdings;

    public DTOUser(User user) {
        this.name = user.getName();
        this.totalHoldings = user.getHoldings().getTotalHoldings();
        this.totalStocksValue = user.getHoldings().getTotalStocksValue();
        this.holdings = new HashMap<>();
        user.getHoldings().getItems().forEach((symbol, item)-> {
            holdings.put(symbol, item.getQuantity());
        });
    }

    public String getUserName() { return name; }

    public int getTotalHoldings() {
        return totalHoldings;
    }

    public Map<String, Integer> getHoldings() {
        return holdings;
    }

    public int getTotalStocksValue() {
        return totalStocksValue;
    }
}
