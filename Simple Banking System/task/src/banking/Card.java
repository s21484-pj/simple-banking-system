package banking;

import java.util.ArrayList;
import java.util.Random;

public class Card {
    private Long id;
    private String pin;
    private double balance;

    public Card() {
    }

    public Card(Long id, String pin, double balance) {
        this.id = id;
        this.pin = pin;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
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

    public void generateId() {
        final String BIN = "400000";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            int temp = random.nextInt(10);
            stringBuilder.append(temp);
        }
        String idAsString = BIN + stringBuilder;
        int checksum = luhnAlgorithm(idAsString);
        idAsString = idAsString.concat(String.valueOf(checksum));
        Long id = Long.parseLong(idAsString);
        this.setId(id);
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
