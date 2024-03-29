package engine.dto;

import engine.stockMarket.users.Movement;
import engine.stockMarket.users.User;
import engine.stockMarket.users.User.Permissions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DTOUser {
    private final String name;
    private final int totalHoldings;
    //    private final int totalStocksValue;
    private final Map<String, Integer> holdings;
    private final Permissions permission;
    private int money;
    private final String password;
    private List<DTOMovment> movments;
    private boolean isNewDealAlert;
    private DTODeal newDeal;

    public DTOUser(User user) {
        this.name = user.getName();
        this.totalHoldings = user.getHoldings().getTotalHoldings();
//        this.totalStocksValue = user.getHoldings().getTotalStocksValue();
        this.holdings = new HashMap<>();
        user.getHoldings().getItems().forEach((symbol, item) -> {
            holdings.put(symbol, item.getQuantity());
        });
        this.permission = user.getPermission();
        this.money = user.getMoney();
        this.password = user.getPassword();
        initList(user.getMovements());
        this.isNewDealAlert = user.isNewDealAlert();
        if (user.getNewDeal() == null) {
            this.newDeal = null;
        } else {
            this.newDeal = new DTODeal(user.getNewDeal());
        }
    }

    private void initList(List<Movement> movements) {
        this.movments = new LinkedList<>();

        movements.forEach((movement) -> {
            DTOMovment dtoMovment = new DTOMovment(movement);
            this.movments.add(dtoMovment);
        });
    }

    public boolean isNewDealAlert() {
        return isNewDealAlert;
    }

    public DTODeal getNewDeal() {
        return newDeal;
    }

    public String getUserName() {
        return name;
    }

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
