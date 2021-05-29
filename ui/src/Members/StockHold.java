package Members;


public class StockHold {

    private String stock;
    private int quantity;

    public StockHold(String stock, int quantity) {
        this.stock = stock;
        this.quantity = quantity;
    }

    public String getStock() {
        return stock;
    }

    public int getQuantity() {
        return quantity;
    }
}
