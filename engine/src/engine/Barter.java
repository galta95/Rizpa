package engine;

import java.util.Date;

public class Barter {
    protected Date date;
    protected int numOfShares;
    protected int price;

    public Barter(Date date, int numOfShares, int price) {
        this.date = date;
        this.numOfShares = numOfShares;
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public int getNumOfShares() {
        return numOfShares;
    }

    public int getPrice() {
        return price;
    }

    public String toString() {
        return "Date: " + this.date +
                "Number of shares: " + this.numOfShares +
                "price: " + this.price;
    }
}
