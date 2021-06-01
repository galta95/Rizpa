package engine.stockMarket;

import engine.dto.*;

public interface StockMarketApi {
        DTOStocks getAllStocks();
        DTOStock getStockByName(String stockName);
        DTOStock getStockBySymbol(String symbol);
        DTODeals getStockDeals(String stockName);
        DTOStockSummary getStockSummary(String symbol);
        DTOStocksSummary getStocksSummary();
        DTOOrder executeLmtOrderBuy(String symbol, String date, int numOfShares, int price, String userName);
        DTOOrder executeLmtOrderSell(String symbol, String date, int numOfShares, int price, String userName);
        DTOOrder executeMktOrderBuy(String symbol, String date, int numOfShares, int price, String userName);
        DTOOrder executeMktOrderSell(String symbol, String date, int numOfShares, int price, String userName);
        DTOUsers getAllUsers();
}
