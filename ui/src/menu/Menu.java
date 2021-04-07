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
        switch (choice) {
            case 1:
                rse = MenuOption.readXML();
                break;
            case 2:
                MenuOption.showAllStocks(rse.getStocks());
                break;
            case 3:
                MenuOption.showStockDetails(rse.getStocks());
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
        }
    }
}
