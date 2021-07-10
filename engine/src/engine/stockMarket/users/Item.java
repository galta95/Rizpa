package engine.stockMarket.users;

import errors.InvalidData;
import errors.RangeError;

public class Item {
    private final String symbol;
    private int quantity;
    private int potentialQuantity;

    public Item(String symbol, int quantity) {
        this.symbol = symbol.toUpperCase();
        this.validateQuantity(quantity);
        this.quantity = quantity;
        this.potentialQuantity = quantity;
    }

/*    public Item(RseItem rseItem) {
        this.symbol = rseItem.getSymbol().toUpperCase();
        this.validateQuantity(rseItem.getQuantity());
        this.quantity = rseItem.getQuantity();
        this.potentialQuantity = quantity;
    }*/

    private void validateQuantity(int quantity) throws InvalidData {
        if (quantity <= 0) {
            throw new InvalidData(quantity + " Not positive");
        }
    }

    public String getSymbol() {
        return symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public void updatePotentialQuantity(int num) {
        if (potentialQuantity - num < 0) {
            throw new RangeError("" + potentialQuantity);
        }
        potentialQuantity -= num;
    }

    public void updateQuantity(int numOfShares) {
        if (potentialQuantity == quantity) {
            quantity += numOfShares;
            potentialQuantity = quantity;
        } else {
            quantity -= numOfShares;
        }
    }

    public int getPotentialQuantity() {
        return potentialQuantity;
    }
}
