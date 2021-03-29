package menu;

import engine.Handler;
import engine.RSE;

import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {

    }

    public static RSE optionOne() {
        String xmlPath;

        System.out.println("Enter xml full path: ");
        Scanner scan = new Scanner(System.in);
        xmlPath = scan.nextLine();

        return Handler.startApp(xmlPath);
    }
}
