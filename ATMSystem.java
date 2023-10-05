import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class User {
    private int userId;
    private int pin;
    private double balance;

    public User(int userId, int pin, double initialBalance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = initialBalance;
    }

    public int getUserId() {
        return userId;
    }

    public int getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

class Transaction {
    private int userId;
    private String description;
    private double amount;

    public Transaction(int userId, String description, double amount) {
        this.userId = userId;
        this.description = description;
        this.amount = amount;
    }

    public int getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }
}

public class ATMSystem {
    private static Map<Integer, User> users = new HashMap<>();
    private static List<Transaction> transactions = new ArrayList<>();
    private static int currentUserID = -1;

    public static void main(String[] args) {
        initializeUsers();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the ATM!");
        while (true) {
            if (currentUserID == -1) {
                System.out.print("Enter User ID: ");
                int userID = scanner.nextInt();
                System.out.print("Enter PIN: ");
                int pin = scanner.nextInt();
                if (authenticateUser(userID, pin)) {
                    currentUserID = userID;
                    System.out.println("Authentication successful.");
                } else {
                    System.out.println("Authentication failed. Please try again.");
                }
            } else {
                displayMenu();
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        viewTransactionHistory();
                        break;
                    case 2:
                        depositMoney(scanner);
                        break;
                    case 3:
                        withdrawMoney(scanner);
                        break;
                    case 4:
                        transferMoney(scanner);
                        break;
                    case 5:
                        System.out.println("Thank you for using the ATM!");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    private static void initializeUsers() {
        users.put(12345, new User(12345, 1111, 1000.0));
        users.put(54321, new User(54321, 2222, 500.0));
    }

    private static boolean authenticateUser(int userID, int pin) {
        User user = users.get(userID);
        return user != null && user.getPin() == pin;
    }

    private static void displayMenu() {
        System.out.println("Choose an option:");
        System.out.println("1. View Transaction History");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Transfer Money");
        System.out.println("5. Quit");
        System.out.print("Enter your choice: ");
    }

    private static void viewTransactionHistory() {
        System.out.println("Transaction History for User " + currentUserID + ":");
        for (Transaction transaction : transactions) {
            if (transaction.getUserId() == currentUserID) {
                System.out.println("Description: " + transaction.getDescription() +
                        ", Amount: $" + transaction.getAmount());
            }
        }
    }

    private static void depositMoney(Scanner scanner) {
        System.out.print("Enter the amount to deposit: $");
        double amount = scanner.nextDouble();
        if (amount > 0) {
            User currentUser = users.get(currentUserID);
            currentUser.deposit(amount);
            transactions.add(new Transaction(currentUserID, "Deposit", amount));
            System.out.println("Deposit successful.");
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    private static void withdrawMoney(Scanner scanner) {
        System.out.print("Enter the amount to withdraw: $");
        double amount = scanner.nextDouble();
        User currentUser = users.get(currentUserID);
        if (currentUser.withdraw(amount)) {
            transactions.add(new Transaction(currentUserID, "Withdrawal", amount));
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance.");
        }
    }

    private static void transferMoney(Scanner scanner) {
        System.out.print("Enter the recipient's User ID: ");
        int recipientID = scanner.nextInt();
        System.out.print("Enter the amount to transfer: $");
        double amount = scanner.nextDouble();

        User sender = users.get(currentUserID);
        User recipient = users.get(recipientID);

        if (recipient != null && amount > 0 && sender != null && sender.withdraw(amount)) {
            recipient.deposit(amount);
            transactions.add(new Transaction(currentUserID, "Transfer to " + recipientID, amount));
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Invalid transfer or recipient user.");
        }
    }
}
