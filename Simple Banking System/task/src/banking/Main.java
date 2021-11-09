package banking;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner numScanner = new Scanner(System.in);
        Scanner strScanner = new Scanner(System.in);
        Cards cards = new Cards();
        ArrayList<Card> generatedCards = new ArrayList<>();
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
                    card.generateId();
                    card.generatePin();
                    card.setBalance(0.0);
                    generatedCards.add(card);
                    cards.setCards(generatedCards);
                    System.out.println("\nYour card has been created");
                    System.out.println("Your card number:");
                    System.out.println(card.getId());
                    System.out.println("Your card PIN:");
                    System.out.println(card.getPin());
                    break;
                case 2:
                    System.out.println("\nEnter your card number:");
                    long id = numScanner.nextLong();
                    System.out.println("Enter your PIN:");
                    String pin = strScanner.nextLine();
                    ArrayList<Card> y = cards.getCards();
                    for (Card x : y) {
                        if (x.getId() == id && x.getPin().equals(pin)) {
                            System.out.println("\nYou have successfully logged in!");
                            while (true) {
                                System.out.println("\n1. Balance");
                                System.out.println("2. Log out");
                                System.out.println("0. Exit");
                                chooseOperation2 = numScanner.nextInt();
                                switch (chooseOperation2) {
                                    case 1:
                                        double balance = x.getBalance();
                                        System.out.println("\nBalance: " + balance);
                                        break;
                                    case 2:
                                        System.out.println("\nYou have successfully logged out!");
                                        break;
                                    case 0:
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