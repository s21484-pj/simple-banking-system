package banking.repository;

import banking.model.Card;
import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class SimpleBankingSystemRepository {

    private Connection connect() {
        //local db
//        String url = "jdbc:sqlite:D:\\SQLite 3.36.0\\simpleBankingSystem.db";

        //to pass tests
        String url = "jdbc:sqlite:card.s3db";
        Connection con = null;
        try {
            con = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return con;
    }

    /*Create DB and table card
        if they do not exist
     */
    public void createDB(String arg) {
        //local db
//        String url = "jdbc:sqlite:D:\\SQLite 3.36.0\\simpleBankingSystem.db";

        //to pass tests
        String url = "jdbc:sqlite:" + arg;

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                        "id INTEGER PRIMARY KEY NOT NULL," +
                        "number TEXT," +
                        "pin TEXT," +
                        "balance INTEGER DEFAULT 0);");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertCard(Card card) {
        String sql = "INSERT INTO card(number, pin, balance) VALUES(?,?,?)";
        String number = card.getNumber();
        String pin = card.getPin();
        int balance = card.getBalance();

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, number);
            statement.setString(2, pin);
            statement.setInt(3, balance);
            statement.executeUpdate();

            System.out.println("\nYour card has been created");
            System.out.println("Your card number:");
            System.out.println(number);
            System.out.println("Your card PIN:");
            System.out.println(pin);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Card getCard(String number, String pin){
        String sql = "SELECT id, number, pin, balance "
                + "FROM card WHERE number = ? AND pin = ?";
        Card card = null;

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, number);
            statement.setString(2, pin);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                int balance = resultSet.getInt(4);
                card = new Card(id, number, pin, balance);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return card;
    }

    public Card getCardByNumber(String number) {
        String sql = "SELECT id, number, pin, balance "
                + "FROM card WHERE number = ?";
        Card card = null;

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, number);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                String pin = resultSet.getString(3);
                int balance = resultSet.getInt(4);
                card = new Card(id, number, pin, balance);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return card;
    }

    public void addIncome(Card card, int income) {
        String sql = "UPDATE card SET balance = ? WHERE id = ?";

        try (Connection con = this.connect();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, card.getBalance() + income);
            statement.setInt(2, card.getId());
            statement.executeUpdate();
            System.out.println("Income was added!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeAccount(Card card) {
        String sql = "DELETE FROM card WHERE id = ?";

        try (Connection con = this.connect();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, card.getId());
            statement.executeUpdate();
            System.out.println("\nThe account has been closed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doTransfer(Card card, Card targetCard, int amount) {
        String sql = "UPDATE card SET balance = ? WHERE id = ?";

        try (Connection con = this.connect();
             PreparedStatement statement1 = con.prepareStatement(sql);
             PreparedStatement statement2 = con.prepareStatement(sql)) {
            con.setAutoCommit(false);

            statement1.setInt(1, card.getBalance() - amount);
            statement1.setInt(2, card.getId());
            statement1.executeUpdate();

            statement2.setInt(1, targetCard.getBalance() + amount);
            statement2.setInt(2, targetCard.getId());
            statement2.executeUpdate();
            con.commit();
            System.out.println("Success!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
