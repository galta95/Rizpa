package engine.stockMarket;

import engine.dto.*;
import engine.stockMarket.users.User.Permissions;

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

        DTOOrder executeFokOrderSell(String symbol, String date, int numOfShares, int price, String userName);
        DTOOrder executeFokOrderBuy(String symbol, String date, int numOfShares, int price, String userName);
        DTOOrder executeIocOrderSell(String symbol, String date, int numOfShares, int price, String userName);
        DTOOrder executeIocOrderBuy(String symbol, String date, int numOfShares, int price, String userName);

        DTOUsers getAllUsers();
        DTOUser getUserByName(String name);
        DTOUserPotentialStockQuantity getUserStockPotentialQuantity(String userName, String symbol);
        DTOUser insertUser(String name, String password, Permissions permission);
        DTOUser loadXml(String userName, String path);
        DTOStock insertStock(String companyName, String symbol, int numOfShares, int companyValue);
        DTOUser addMoney(String userName, int money);
}

