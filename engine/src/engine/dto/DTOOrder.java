package engine.dto;

import java.util.LinkedList;
import java.util.List;

public class DTOOrder {
    private final Boolean successfulOrder;
    private final List<Integer> deals = new LinkedList<>();
    private final int numberOfSharesInsertedToList;
    private final int dealsCounter;

    public DTOOrder(Boolean successfulOrder, List<Integer> deals, int numberOfSharesInsertedToList, int dealsCounter) {
        this.successfulOrder = successfulOrder;
        this.numberOfSharesInsertedToList = numberOfSharesInsertedToList;
        this.dealsCounter = dealsCounter;
        this.deals.addAll(deals);
    }

    public Boolean getSuccessfulOrder() {
        return successfulOrder;
    }

    public int getDealsCounter() {
        return dealsCounter;
    }

    public int getNumberOfSharesInsertedToList() {
        return numberOfSharesInsertedToList;
    }

    public List<Integer> getDeals() {
        return deals;
    }
}
