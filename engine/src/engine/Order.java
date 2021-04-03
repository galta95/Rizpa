package engine;

import java.util.Date;

public class Order {
    public static String executeStockExchangeOrder(RSE rse, String symbol, String direction, String type,
                                                   Date date, int numOfShares, int price) {
        String res = null;
        Trade trade = new Trade(date, numOfShares, price);

        if (type.equals("LMT") || type.equals("MKT")) {
            if (direction.equals("buy"))
                res = LMTorMKTBuy(rse, symbol, trade);
            else if (direction.equals("sell"))
                res = LMTorMKTSell(rse, symbol, trade);
        } else
            res = "ORDER FAILED";

        return res;
    }

    private static String LMTorMKTBuy(RSE rse, String symbol, Trade trade) {
        String res;
        int boughtNumOfShare = trade.getNumOfShares();

        trade = rse.buyTrade(trade, symbol);
        res = "Buy was executed successfully. \n" +
                "Bought - " + (boughtNumOfShare - trade.getNumOfShares()) + "\n";

        if (trade.getNumOfShares() > 0) {
            boughtNumOfShare = trade.getNumOfShares();
            rse.addToBuyList(trade, symbol);
            res += "Insert to buy list - " + boughtNumOfShare + "\n";
        }

        return res;
    }

    private static String LMTorMKTSell(RSE rse, String symbol, Trade trade) {
        String res;
        int sellNumOfShare = trade.getNumOfShares();

        trade = rse.sellTrade(trade, symbol);
        res = "Sell was executed successfully. \n" +
                "Sold - " + (sellNumOfShare - trade.getNumOfShares()) + "\n";

        if (trade.getNumOfShares() > 0) {
            sellNumOfShare = trade.getNumOfShares();
            rse.addToSellList(trade, symbol);
            res += "Insert to sell list - " + sellNumOfShare + "\n";
        }

        return res;
    }
}
