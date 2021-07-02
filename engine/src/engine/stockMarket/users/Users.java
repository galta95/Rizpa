package engine.stockMarket.users;

import engine.dto.DTOUser;
import engine.stockMarket.stocks.Stocks;
import errors.ConstraintError;
import errors.NotFoundError;
import java.util.HashMap;
import java.util.Map;

public class Users {
    private final Map<String, User> users;

    public Users() {
        users = new HashMap<>();
    }

    public void addUser(User newUser) throws ConstraintError {
        if (this.isUserExists(newUser.getName())) {
            throw new ConstraintError(newUser.getName());
        }
        this.users.put(newUser.getName(), newUser);
    }

    public final Map<String, User> getUsers() {
        return this.users;
    }

    public User getUserByName(String name) {
        return users.get(name);
    }

    public boolean isUserExists(String name) {
        return getUserByName(name) != null;
    }

    private void isStockExists(Holdings currUserHoldings, Stocks stocks) {
        currUserHoldings.getItems().forEach((String symbol, Item item) -> {
            if (stocks.getStocks().get(symbol) == null) {
                throw new NotFoundError(symbol);
            }
        });
    }

    public void updateAllUsersTotalUsers() {
        this.users.forEach((String name, User user) -> {
            user.getHoldings().updateTotalStocksValue();
        });
    }
}
