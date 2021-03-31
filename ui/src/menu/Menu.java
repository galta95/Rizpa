package menu;

import engine.*;

import java.util.*;

public class Menu {
    public static void main(String[] args) {
        RSE rse;
        rse = Handler.startApp("engine/src/resources/ex1-small.xml");
        choiseTwo(rse.getStocks());
    }

    public static void choiseTwo(Stocks stocks) {
        Map<String, Stock> hashStocks = stocks.getStocks();
        System.out.println("Stocks list: ");

        hashStocks.forEach((symbol, stock) -> {
            printStockDetails(stock);
            System.out.println();
        });
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
