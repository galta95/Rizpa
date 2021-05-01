package engine.transaction;

public class Trade {
    private String date;
    private int numOfShares;
    private int price;

    public Trade(String date, int numOfShares, int price) {
        this.date = date;
        this.numOfShares = numOfShares;
        this.price = price;
    }

    public String getDate() {
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
}
