package banking;

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

    public void generateId() {
        final String BIN = "400000";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int temp = random.nextInt(10);
            stringBuilder.append(temp);
        }
        String idAsString = BIN + stringBuilder;
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
