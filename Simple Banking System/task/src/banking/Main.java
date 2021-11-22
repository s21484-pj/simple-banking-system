package banking;

import banking.model.Card;
import banking.repository.SimpleBankingSystemRepository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SimpleBankingSystemRepository repository = new SimpleBankingSystemRepository();
        Scanner numScanner = new Scanner(System.in);
        Scanner strScanner = new Scanner(System.in);
        int chooseOperation1;
        int chooseOperation2;

        repository.createDB(args[1]);
        while (true) {
            printMenu1();
            chooseOperation1 = numScanner.nextInt();
            switch (chooseOperation1) {
                case 1:
                    Card newCard = new Card();
                    newCard.generateNumber();
                    newCard.generatePin();
                    repository.insertCard(newCard);
                    break;
                case 2:
                    System.out.println("\nEnter your card number:");
                    String number = strScanner.nextLine();
                    System.out.println("Enter your PIN:");
                    String pin = strScanner.nextLine();
                    Card card = repository.getCard(number, pin);
                    if (card != null) {
                        System.out.println("\nYou have successfully logged in!");
                        while (true) {
                            card = repository.getCard(number, pin);
                            printMenu2();
                            chooseOperation2 = numScanner.nextInt();
                            switch (chooseOperation2) {
                                case 1:
                                    System.out.println("\nBalance: " + card.getBalance());
                                    break;
                                case 2:
                                    System.out.println("\nEnter income:");
                                    int income = numScanner.nextInt();
                                    repository.addIncome(card, income);
                                    break;
                                case 3:
                                    System.out.println("\nTransfer" +
                                            "\nEnter card number:");
                                    String num = strScanner.nextLine();
                                    Card targetCard = repository.getCardByNumber(num);

                                    if (num.equals(card.getNumber())) {
                                        System.out.println("You can't transfer money to the same account!");
                                        break;
                                    } else if (!new Card().isLuhn(num)) {
                                        System.out.println("Probably you made a mistake in the card number. Please try again!");
                                        break;
                                    } else if (targetCard == null) {
                                        System.out.println("Such a card does not exist.");
                                        break;
                                    }

                                    System.out.println("Enter how much money you want to transfer:");
                                    int amount = numScanner.nextInt();

                                    if (card.getBalance() < amount) {
                                        System.out.println("Not enough money!");
                                        break;
                                    }
                                    repository.doTransfer(card, targetCard, amount);
                                    break;
                                case 4:
                                    repository.closeAccount(card);
                                    break;
                                case 5:
                                    System.out.println("\nYou have successfully logged out!");
                                    break;
                                case 0:
                                    chooseOperation1 = 0;
                                    break;
                                default:
                                    System.out.println("Wrong operation");
                                    break;
                            }
                            if (chooseOperation2 == 0 ||
                                    chooseOperation2 == 4 ||
                                    chooseOperation2 == 5) {
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

    static void printMenu1() {
        System.out.println("\n1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
    }

    static void printMenu2() {
        System.out.println("\n1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");
    }
}