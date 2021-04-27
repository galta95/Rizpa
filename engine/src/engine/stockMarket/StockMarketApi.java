package engine.stockMarket;

import engine.dto.DTODeals;
import engine.dto.DTOOrder;
import engine.dto.DTOStock;
import engine.dto.DTOStocks;

public interface StockMarketApi {
        public DTOStocks getAllStocks ();
        public DTOStock getStockByName(String stockName);
        public DTOStock getStockBySymbol();
        public DTOOrder executeLmtOrderBuy(String symbol, String date, int numOfShares, int price);
        public DTOOrder executeLmtOrderSell(String symbol, String date, int numOfShares, int price);
        public DTOOrder executeMktOrderBuy(String symbol, String date, int numOfShares, int price);
        public DTOOrder executeMktOrderSell(String symbol, String date, int numOfShares, int price);
        public DTODeals getStockDeals(String stockName);
}
