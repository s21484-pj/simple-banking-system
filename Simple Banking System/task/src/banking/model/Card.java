package banking.model;

import java.util.ArrayList;
import java.util.Random;

public class Card {
    private int id;
    private String number;
    private String pin;
    private int balance;

    public Card() {
    }

    public Card(int id, String number, String pin, int balance) {
        this.id = id;
        this.number = number;
        this.pin = pin;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    private int luhnAlgorithm(String id) {
        ArrayList<Integer> digits = new ArrayList<>();
        int sum = 0;
        for (int i = 0; i < id.length(); i++) {
            digits.add(Integer.parseInt(String.valueOf(id.charAt(i))));
            if (i % 2 == 0) {
                int temp = digits.get(i) * 2;
                digits.set(i, temp);
            }
            if (digits.get(i) > 9) {
                digits.set(i, digits.get(i) - 9);
            }
        }
        for (Integer digit : digits) {
            sum += digit;
        }
        int temp = sum % 10;
        int checksum = 0;
        if (temp != 0) {
            while (temp < 10) {
                checksum++;
                temp++;
            }
        }
        return checksum;
    }

    public void generateNumber() {
        final String BIN = "400000";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            int temp = random.nextInt(10);
            stringBuilder.append(temp);
        }
        String numberAsString = BIN + stringBuilder;
        int checksum = luhnAlgorithm(numberAsString);
        numberAsString = numberAsString.concat(String.valueOf(checksum));
        this.setNumber(numberAsString);
    }

    public void generatePin() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int temp = random.nextInt(10);
            stringBuilder.append(temp);
        }
        this.setPin(String.valueOf(stringBuilder));
    }
}
