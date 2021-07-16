package engine.stockMarket.users;

public class Movement {
    public enum MovementType {
        BUY, SELL, DEPOSIT
    }

    private final MovementType movementType;
    private final String symbol;
    private final String date;
    private final int amount;
    private final int prevBalance;
    private final int afterBalance;

    public Movement(MovementType movementType, String symbol, String date, int amount, int prevBalance, int afterBalance) {
        this.movementType = movementType;
        this.symbol = symbol;
        this.date = date;
        this.amount = amount;
        this.prevBalance = prevBalance;
        this.afterBalance = afterBalance;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDate() {
        return date;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrevBalance() {
        return prevBalance;
    }

    public int getAfterBalance() {
        return afterBalance;
    }
}
