package engine.dto;

import engine.stockMarket.users.Movement;

public class DTOMovment {
    private final Movement.MovementType movementType;
    private final String date;
    private final String symbol;
    private final int amount;
    private final int prevBalance;
    private final int afterBalance;

    public DTOMovment(Movement movement) {
        this.movementType = movement.getMovementType();
        this.date = movement.getDate();
        this.symbol = movement.getSymbol();
        this.amount = movement.getAmount();
        this.prevBalance = movement.getPrevBalance();
        this.afterBalance = movement.getAfterBalance();
    }

    public Movement.MovementType getMovementType() {
        return movementType;
    }

    public String getDate() {
        return date;
    }

    public String getSymbol() {
        return symbol;
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
