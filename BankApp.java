import java.util.ArrayList;

// Transaction class to store deposit and withdrawal details
class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String toString() {
        return type + ": " + amount;
    }
}

// Account class
class Account {
    private String accountNumber;
    protected double balance; // protected so subclass can access
    protected ArrayList<Transaction> transactions; // protected for subclass access

    // Constructor
    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    // Deposit method
    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit", amount));
        System.out.println(amount + " deposited. New balance: " + balance);
    }

    // Withdraw method
    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactions.add(new Transaction("Withdraw", amount));
            System.out.println(amount + " withdrawn. New balance: " + balance);
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    // Print all transactions
    public void printTransactions() {
        System.out.println("Transactions for account " + accountNumber + ":");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    // Get current balance
    public double getBalance() {
        return balance;
    }
}

// SavingsAccount class extending Account
class SavingsAccount extends Account {
    private double annualInterestRate; // e.g., 0.05 for 5%

    // Constructor
    public SavingsAccount(String accountNumber, double initialBalance, double annualInterestRate) {
        super(accountNumber, initialBalance);
        this.annualInterestRate = annualInterestRate;
    }

    // Apply monthly interest for given months
    public void applyMonthlyInterest(int months) {
        double monthlyRate = annualInterestRate / 12;
        for (int i = 0; i < months; i++) {
            double interest = getBalance() * monthlyRate;
            deposit(interest); // deposit method will add to balance and record transaction
        }
        System.out.println(months + " months interest applied. Balance: " + getBalance());
    }
}

// Main class to test both Account and SavingsAccount
public class BankApp {
    public static void main(String[] args) {
        System.out.println("--- Testing Account ---");
        Account acc = new Account("12345", 1000);
        acc.deposit(500);
        acc.withdraw(200);
        acc.withdraw(2000); // should show insufficient balance
        acc.printTransactions();
        System.out.println("Current Balance: " + acc.getBalance());

        System.out.println("\n--- Testing SavingsAccount ---");
        SavingsAccount savAcc = new SavingsAccount("S001", 2000, 0.05); // 5% annual interest
        savAcc.deposit(500);
        savAcc.withdraw(300);
        savAcc.applyMonthlyInterest(3); // Apply 3 months interest
        savAcc.printTransactions();
        System.out.println("Final Balance: " + savAcc.getBalance());
    }
}
