package engine.stockMarket.users;

import dataManager.generated.RseItem;
import errors.InvalidData;

public class Item {
    private final String symbol;
    private int quantity;

    public Item(String symbol, int quantity) {
        this.symbol = symbol.toUpperCase();
        this.validateQuantity(quantity);
        this.quantity = quantity;
    }

    public Item(RseItem rseItem) {
        this.symbol = rseItem.getSymbol().toUpperCase();
        this.validateQuantity(rseItem.getQuantity());
        this.quantity = rseItem.getQuantity();
    }

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
}
