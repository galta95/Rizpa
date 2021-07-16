package engine.dto;

import engine.stockMarket.stocks.Stock;
import engine.transaction.Trade;

import java.util.LinkedList;
import java.util.List;

public class DTOStock {
    private final String companyName;
    private final String symbol;
    private final int price;
    private final int dealsCount;
    private final int cycle;
    private List<DTOTrade> sells;
    private List<DTOTrade> buys;

    public DTOStock(Stock stock) {
        this.companyName = stock.getCompanyName();
        this.symbol = stock.getSymbol();
        this.price = stock.getPrice();
        this.dealsCount = stock.getDealsCount();
        this.cycle = stock.getCycle();
        initLists(stock.getSells(), stock.getBuys());
    }

    public int getCycle() {
        return cycle;
    }

    public int getDealsCount() {
        return dealsCount;
    }

    public int getPrice() {
        return price;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getSymbol() {
        return symbol;
    }

    private void initLists(List<Trade> sells,List<Trade> buys) {
        this.sells = new LinkedList<>();
        this.buys = new LinkedList<>();

        sells.forEach((trade) -> {
            DTOTrade dtoTrade = new DTOTrade(trade);
            this.sells.add(dtoTrade);
        });

        buys.forEach((trade) -> {
            DTOTrade dtoTrade = new DTOTrade(trade);
            this.buys.add(dtoTrade);
        });
    }
}
