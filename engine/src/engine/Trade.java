package engine;

import java.util.Date;

public class Trade extends Barter {
    private Order.Direction direction;

    public Trade(Date date, int numOfShares, int soldPrice, Order.Direction direction) {
        super(date, numOfShares, soldPrice);
        this.direction = direction;
    }

    public Order.Direction getDirection() {
        return direction;
    }

    public String toString() {
        return super.toString() +
                this.direction.toString();
    }
}
