package menu;

import Errors.RangeError;
import engine.stockMarket.RSE;

import java.util.*;

public class Menu {
    public static void main(String[] args) {
        int choice;
        RSE rse = null;
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
                        rse = MenuOption.readXML();
                        break;
                    case 2:
                        MenuOption.showAllStocks(rse);
                        break;
                    case 3:
                        MenuOption.showStockDetails(rse);
                        break;
                    case 4:
                        MenuOption.executeOrder(rse);
                        break;
                    case 5:
                        MenuOption.showStocksSummary(rse);
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
