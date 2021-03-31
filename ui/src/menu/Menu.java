package menu;

import engine.*;

import java.util.*;

public class Menu {
    public static void main(String[] args) {
        RSE rse;
        rse = Handler.startApp("engine/src/resources/ex1-small.xml");
        try {
            optionTwo(rse.getStocks());
        } catch (Error e){
            if (e.getMessage().equals("No such stock")) {
                System.out.println("No such stock");
            } else if (e.getMessage().equals("No deals")) {
                System.out.println("No deals");
            }
        }
    }

    public static RSE optionOne() {
        String xmlPath;

        System.out.println("Enter xml full path: ");
        Scanner scan = new Scanner(System.in);
        xmlPath = scan.nextLine();

        return Handler.startApp(xmlPath);
    }

    public static void optionTwo(Stocks stocks) {
        Map<String, Stock> hashStocks = stocks.getStocks();
        System.out.println("Stocks list: \n");

        hashStocks.forEach((symbol, stock) -> {
            System.out.println(stock);
            System.out.println();
        });
    }

    public static void optionThree(Stocks stocks, String stockName) {
        String stockSymbol = stockName.toUpperCase();
        Stock stock = stocks.getStocks().get(stockSymbol);
        if (stock == null) {
            throw new Error("No such stock");
        }
        System.out.println(stock);
        printStockDeals(stock);
    }

    public static void printStockDeals(Stock stock) {
        List<Deal> deals = stock.getDeals();
        if (deals == null) {
            throw new Error("No deals");
        }
        for (Deal deal : deals) {
            System.out.println(deal);
            System.out.println();
        }
    }
}
