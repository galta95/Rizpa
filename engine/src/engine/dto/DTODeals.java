package engine.dto;

import engine.transaction.Deal;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DTODeals implements Iterable<DTODeal> {
    private final List<DTODeal> deals;

    public DTODeals(List<Deal> deals) {
        this.deals = new LinkedList<>();
        for (Deal deal : deals) {
            this.deals.add(new DTODeal(deal.getDealValue(), deal.getDate(), deal.getNumOfShares(),
                    deal.getPrice(), deal.getOrderType(), deal.getConsumer().getName(), deal.getProducer().getName(),
                    deal.getSymbol()));
        }
    }

    public List<DTODeal> getDeals() {
        return deals;
    }

    @Override
    public Iterator<DTODeal> iterator() {
        return deals.iterator();
    }
}
