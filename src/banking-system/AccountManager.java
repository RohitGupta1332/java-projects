package bank_project;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.Scanner;

public class AccountManager {
    private Scanner scanner;
    private Connection connection;
    public AccountManager(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }
    public void debitMoney(long account_number){
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter security pin: ");
        String pin = scanner.nextLine();
        String query = "SELECT * FROM accounts WHERE account_number = ? AND security_pin = ?";
        try{
            PreparedStatement pre = connection.prepareStatement(query);
            pre.setLong(1, account_number);
            pre.setString(2, pin);
            ResultSet resultSet = pre.executeQuery();
            if(resultSet.next()){
                double initial_balance = resultSet.getDouble("balance");
                if(initial_balance >= amount){
                    String query2 = "UPDATE accounts SET balance = balance-? WHERE account_number = ?";
                    PreparedStatement pre2 = connection.prepareStatement(query2);
                    pre2.setDouble(1, amount);
                    pre2.setLong(2, account_number);
                    int rows = pre2.executeUpdate();
                    if(rows > 0){
                        System.out.println(amount + " debited from your bank account.");
                        System.out.println();
                    }
                    else{
                        System.out.println("Something went wrong!");
                    }
                }
                else{
                    System.out.println("Insufficient balance!");
                    System.out.println();
                }
            }
            else{
                System.out.println("Incorrect security pin!");
                System.out.println();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void creditMoney(long account_number){
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter security pin: ");
        String pin = scanner.nextLine();
        String query = "SELECT * FROM accounts WHERE account_number = ? AND security_pin = ?";
        try{
            PreparedStatement pre = connection.prepareStatement(query);
            pre.setLong(1, account_number);
            pre.setString(2, pin);
            ResultSet resultSet = pre.executeQuery();
            if(resultSet.next()){
                String query2 = "UPDATE accounts SET balance = balance+? WHERE account_number = ?";
                PreparedStatement pre2 = connection.prepareStatement(query2);
                pre2.setDouble(1, amount);
                pre2.setLong(2, account_number);
                int rows = pre2.executeUpdate();
                if(rows > 0){
                    System.out.println(amount + " credited from your bank account.");
                    System.out.println();
                }
                else {
                    System.out.println("Something went wrong!");
                }
            }
            else{
                System.out.println("Incorrect security pin!");
                System.out.println();
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void transferMoney(long account_number){
        scanner.nextLine();
        System.out.print("Enter receiver account number: ");
        long receiver_account_number = scanner.nextLong();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter security pin: ");
        String pin = scanner.nextLine();
        String query1 = "UPDATE accounts SET balance = balance-? WHERE account_number = ? And security_pin = ?";
        String query2 = "UPDATE accounts SET balance = balance+? WHERE account_number = ?";
        try{
            connection.setAutoCommit(false);
            PreparedStatement pre1 = connection.prepareStatement(query1);
            PreparedStatement pre2 = connection.prepareStatement(query2);
            pre1.setDouble(1, amount);
            pre1.setLong(2, account_number);
            pre1.setString(3, pin);
            pre2.setDouble(1, amount);
            pre2.setLong(2, receiver_account_number);
            int row1 = pre1.executeUpdate();
            int row2 = pre2.executeUpdate();
            if(row1 > 0 && row2 > 0){
                System.out.println("Transaction successful!");
                System.out.println();
                connection.commit();
            }
            else{
                System.out.println("Transaction failed!");
                connection.rollback();
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public double checkBalance(long account_number){
        scanner.nextLine();
        System.out.print("Enter security pin: ");
        String pin = scanner.nextLine();
        String query = "SELECT * FROM accounts WHERE account_number = ? AND security_pin = ?";
        try{
            PreparedStatement pre= connection.prepareStatement(query);
            pre.setLong(1, account_number);
            pre.setString(2, pin);
            ResultSet resultSet = pre.executeQuery();
            if(resultSet.next()){
                return resultSet.getDouble("balance");
            }
            else{
                System.out.println("Invalid Security pin.");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}
