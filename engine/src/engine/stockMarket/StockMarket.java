package engine.stockMarket;

import dataManager.generated.RizpaStockExchangeDescriptor;
import engine.dto.*;
import engine.stockMarket.stocks.Stock;
import engine.stockMarket.stocks.Stocks;
import engine.stockMarket.users.User;
import engine.stockMarket.users.Users;
import errors.NotFoundError;
import dataManager.SchemaBasedJAXB;
import engine.transaction.Trade;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StockMarket implements StockMarketApi {
    private final Stocks stocks;
    private final Users users;

    public StockMarket(String path) throws JAXBException, FileNotFoundException {
        RizpaStockExchangeDescriptor rsed = SchemaBasedJAXB.loadXml(path);
        this.stocks = new Stocks(rsed.getRseStocks());
        this.users = new Users(rsed.getRseUsers(), this.stocks);
    }

    private void assertStockExists(String name) throws NotFoundError {
        this.stocks.getStockByName(name);
    }

    private Trade buyTrade(Trade trade, String symbol) throws NotFoundError {
        Stock stock = this.stocks.getStockByName(symbol);
        if (trade.getPrice() == 0) { // price stock that is 0, means null
            trade.setPrice(stock.getPrice());
        }
        return stock.buy(trade);
    }

    private Trade sellTrade(Trade trade, String symbol) throws NotFoundError {
        Stock stock = this.stocks.getStockByName(symbol);
        if (trade.getPrice() == 0) // price stock that is 0, means null
            trade.setPrice(stock.getPrice());

        return stock.sell(trade);
    }

    private void addToBuyList(Trade trade, String symbol) throws NotFoundError {
        Stock stock = this.stocks.getStockByName(symbol);
        stock.addBuyToBuysList(trade);
    }

    private void addToSellList(Trade trade, String symbol) throws NotFoundError {
        Stock stock = this.stocks.getStockByName(symbol);
        stock.addSellToSellsList(trade);
    }

    private boolean isStockBuyListEmpty(String symbol) {
        Stock stock = this.stocks.getStockByName(symbol);
        return stock.getBuys().isEmpty();
    }

    private boolean isStockSellListEmpty(String symbol) {
        Stock stock = this.stocks.getStockByName(symbol);
        return stock.getSells().isEmpty();
    }

    private int getStockPrice(String symbol) {
        return this.stocks.getStockByName(symbol).getPrice();
    }

    private int getStockBuyPrice(String symbol) {
        return this.stocks.getStockByName(symbol).getBuys().get(0).getPrice();
    }

    private int getStockSellPrice(String symbol) {
        return this.stocks.getStockByName(symbol).getSells().get(0).getPrice();
    }

    //----------------------------API-----------------------------//

    @Override
    public DTOStocks getAllStocks() {
        return new DTOStocks(this.stocks.getStocks());
    }

    @Override
    public DTOStock getStockByName(String stockName) {
        try {
            return new DTOStock(this.stocks.getStockByName(stockName));
        } catch (NotFoundError error) {
            return null;
        }
    }

    @Override
    public DTOStock getStockBySymbol(String symbol) {
        try {
            return new DTOStock(this.stocks.getStockBySymbol(symbol));
        } catch (NotFoundError error) {
            return null;
        }
    }

    @Override
    public DTODeals getStockDeals(String stockName) {
        try {
            Stock stock = this.stocks.getStockByName(stockName);
            return new DTODeals(stock.getDeals());
        } catch (NotFoundError error) {
            return null;
        }
    }

    @Override
    public DTOStockSummary getStockSummary(String symbol) {
        Stock stock = this.stocks.getStockBySymbol(symbol);
        int potentialSellsValue = 0;
        int potentialBuysValue = 0;
        int sellsSize = stock.getSells().size();
        int buysSize = stock.getBuys().size();

        if (sellsSize > 0) {
            for (Trade sell : stock.getSells()) {
                potentialSellsValue += sell.getNumOfShares() * sell.getPrice();
            }
        }

        if (buysSize > 0) {
            for (Trade buy : stock.getBuys()) {
                potentialBuysValue += buy.getNumOfShares() * buy.getPrice();
            }
        }

        return new DTOStockSummary(stock.getSymbol(), stock.getCompanyName(), sellsSize, buysSize, stock.getSells(),
                stock.getBuys(), stock.getDeals(), potentialSellsValue, potentialBuysValue, stock.getCycle());

    }

    @Override
    public DTOStocksSummary getStocksSummary() {
        Map<String, Stock> stocks = this.stocks.getStocks();
        List<DTOStockSummary> stocksSummary = new LinkedList<>();

        for (Map.Entry<String, Stock> stock : stocks.entrySet()) {
            stocksSummary.add(this.getStockSummary(stock.getKey()));
        }

        return new DTOStocksSummary(stocksSummary);
    }

    @Override
    public DTOOrder executeLmtOrderBuy(String symbol, String date, int numOfShares, int price) {
        List<Integer> deals = new LinkedList<>();
        Trade trade = new Trade(date, numOfShares, price, Trade.OrderType.LMT);

        int numberOfSharesInsertedToList = 0;
        int boughtNumOfShare = trade.getNumOfShares();
        int dealsCounter = 1;

        trade = this.buyTrade(trade, symbol);

        if (boughtNumOfShare != trade.getNumOfShares()) {
            dealsCounter++;

            while (boughtNumOfShare != trade.getNumOfShares()) {
                boughtNumOfShare = trade.getNumOfShares();
                trade = this.buyTrade(trade, symbol);
                if (trade.getNumOfShares() != boughtNumOfShare) {
                    dealsCounter++;
                }
            }
        }

        if (trade.getNumOfShares() > 0) {
            numberOfSharesInsertedToList = trade.getNumOfShares();
            this.addToBuyList(trade, symbol);
        }

        return new DTOOrder(true, deals, numberOfSharesInsertedToList, dealsCounter);
    }

    @Override
    public DTOOrder executeLmtOrderSell(String symbol, String date, int numOfShares, int price) {
        List<Integer> deals = new LinkedList<>();
        Trade trade = new Trade(date, numOfShares, price, Trade.OrderType.LMT);

        int numberOfSharesInsertedToList = 0;
        int sellNumOfShare = trade.getNumOfShares();
        int dealsCounter = 1;

        trade = this.sellTrade(trade, symbol);

        if (sellNumOfShare != trade.getNumOfShares()) {
            dealsCounter++;

            while (sellNumOfShare != trade.getNumOfShares()) {
                sellNumOfShare = trade.getNumOfShares();
                trade = this.sellTrade(trade, symbol);
                if (trade.getNumOfShares() != sellNumOfShare) {
                    dealsCounter++;
                }
            }
        }

        if (trade.getNumOfShares() > 0) {
            numberOfSharesInsertedToList = trade.getNumOfShares();
            this.addToSellList(trade, symbol);
        }

        return new DTOOrder(true, deals, numberOfSharesInsertedToList, dealsCounter);
    }

    @Override
    public DTOOrder executeMktOrderBuy(String symbol, String date, int numOfShares, int price) {
        List<Integer> deals = new LinkedList<>();
        Trade trade = new Trade(date, numOfShares, price, Trade.OrderType.MKT);

        int sellNumOfShare = 0;
        int dealsCounter = 0;
        int numberOfSharesInsertedToList = 0;

        while (trade.getNumOfShares() > 0) {
            if (this.isStockSellListEmpty(symbol)) {
                trade.setPrice(this.getStockPrice(symbol));
                numberOfSharesInsertedToList = trade.getNumOfShares();
                this.addToBuyList(trade, symbol);
            } else {
                sellNumOfShare = trade.getNumOfShares();

                trade.setPrice(this.getStockSellPrice(symbol));
                trade = this.buyTrade(trade, symbol);
                dealsCounter++;
                deals.add(sellNumOfShare - trade.getNumOfShares());
            }
        }
        return new DTOOrder(true, deals, numberOfSharesInsertedToList, dealsCounter);
    }

    @Override
    public DTOOrder executeMktOrderSell(String symbol, String date, int numOfShares, int price) {
        int sellNumOfShare;
        int dealsCounter = 0;
        List<Integer> deals = new LinkedList<>();
        int numberOfSharesInsertedToList = 0;
        Trade trade = new Trade(date, numOfShares, price, Trade.OrderType.MKT);

        while (trade.getNumOfShares() > 0) {
            if (this.isStockBuyListEmpty(symbol)) {
                trade.setPrice(this.getStockPrice(symbol));
                numberOfSharesInsertedToList = trade.getNumOfShares();
                this.addToSellList(trade, symbol);
            } else {
                sellNumOfShare = trade.getNumOfShares();

                trade.setPrice(this.getStockBuyPrice(symbol));
                trade = this.sellTrade(trade, symbol);
                dealsCounter++;
                deals.add(sellNumOfShare - trade.getNumOfShares());
            }
        }
        return new DTOOrder(true, deals, numberOfSharesInsertedToList, dealsCounter);
    }

    @Override
    public DTOUsers getAllUsers() {
        return new DTOUsers(this.users.getUsers());
    }
}
