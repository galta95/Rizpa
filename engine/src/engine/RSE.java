package engine;

import dataManager.jaxb.generated.RizpaStockExchangeDescriptor;

import java.util.HashMap;
import java.util.Map;

public class RSE {
    private Stocks stocks;

    public RSE(RizpaStockExchangeDescriptor rsed) {
        this.stocks = new Stocks(rsed.getRseStocks());
    }

    public Stocks getStocks() {
        return this.stocks;
    }
}
