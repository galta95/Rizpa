package menu;

import Errors.DataNotLoadedError;
import Errors.RangeError;
import engine.*;
import engine.stockMarket.RSE;
import engine.stockMarket.Stock;
import engine.transaction.Deal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MenuOption {
    public static void showMenu() {
        System.out.println(
                "CHOOSE ONE OF THE FOLLOW: \n" +
                        "\t 1 - Read XML file \n" +
                        "\t 2 - Show all stocks \n" +
                        "\t 3 - Show stock details \n" +
                        "\t 4 - Execute order \n" +
                        "\t 5 - Show stocks summary \n" +
                        "\t 6 - Exit system \n" +
                        "PLEASE SELECT: "
        );
    }

    public static RSE readXML() throws Exception {
        String xmlPath;

        System.out.println("Enter xml full path: ");
        Scanner scan = new Scanner(System.in);
        xmlPath = scan.nextLine();

        try {
            RSE rse = Handler.startApp(xmlPath);
            System.out.println("XML loaded!");
            return rse;
        } catch (Exception e) {
            throw e;
        }
    }

    public static void showAllStocks(RSE rse) throws DataNotLoadedError {
        if (rse == null) {
            throw new DataNotLoadedError();
        }
        Map<String, Stock> hashStocks = rse.getStocks().getStocks(); //TODO: make better
        System.out.println("Stocks list: \n");

        hashStocks.forEach((symbol, stock) -> {
            System.out.println(stock);
            System.out.println();
        });
    }

    public static void showStockDetails(RSE rse) throws DataNotLoadedError, Exception {
        if (rse == null) {
            throw new DataNotLoadedError();
        }

        Scanner scan = new Scanner(System.in);
        String stockName;

        System.out.println("Please enter stock symbol/company name: ");
        stockName = scan.nextLine();
        stockName = stockName.toUpperCase();
        Stock stock = rse.getStocks().getStockByName(stockName);
        System.out.println(stock);
    }

    public static void executeOrder(RSE rse) throws DataNotLoadedError, Exception {
        Scanner scan = new Scanner(System.in);
        String date = DateTimeFormatter.ofPattern("HH:mm:ss:SSS").format(LocalDateTime.now());
        String orderName, operation, res;
        int price;

        if (rse == null) {
            throw new DataNotLoadedError();
        }

        try {
            System.out.println(
                    "CHOOSE ONE OF THE FOLLOW: \n" +
                            "\t 1 - LMT \n" +
                            "\t 2 - MKT \n" +
                            "PLEASE SELECT: "
            );

            int orderNumber = scan.nextInt();
            if (orderNumber == 1) {
                orderName = "LMT";
            } else if (orderNumber == 2) {
                orderName = "MKT";
            } else {
                throw new RangeError("1-2");
            }
            scan.nextLine();

            System.out.println("Please enter stock symbol/company name: ");
            String symbolName = scan.nextLine().toUpperCase();
            rse.assertStockExists(symbolName);

            System.out.println(
                    "CHOOSE ONE OF THE FOLLOW: \n" +
                            "\t 1 - BUY \n" +
                            "\t 2 - SELL \n" +
                            "PLEASE SELECT: "
            );

            int operationNumber = scan.nextInt();
            if (operationNumber == 1) {
                operation = "BUY";
                System.out.println("Please enter number of shares you want to buy: ");
            } else if (operationNumber == 2) {
                operation = "SELL";
                System.out.println("Please enter number of shares you want to sell: ");
            } else {
                throw new RangeError("1-2");
            }

            int numOfShares = scan.nextInt();
            if (numOfShares <= 0) {
                throw new InputMismatchException("Not a positive number (InputMismatchException)");
            }

            if (orderName.equals("LMT")) {
                if (operation.equals("BUY")) {
                    System.out.println("Please enter the price you want to buy the stock: ");
                } else {
                    System.out.println("Please enter the price you want to sell the stock: ");
                }
                price = scan.nextInt();

                if (price <= 0) {
                    throw new InputMismatchException("Not a positive number (InputMismatchException)");
                }
            } else { // MKT
                price = 0;
            }

            res = Order.executeStockExchangeOrder(rse, symbolName, operation, orderName, date, numOfShares, price);
            System.out.println(res);
        } catch (Exception e) {
            if (e.getMessage() != null) {
                throw e;
            }
            throw new Exception("General error (Exception)");
        }
    }

    public static void showStocksSummary(RSE rse) throws DataNotLoadedError {
        if (rse == null) {
            throw new DataNotLoadedError();
        }
        System.out.println(rse.getStocks().stocksSummary());
    }

    public static void exitSystem() {
        System.out.println("Thank you for using our system! \n" +
                "BYE BYE");
        System.exit(0);
    }
}
