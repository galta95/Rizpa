package menu;

import engine.*;

import java.util.*;


public class Menu {
    private static RSE rse;
    private static int choice;
    private static Scanner s;

    public static void main(String[] args) {
        s = new Scanner(System.in);

        while (true) {
            MenuOption.showMenu();
            // try
            choice = s.nextInt();
            // catch

            menuActivate();
        }
    }

    public static void menuActivate() {
        switch (choice) { // TODO: do it better
            case 1:
                rse = MenuOption.readXML();
                break;
            case 2:
                MenuOption.showAllStocks(rse.getStocks());
                break;
            case 3:
                System.out.println("Please enter stock name: ");
                String stockName = s.nextLine(); // TODO: do it better
                stockName = s.nextLine();
                MenuOption.showStockDetails(rse.getStocks(), stockName);
                break;
            case 4:
                MenuOption.executeOrder(rse);
                break;
            case 5:
                MenuOption.showOrders();
                break;
            case 6:
                MenuOption.exitSystem();
                break;
        }
    }
}
