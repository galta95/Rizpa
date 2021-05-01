package engine.stockMarket.users;

import dataManager.generated.RseUser;
import dataManager.generated.RseUsers;
import engine.stockMarket.stocks.Stocks;
import errors.ConstraintError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Users {
    private final Map<String, User> users;

    public Users(RseUsers rseUsers, Stocks stocks) {
        List<RseUser> rseUser = rseUsers.getRseUser();
        this.users = new HashMap<>();

        for (RseUser user : rseUser) {
            User currUser = new User(user, stocks);
            String name = currUser.getName();
            this.isUserNameExits(name);
            this.users.put(name, currUser);
        }
    }

    private void isUserNameExits(String name) throws ConstraintError {
        User user = this.users.get(name);
        if (user != null) {
            throw new ConstraintError(name);
        }
    }
}
