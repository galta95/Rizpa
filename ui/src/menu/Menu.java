package menu;

import engine.Stack;

import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        System.out.println("Choose number");

        Scanner s = new Scanner(System.in);
        int num = s.nextInt();

        Stack.some(num);
    }
}
