package engine.dto;

import engine.stockMarket.users.User;
import engine.stockMarket.users.User.Permissions;

import java.util.HashMap;
import java.util.Map;

public class DTOUser {
    private final String name;
    private final int totalHoldings;
//    private final int totalStocksValue;
    private final Map<String, Integer> holdings;
    private final Permissions permission;
    private int money;
    private String password;

    public DTOUser(User user) {
        this.name = user.getName();
        this.totalHoldings = user.getHoldings().getTotalHoldings();
//        this.totalStocksValue = user.getHoldings().getTotalStocksValue();
        this.holdings = new HashMap<>();
        user.getHoldings().getItems().forEach((symbol, item)-> {
            holdings.put(symbol, item.getQuantity());
        });
        this.permission = user.getPermission();
        this.money = user.getMoney();
        this.password = user.getPassword();
    }

    public String getUserName() { return name; }

    public User.Permissions getPermission() {
        return permission;
    }

    public int getTotalHoldings() {
        return totalHoldings;
    }

    public Map<String, Integer> getHoldings() {
        return holdings;
    }

//    public int getTotalStocksValue() {
//        return totalStocksValue;
//    }

    public String getPassword() {
        return password;
    }
}
