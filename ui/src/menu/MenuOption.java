package menu;

import Errors.DataNotLoadedError;
import Errors.RangeError;
import engine.dto.*;
import engine.stockMarket.StockMarket;

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

    public static StockMarket readXML() throws Exception {
        String xmlPath;

        System.out.println("Enter xml full path: ");
        Scanner scan = new Scanner(System.in);
        xmlPath = scan.nextLine();

        try {
            StockMarket stockMarket = new StockMarket(xmlPath);
            System.out.println("XML loaded!");
            return stockMarket;
        } catch (Exception e) {
            throw e;
        }
    }

    public static void showAllStocks(StockMarket stockMarket) throws DataNotLoadedError {
        if (stockMarket == null) {
            throw new DataNotLoadedError();
        }
        DTOStocks stocks = stockMarket.getAllStocks();

        System.out.println("Stocks list: \n");
        for (DTOStock stock: stocks) {
            DTODeals stockDeals = stockMarket.getStockDeals(stock.getCompanyName());
            printStock(stock, stockDeals);
            System.out.println();
        }
    }

    public static void showStockDetails(StockMarket stockMarket) throws DataNotLoadedError, Exception {
        if (stockMarket == null) {
            throw new DataNotLoadedError();
        }

        Scanner scan = new Scanner(System.in);
        String stockName;

        System.out.println("Please enter stock symbol/company name: ");
        stockName = scan.nextLine();
        stockName = stockName.toUpperCase();
        DTOStock stock = stockMarket.getStockByName(stockName);
        DTODeals stockDeals = stockMarket.getStockDeals(stockName);

        printStock(stock, stockDeals);
    }

    public static void printStock(DTOStock stock, DTODeals stockDeals) {
        System.out.println(
                "Stock symbol: " + stock.getSymbol() + "\n" +
                        "Stock company name: " + stock.getCompanyName() + "\n" +
                        "Current price: " + stock.getPrice() + "\n" +
                        "Deals until now: " + stock.getDealsCount() + "\n" +
                        "Stock cycle: " + stock.getCycle() + "\n" +
                        "Deals history: \n"
        );

        if (stock.getDealsCount() == 0) {
            System.out.println("There are no deals yet");
        } else {
            System.out.println("\n");
            for (DTODeal deal : stockDeals) {
                System.out.println(
                        "*****\n" +
                                "Date: " + deal.getDate() + "\n" +
                                "Number of shares: " + deal.getNumOfShares() + "\n" +
                                "price: " + deal.getPrice() +
                                "Deal value: " + deal.getDealValue()
                );
            }
        }
    }

    public static void executeOrder(StockMarket stockMarket) throws DataNotLoadedError, Exception {
        Scanner scan = new Scanner(System.in);
        String date = DateTimeFormatter.ofPattern("HH:mm:ss:SSS").format(LocalDateTime.now());
        String orderName, operation;
        int price;

        DTOOrder dtoOrder;

        if (stockMarket == null) {
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
            //rse.assertStockExists(symbolName);

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

            if (orderName.equals("LMT")) {
                if (operation.equals("BUY"))
                    dtoOrder = stockMarket.executeLmtOrderBuy(symbolName, date, numOfShares, price);
                else if (operation.equals("SELL"))
                    dtoOrder = stockMarket.executeLmtOrderSell(symbolName, date, numOfShares, price);
            } else if (orderName.equals("MKT")) {
                if (operation.equals("BUY"))
                    dtoOrder = stockMarket.executeMktOrderBuy(symbolName, date, numOfShares, price);
                else if (operation.equals("SELL"))
                    dtoOrder = stockMarket.executeMktOrderSell(symbolName, date, numOfShares, price);
            } else {
                System.out.println("ORDER FAILED");
            }
        } catch (Exception e) {
            if (e.getMessage() != null) {
                throw e;
            }
            throw new Exception("General error (Exception)");
        }
    }

    public static void showStocksSummary(StockMarket stockMarket) throws DataNotLoadedError {
        if (stockMarket == null) {
            throw new DataNotLoadedError();
        }
        //System.out.println(rse.getStocks().stocksSummary());
    }

    public static void exitSystem() {
        System.out.println("Thank you for using our system! \n" +
                "BYE BYE");
        System.exit(0);
    }
}
