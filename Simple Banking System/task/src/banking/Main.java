package banking;

import banking.model.Card;
import banking.repository.SimpleBankingSystemRepository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SimpleBankingSystemRepository repository = new SimpleBankingSystemRepository();
        repository.createDB(args[1]);
        Scanner numScanner = new Scanner(System.in);
        Scanner strScanner = new Scanner(System.in);
        int chooseOperation1;
        int chooseOperation2;
        while (true) {
            System.out.println("\n1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");
            chooseOperation1 = numScanner.nextInt();
            switch (chooseOperation1) {
                case 1:
                    Card card = new Card();
                    card.generateNumber();
                    card.generatePin();
                    repository.insertCard(card);
                    System.out.println("\nYour card has been created");
                    System.out.println("Your card number:");
                    System.out.println(card.getNumber());
                    System.out.println("Your card PIN:");
                    System.out.println(card.getPin());
                    break;
                case 2:
                    System.out.println("\nEnter your card number:");
                    String number = strScanner.nextLine();
                    System.out.println("Enter your PIN:");
                    String pin = strScanner.nextLine();
                    Card card1 = repository.getCard(number, pin);
                    if (card1 != null) {
                        System.out.println("\nYou have successfully logged in!");
                        while (true) {
                            System.out.println("\n1. Balance");
                            System.out.println("2. Log out");
                            System.out.println("0. Exit");
                            chooseOperation2 = numScanner.nextInt();
                            switch (chooseOperation2) {
                                case 1:
                                    System.out.println("\nBalance: " + card1.getBalance());
                                    break;
                                case 2:
                                    System.out.println("\nYou have successfully logged out!");
                                    break;
                                case 0:
                                    chooseOperation1 = 0;
                                    break;
                                default:
                                    System.out.println("Wrong operation");
                                    break;
                            }
                            if (chooseOperation2 == 2 || chooseOperation2 == 0) {
                                break;
                            }
                        }
                    } else {
                        System.out.println("\nWrong card number or PIN!");
                        break;
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Wrong operation");
                    break;
            }
            if (chooseOperation1 == 0) {
                System.out.println("\nBye!");
                break;
            }
        }
    }
}