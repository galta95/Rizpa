package engine;

import java.util.Date;

public class Trade {
    protected Date date;
    protected int numOfShares;
    protected int price;

    public Trade(Date date, int numOfShares, int price) {
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

    public void setNumOfShares(int numOfShares) {
        this.numOfShares = numOfShares;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String toString() {
        return "Date: " + this.date +
                "Number of shares: " + this.numOfShares +
                "price: " + this.price;
    }
}
