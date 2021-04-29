package engine.stockMarket;

import engine.dto.DTODeals;
import engine.dto.DTOOrder;
import engine.dto.DTOStock;
import engine.dto.DTOStocks;

public interface StockMarketApi {
        DTOStocks getAllStocks();
        DTOStock getStockByName(String stockName);
        DTOStock getStockBySymbol();
        DTODeals getStockDeals(String stockName);
        DTOOrder executeLmtOrderBuy(String symbol, String date, int numOfShares, int price);
        DTOOrder executeLmtOrderSell(String symbol, String date, int numOfShares, int price);
        DTOOrder executeMktOrderBuy(String symbol, String date, int numOfShares, int price);
        DTOOrder executeMktOrderSell(String symbol, String date, int numOfShares, int price);
}
