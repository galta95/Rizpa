package menu;

import engine.*;

import java.util.*;

public class Menu {
    public static void main(String[] args) {
        RSE rse;
        rse = Handler.startApp("engine/src/resources/ex1-small.xml");
        try {
            choiceThree(rse.getStocks(), "g");
        } catch (Error e){
            if (e.getMessage().equals("No such stock")) {
                System.out.println("No such stock");
            } else if (e.getMessage().equals("No deals")) {
                System.out.println("No deals");
            }
        }
    }

    public static void choiceTwo(Stocks stocks) {
        Map<String, Stock> hashStocks = stocks.getStocks();
        System.out.println("Stocks list: ");

        hashStocks.forEach((symbol, stock) -> {
            printStockDetails(stock);
            System.out.println();
        });
    }

    public static void choiceThree(Stocks stocks, String stockName) {
        String stockSymbol = stockName.toUpperCase();
        Stock stock = stocks.getStocks().get(stockSymbol);
        if (stock == null) {
            throw new Error("No such stock");
        }
        printStockDetails(stock);
        printStockDeals(stock);
    }

    public static void printStockDetails(Stock stock) {
        System.out.println(
                "\t" + "Stock symbol: " + stock.getSymbol() + "\n" +
                "\t" + "Stock company name: " + stock.getCompanyName() + "\n" +
                "\t" + "Current price: " + stock.getPrice() + "\n" +
                "\t" + "Deals until now: " + stock.getDealsCount() + "\n" +
                "\t" + "Stock cycle: " + stock.getCycle()
        );
    }

    public static void printStockDeals(Stock stock) {
        List<Deal> deals = stock.getDeals();
        if (deals == null) {
            throw new Error("No deals");
        }
        for (Deal deal : deals) {
                System.out.println(
                    "\t" + "Date: " + deal.getDate() +
                    "\t" + "Number of shares: " + deal.getNumOfShares() +
                    "\t" + "Sold price: " + deal.getSoldPrice() +
                    "\t" + "Deal value: " + deal.getDealValue() + "\n"
            );
        }
    }
}
