package engine.stockMarket.users;

import dataManager.generated.RseHoldings;
import dataManager.generated.RseItem;
import dataManager.generated.RseStock;
import dataManager.generated.RseStocks;
import engine.stockMarket.stocks.Stocks;
import engine.transaction.Trade;
import errors.NotFoundError;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class User {
    public enum Permissions {
        ADMIN, BROKER
    }

    private final String name;
    private final Holdings holdings;
    private final Permissions permission;
    private final String password;
    private int money;
    private final List<Movement> movements;

    public User(String name, String password, Permissions permission) {
        this.name = name.toUpperCase();
        this.permission = permission;
        this.password = password;
        this.holdings = new Holdings();
        this.money = 0;
        this.movements = new LinkedList<>();
    }

    public List<Movement> getMovements() {
        return movements;
    }

    public String getName() {
        return name;
    }

    public Holdings getHoldings() {
        return holdings;
    }

    public int getMoney() {
        return money;
    }

    public String getPassword() {
        return password;
    }

    public void addMoney(int money, Movement.MovementType movementType, String symbol) {
        String date = DateTimeFormatter.ofPattern("HH:mm:ss:SSS").format(LocalDateTime.now());
        int moneyAfter = this.money + money;

        Movement movement = new Movement(movementType, symbol, date, money, this.money, moneyAfter);
        this.movements.add(0, movement);

        this.money = moneyAfter;
    }

    public void subMoney(int money, Movement.MovementType movementType, String symbol) {
        String date = DateTimeFormatter.ofPattern("HH:mm:ss:SSS").format(LocalDateTime.now());
        int moneyAfter = this.money - money;

        Movement movement = new Movement(movementType, symbol, date, (money * -1), this.money, moneyAfter);
        this.movements.add(0, movement);

        this.money = moneyAfter;
    }

    public Permissions getPermission() {
        return permission;
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
//        holdings.updateTotalStocksValue();
    }

    public void addHoldingsFromXml(RseHoldings rseHoldings, Stocks stocks) throws NotFoundError {
        List<RseItem> rseItemsList = rseHoldings.getRseItem();
        this.holdings.setStocks(stocks);

        rseItemsList.forEach(rseItem -> {
            if (this.holdings.getItemBySymbol(rseItem.getSymbol().toUpperCase()) != null) {
                this.updateHoldings(rseItem.getSymbol(), rseItem.getQuantity());
            } else {
                this.holdings.addItem(rseItem.getSymbol(), rseItem.getQuantity());
                holdings.setTotalHoldings();
//                holdings.updateTotalStocksValue();
            }
        });
    }
}
