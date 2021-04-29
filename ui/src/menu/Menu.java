package menu;

import errors.RangeError;
import engine.stockMarket.StockMarket;

import java.util.*;

public class Menu {
    public static void main(String[] args) {
        int choice;
        StockMarket stockMarket = null;
        Scanner scan = new Scanner(System.in);

        while (true) {
            MenuOption.showMenu();
            try {
                try {
                    choice = scan.nextInt();
                } catch (Exception e) {
                    throw new IllegalStateException("Not a number (IllegalStateException)");
                }
                switch (choice) {
                    case 1:
                        stockMarket = MenuOption.readXML();
                        break;
                    case 2:
                        MenuOption.showAllStocks(stockMarket);
                        break;
                    case 3:
                        MenuOption.showStockDetails(stockMarket);
                        break;
                    case 4:
                        MenuOption.executeOrder(stockMarket);
                        break;
                    case 5:
                        MenuOption.showStocksSummary(stockMarket);
                        break;
                    case 6:
                        MenuOption.exitSystem();
                        break;
                    default:
                        throw new RangeError("1-6");
                }
            }
            catch (Exception e){
                System.out.println("Error: " + e.getMessage());
                scan.nextLine();
            } finally {
                System.out.println();
            }
        }
    }
}
