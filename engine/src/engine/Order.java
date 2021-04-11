package engine;

import engine.stockMarket.RSE;
import engine.transaction.Trade;

public class Order {
    public static String executeStockExchangeOrder(RSE rse, String symbol, String direction, String type,
                                                   String date, int numOfShares, int price) throws Exception {
        String res = null;
        Trade trade = new Trade(date, numOfShares, price);

        if (type.equals("LMT") || type.equals("MKT")) {
            if (direction.equals("BUY"))
                res = LMTorMKTBuy(rse, symbol, trade);
            else if (direction.equals("SELL"))
                res = LMTorMKTSell(rse, symbol, trade);
        } else
            res = "ORDER FAILED";

        return res;
    }

    private static String LMTorMKTBuy(RSE rse, String symbol, Trade trade) throws Exception {
        String res;
        int boughtNumOfShare = trade.getNumOfShares();
        int dealsCounter = 1;
        res = "Order was executed successfully. \n";

        trade = rse.buyTrade(trade, symbol);

        if (boughtNumOfShare != trade.getNumOfShares()) {
            res += "Bought #" + dealsCounter + ": " + (boughtNumOfShare - trade.getNumOfShares()) + "\n";
            dealsCounter++;

            while (boughtNumOfShare != trade.getNumOfShares()) {
                boughtNumOfShare = trade.getNumOfShares();
                trade = rse.buyTrade(trade, symbol);
                if (trade.getNumOfShares() != boughtNumOfShare) {
                    res += "Bought #" + dealsCounter + ": " + (boughtNumOfShare - trade.getNumOfShares()) + "\n";
                    dealsCounter++;
                }
            }
        } else {
            res += "Bought - 0\n";
        }

        if (trade.getNumOfShares() > 0) {
            boughtNumOfShare = trade.getNumOfShares();
            rse.addToBuyList(trade, symbol);
            res += "Insert to buy list - " + boughtNumOfShare + "\n";
        }

        return res;
    }

    private static String LMTorMKTSell(RSE rse, String symbol, Trade trade) throws Exception {
        String res;
        int sellNumOfShare = trade.getNumOfShares();
        int dealsCounter = 1;
        res = "Order was executed successfully. \n";

        trade = rse.sellTrade(trade, symbol);

        if (sellNumOfShare != trade.getNumOfShares()) {
            res += "Sold #" + dealsCounter + ": " + (sellNumOfShare - trade.getNumOfShares()) + "\n";
            dealsCounter++;

            while (sellNumOfShare != trade.getNumOfShares()) {
                sellNumOfShare = trade.getNumOfShares();
                trade = rse.sellTrade(trade, symbol);
                if (trade.getNumOfShares() != sellNumOfShare) {
                    res += "Sold #" + dealsCounter + ": " + (sellNumOfShare - trade.getNumOfShares()) + "\n";
                    dealsCounter++;
                }
            }
        } else {
            res += "Bought - 0\n";
        }

        if (trade.getNumOfShares() > 0) {
            sellNumOfShare = trade.getNumOfShares();
            rse.addToSellList(trade, symbol);
            res += "Insert to sell list - " + sellNumOfShare + "\n";
        }

        return res;
    }
}
