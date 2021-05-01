package engine.stockMarket;

import engine.dto.*;

public interface StockMarketApi {
        DTOStocks getAllStocks();
        DTOStock getStockByName(String stockName);
        DTOStock getStockBySymbol(String symbol);
        DTODeals getStockDeals(String stockName);
        DTOStockSummary getStockSummary(String symbol);
        DTOStocksSummary getStocksSummary();
        DTOOrder executeLmtOrderBuy(String symbol, String date, int numOfShares, int price);
        DTOOrder executeLmtOrderSell(String symbol, String date, int numOfShares, int price);
        DTOOrder executeMktOrderBuy(String symbol, String date, int numOfShares, int price);
        DTOOrder executeMktOrderSell(String symbol, String date, int numOfShares, int price);
}
