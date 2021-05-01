package engine.dto;

import engine.transaction.Deal;
import engine.transaction.Trade;

import java.util.List;

public class DTOStockSummary {
    private final String symbol;
    private final String companyName;
    private final int sellsCount;
    private final int buysCount;
    private final DTOTrades sellsList;
    private final DTOTrades buysList;
    private final DTODeals dealsList;
    private final int potentialSellsValue;
    private final int potentialBuysValue;
    private final int cycle;

    public DTOStockSummary(String symbol, String companyName, int sellsCount, int buysCount, List<Trade> sellsList,
                           List<Trade> buysList, List<Deal> dealsList, int potentialSellsValue, int potentialBuysValue,
                           int cycle) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.sellsCount = sellsCount;
        this.buysCount = buysCount;
        this.sellsList = new DTOTrades(sellsList);
        this.buysList = new DTOTrades(buysList);
        this.dealsList = new DTODeals(dealsList);
        this.potentialSellsValue = potentialSellsValue;
        this.potentialBuysValue = potentialBuysValue;
        this.cycle = cycle;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getCycle() {
        return cycle;
    }

    public DTODeals getDealsList() {
        return dealsList;
    }

    public int getBuysCount() {
        return buysCount;
    }

    public int getPotentialBuysValue() {
        return potentialBuysValue;
    }

    public int getPotentialSellsValue() {
        return potentialSellsValue;
    }

    public int getSellsCount() {
        return sellsCount;
    }

    public DTOTrades getBuysList() {
        return buysList;
    }

    public DTOTrades getSellsList() {
        return sellsList;
    }
}
