package engine.dto;

import java.util.List;

public class DTOStocksSummary {
    List<DTOStockSummary> stocksSummaryList;

    public DTOStocksSummary(List<DTOStockSummary> dtoStockSummaryList) {
        this.stocksSummaryList.addAll(dtoStockSummaryList);
    }
}
