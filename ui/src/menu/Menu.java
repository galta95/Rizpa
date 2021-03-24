package menu;

import engine.Handler;
import engine.RSE;

import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        RSE rse;
        int userChoise;

        System.out.println("1. Load XML");
        Scanner s = new Scanner(System.in);
        userChoise = s.nextInt();

        rse = Handler.startApp("engine/src/resources/ex1-small.xml");
        System.out.println("Bye");
    }
}
