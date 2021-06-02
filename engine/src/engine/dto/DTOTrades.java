package engine.dto;

import engine.transaction.Trade;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DTOTrades implements Iterable<DTOTrade> {
    private final List<DTOTrade> trades;

    public DTOTrades(List<Trade> trades) {
        this.trades = new LinkedList<>();
        for (Trade trade : trades) {
            this.trades.add(new DTOTrade(trade.getDate(), trade.getNumOfShares(),
                    trade.getPrice(), trade.getOrderType(), trade.getUser().getName()));
        }
    }

    public List<DTOTrade> getTrades() {
        return trades;
    }

    @Override
    public Iterator<DTOTrade> iterator() {
        return trades.iterator();
    }
}
