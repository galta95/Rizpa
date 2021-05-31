package engine.dto;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DTOStocksSummary implements Iterable<DTOStockSummary> {
    List<DTOStockSummary> stocksSummaryList;

    public DTOStocksSummary(List<DTOStockSummary> dtoStockSummaryList) {
        this.stocksSummaryList = new LinkedList<>();
        this.stocksSummaryList.addAll(dtoStockSummaryList);
    }

    public List<DTOStockSummary> getStocksSummaryList() {
        return stocksSummaryList;
    }

    @Override
    public Iterator<DTOStockSummary> iterator() {
        return stocksSummaryList.iterator();
    }
}
