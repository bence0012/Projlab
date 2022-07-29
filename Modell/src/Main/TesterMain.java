package Main;

import Tester.Tester;

import java.util.Scanner;

public class TesterMain {
    public static void main(String[] args) {
        System.out.println("Welcome to the prototype tester, write your commands here:");
        Tester tester = new Tester();
        Scanner sc = new Scanner(System.in);
        if (args.length == 1) {
            tester.RunCommand("runTest " + args[0]);
        }
        System.out.print(">>> ");
        while (sc.hasNextLine()) {
            String command = sc.nextLine().strip();
            tester.RunCommand(command);
            System.out.print(">>> ");
        }
    }
}