package engine.dto;

import engine.stockMarket.users.User;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DTOUsers implements Iterable<DTOUser> {
    private final List<DTOUser> users;

    public DTOUsers(Map<String, User> users) {
        this.users = new LinkedList<>();
        users.forEach((userName, user) -> {
            this.users.add(new DTOUser(user));
        });
    }

    public List<DTOUser> getUsers() {
        return users;
    }

    @Override
    public Iterator<DTOUser> iterator() {
        return users.iterator();
    }
}