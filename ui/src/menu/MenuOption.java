package menu;

import engine.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuOption {
    public static void showMenu() {
        System.out.println(
                "CHOOSE ONE OF THE FOLLOW: \n" +
                "\t 1 - read XML file \n" +
                "\t 2 - show all stocks \n" +
                "\t 3 - show stock details \n" +
                "\t 4 - execute command \n" +
                "\t 5 - show commands \n" +
                "\t 6 - exit system \n" +
                "PLEASE SELECT: "
        );
    }

    public static RSE readXML() {
        String xmlPath;

        System.out.println("Enter xml full path: ");
        Scanner scan = new Scanner(System.in);
        xmlPath = scan.nextLine();

        return Handler.startApp(xmlPath);
    }

    public static void showAllStocks(Stocks stocks) {
        Map<String, Stock> hashStocks = stocks.getStocks();
        System.out.println("Stocks list: \n");

        hashStocks.forEach((symbol, stock) -> {
            System.out.println(stock);
            System.out.println();
        });
    }

    public static void showStockDetails(Stocks stocks, String stockName) {
        String stockSymbol = stockName.toUpperCase();
        Stock stock = stocks.getStocks().get(stockSymbol);
        if (stock == null) {
            throw new Error("No such stock");
        }
        System.out.println(stock);
        printStockDeals(stock);
    }

    public static void executeCommand() {

    }

    public static void showCommands() {

    }

    public static void exitSystem() {

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
