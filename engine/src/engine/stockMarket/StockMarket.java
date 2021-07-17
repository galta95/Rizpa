package engine.stockMarket;

import dataManager.SchemaBasedJAXB;
import dataManager.generated.*;
import engine.dto.*;
import engine.stockMarket.stocks.Stock;
import engine.stockMarket.users.Movement;
import engine.stockMarket.users.User.Permissions;
import engine.stockMarket.stocks.Stocks;
import engine.stockMarket.users.User;
import engine.stockMarket.users.Users;
import errors.ConstraintError;
import errors.NotFoundError;
import engine.transaction.Trade;
import errors.NotUpperCaseError;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class StockMarket implements StockMarketApi {
    private final Stocks stocks;
    private final Users users;

    public StockMarket() {
        stocks = new Stocks();
        users = new Users();
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

    private void validateLoad(RseHoldings rseHoldings, RseStocks rseStocks) {
        List<RseItem> rseItemsList = rseHoldings.getRseItem();
        List<RseStock> rseStocksList = rseStocks.getRseStock();

        AtomicBoolean flag = new AtomicBoolean(false);
        rseItemsList.forEach(rseItem -> {
            flag.set(false);
            rseStocksList.forEach(rseStock -> {
                if (rseItem.getSymbol().equals(rseStock.getRseSymbol())) {
                    flag.set(true);
                }
            });
            if (!flag.get()) {
                throw new NotFoundError(rseItem.getSymbol());
            }
        });
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
        } catch (Exception error) {
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
    public DTOOrder executeLmtOrderBuy(String symbol, String date, int numOfShares,
                                       int price, String userName) {
        List<Integer> deals = new LinkedList<>();
        User preformUser = users.getUserByName(userName);
        Trade trade = new Trade(date, numOfShares, price, Trade.OrderType.LMT, preformUser);

        int numberOfSharesInsertedToList = 0;
        int boughtNumOfShare = trade.getNumOfShares();
        int dealsCounter = 0;

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

//        this.users.updateAllUsersTotalUsers();
        return new DTOOrder(true, deals, numberOfSharesInsertedToList, dealsCounter);
    }

    @Override
    public DTOOrder executeLmtOrderSell(String symbol, String date, int numOfShares, int price,
                                        String userName) {
        List<Integer> deals = new LinkedList<>();
        User preformUser = users.getUserByName(userName);
        Trade trade = new Trade(date, numOfShares, price, Trade.OrderType.LMT, preformUser);
        preformUser.getHoldings().getItemBySymbol(symbol).updatePotentialQuantity(numOfShares);

        int numberOfSharesInsertedToList = 0;
        int sellNumOfShare = trade.getNumOfShares();
        int dealsCounter = 0;

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

//        this.users.updateAllUsersTotalUsers();
        return new DTOOrder(true, deals, numberOfSharesInsertedToList, dealsCounter);
    }

    @Override
    public DTOOrder executeMktOrderBuy(String symbol, String date, int numOfShares, int price, String userName) {
        List<Integer> deals = new LinkedList<>();
        User preformUser = users.getUserByName(userName);
        Trade trade = new Trade(date, numOfShares, price, Trade.OrderType.MKT, preformUser);

        int sellNumOfShare = 0;
        int dealsCounter = 0;
        int numberOfSharesInsertedToList = 0;

        while (trade.getNumOfShares() > 0) {
            if (this.isStockSellListEmpty(symbol)) {
                trade.setPrice(this.getStockPrice(symbol));
                numberOfSharesInsertedToList = trade.getNumOfShares();
                this.addToBuyList(trade, symbol);
                break;
            } else {
                sellNumOfShare = trade.getNumOfShares();

                trade.setPrice(this.getStockSellPrice(symbol));
                trade = this.buyTrade(trade, symbol);
                dealsCounter++;
                deals.add(sellNumOfShare - trade.getNumOfShares());
            }
        }

//        this.users.updateAllUsersTotalUsers();
        return new DTOOrder(true, deals, numberOfSharesInsertedToList, dealsCounter);
    }

    @Override
    public DTOOrder executeMktOrderSell(String symbol, String date, int numOfShares, int price,
                                        String userName) {
        int sellNumOfShare;
        int dealsCounter = 0;
        List<Integer> deals = new LinkedList<>();
        User preformUser = users.getUserByName(userName);
        int numberOfSharesInsertedToList = 0;
        Trade trade = new Trade(date, numOfShares, price, Trade.OrderType.MKT, preformUser);
        preformUser.getHoldings().getItemBySymbol(symbol).updatePotentialQuantity(numOfShares);

        while (trade.getNumOfShares() > 0) {
            if (this.isStockBuyListEmpty(symbol)) {
                trade.setPrice(this.getStockPrice(symbol));
                numberOfSharesInsertedToList = trade.getNumOfShares();
                this.addToSellList(trade, symbol);
                break;
            } else {
                sellNumOfShare = trade.getNumOfShares();

                trade.setPrice(this.getStockBuyPrice(symbol));
                trade = this.sellTrade(trade, symbol);
                dealsCounter++;
                deals.add(sellNumOfShare - trade.getNumOfShares());
            }
        }

//        this.users.updateAllUsersTotalUsers();
        return new DTOOrder(true, deals, numberOfSharesInsertedToList, dealsCounter);
    }

    @Override
    public DTOOrder executeFokOrderSell(String symbol, String date, int numOfShares, int price, String userName) {
        int possibleShares = numOfShares;
        Stock stock = stocks.getStockBySymbol(symbol.toUpperCase());
        List<Trade> buyList = stock.getBuys();

        for (Trade trade : buyList) {
            if (trade.getPrice() < price) {
                return null;
            }
            possibleShares -= trade.getNumOfShares();
            if (possibleShares <= 0) {
                break;
            }
        }

        if (possibleShares > 0) {
            return null;
        }

        List<Integer> deals = new LinkedList<>();
        User preformUser = users.getUserByName(userName);
        Trade trade = new Trade(date, numOfShares, price, Trade.OrderType.FOK, preformUser);
        preformUser.getHoldings().getItemBySymbol(symbol).updatePotentialQuantity(numOfShares);

        int numberOfSharesInsertedToList = 0;
        int sellNumOfShare = trade.getNumOfShares();
        int dealsCounter = 0;

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

//        this.users.updateAllUsersTotalUsers();
        return new DTOOrder(true, deals, numberOfSharesInsertedToList, dealsCounter);
    }

    @Override
    public DTOOrder executeFokOrderBuy(String symbol, String date, int numOfShares, int price, String userName) {
        int possibleShares = numOfShares;
        Stock stock = stocks.getStockBySymbol(symbol.toUpperCase());
        List<Trade> sellList = stock.getSells();

        for (Trade trade : sellList) {
            if (trade.getPrice() > price) {
                return null;
            }
            possibleShares -= trade.getNumOfShares();
            if (possibleShares <= 0) {
                break;
            }
        }

        if (possibleShares > 0) {
            return null;
        }

        List<Integer> deals = new LinkedList<>();
        User preformUser = users.getUserByName(userName);
        Trade trade = new Trade(date, numOfShares, price, Trade.OrderType.FOK, preformUser);

        int numberOfSharesInsertedToList = 0;
        int boughtNumOfShare = trade.getNumOfShares();
        int dealsCounter = 0;

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

//        this.users.updateAllUsersTotalUsers();
        return new DTOOrder(true, deals, numberOfSharesInsertedToList, dealsCounter);
    }

    @Override
    public DTOOrder executeIocOrderSell(String symbol, String date, int numOfShares, int price, String userName) {
        List<Integer> deals = new LinkedList<>();
        User preformUser = users.getUserByName(userName);
        Trade trade = new Trade(date, numOfShares, price, Trade.OrderType.IOC, preformUser);
        preformUser.getHoldings().getItemBySymbol(symbol).updatePotentialQuantity(numOfShares);

        int numberOfSharesInsertedToList = 0;
        int sellNumOfShare = trade.getNumOfShares();
        int dealsCounter = 0;

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

//        this.users.updateAllUsersTotalUsers();
        return new DTOOrder(true, deals, numberOfSharesInsertedToList, dealsCounter);
    }

    @Override
    public DTOOrder executeIocOrderBuy(String symbol, String date, int numOfShares, int price, String userName) {
        List<Integer> deals = new LinkedList<>();
        User preformUser = users.getUserByName(userName);
        Trade trade = new Trade(date, numOfShares, price, Trade.OrderType.IOC, preformUser);

        int numberOfSharesInsertedToList = 0;
        int boughtNumOfShare = trade.getNumOfShares();
        int dealsCounter = 0;

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
//        this.users.updateAllUsersTotalUsers();
        return new DTOOrder(true, deals, numberOfSharesInsertedToList, dealsCounter);
    }

    @Override
    public DTOUsers getAllUsers() {
        return new DTOUsers(this.users.getUsers());
    }

    @Override
    public DTOUser getUserByName(String name) {
        User user = this.users.getUserByName(name.toUpperCase());
        if (user == null) {
            return null;
        }
        return new DTOUser(user);
    }

    @Override
    public DTOUserPotentialStockQuantity getUserStockPotentialQuantity(String userName, String symbol) {
        User user = this.users.getUserByName(userName);
        return new DTOUserPotentialStockQuantity(user.getHoldings().getItems().get(symbol).getPotentialQuantity());
    }

    @Override
    public DTOUser insertUser(String name, String password, Permissions permission) {
        User newUser = new User(name, password, permission);
        try {
            this.users.addUser(newUser);
        } catch (ConstraintError e) {
            return null;
        }
        return new DTOUser(newUser);
    }

    @Override
    public DTOUser loadXml(String userName, String path) {
        try {
            RizpaStockExchangeDescriptor rsed = SchemaBasedJAXB.loadXml(path);
            validateLoad(rsed.getRseHoldings(), rsed.getRseStocks());
            User user = this.users.getUserByName(userName);

            this.stocks.addStocksFromXml(rsed.getRseStocks());
            user.addHoldingsFromXml(rsed.getRseHoldings(), this.stocks);
            return new DTOUser(user);
        } catch (JAXBException | FileNotFoundException | NotUpperCaseError | NotFoundError error) {
            return null; //TODO: add errors return
        }
    }

    @Override
    public DTOStock insertStock(String companyName, String symbol, int numOfShares, int companyValue, String username) {
        int price = companyValue / numOfShares;
        Stock newStock;
        try {
            newStock = new Stock(symbol, companyName, price);
            this.stocks.addStock(newStock);
            this.users.getUserByName(username.toUpperCase()).updateHoldings(symbol, numOfShares);
        } catch (ConstraintError e) {
            return null;
        }
        return new DTOStock(newStock);
    }

    @Override
    public DTOUser addMoney(String userName, int money) {
        if (!this.users.isUserExists(userName)) {
            return null;
        }
        User user = this.users.getUserByName(userName);
        user.addMoney(money, Movement.MovementType.DEPOSIT, null);
        return new DTOUser(user);
    }

    @Override
    public DTOUser resetDealAlert(String userName) {
        if (!this.users.isUserExists(userName)) {
            return null;
        }
        User user = this.users.getUserByName(userName);
        user.resetDealAlert();

        return new DTOUser(user);
    }
}
