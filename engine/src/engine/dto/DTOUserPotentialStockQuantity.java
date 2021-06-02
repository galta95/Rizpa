package engine.dto;

public class DTOUserPotentialStockQuantity {
    private final int potentialQuantity;

    public DTOUserPotentialStockQuantity(int potentialQauntity) {
        this.potentialQuantity = potentialQauntity;
    }

    public int getPotentialQuantity() {
        return potentialQuantity;
    }
}
