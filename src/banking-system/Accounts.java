package bank_project;

import java.sql.*;
import java.util.Scanner;

public class Accounts {
    private Scanner scanner;
    private Connection connection;
    public Accounts(Connection connection, Scanner scanner){
        this.scanner = scanner;
        this.connection = connection;
    }
    public long open_account(String email){
        scanner.nextLine();
        System.out.print("Enter full name: ");
        String name = scanner.nextLine();
        System.out.print("Enter initial amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Create security pin: ");
        String pin = scanner.nextLine();
        long account_number = generateAccount_number();
        String query = "Insert INTO accounts(account_number, full_name, email, balance, security_pin) VALUES(?, ?, ?, ?, ?)";
        try{
            PreparedStatement pre = connection.prepareStatement(query);
            pre.setLong(1, account_number);
            pre.setString(2, name);
            pre.setString(3, email);
            pre.setDouble(4, amount);
            pre.setString(5, pin);
            int rows = pre.executeUpdate();
            if(rows > 0){
                System.out.println("Account opened successfully!");
                return account_number;
            }
            else{
                throw new RuntimeException("Account opening failed!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return account_number;
    }
    public long getAccount_number(String email){
        String query = "SELECT * FROM accounts WHERE email = ?";
        try{
            PreparedStatement pre = connection.prepareStatement(query);
            pre.setString(1, email);
            ResultSet resultSet = pre.executeQuery();
            if(resultSet.next()){
                return resultSet.getLong("account_number");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        throw new RuntimeException("Account number doesn't exists");
    }
    public long generateAccount_number(){
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT account_number From accounts ORDER BY account_number DESC LIMIT 1");
            if(resultSet.next()){
                return resultSet.getLong("account_number")+1;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return 1000100;
    }
    public boolean accountExists(String email){
        String query = "SELECT * FROM accounts WHERE email = ?";
        try{
            PreparedStatement pre = connection.prepareStatement(query);
            pre.setString(1, email);
            ResultSet resultSet = pre.executeQuery();
            if(resultSet.next()){
                return true;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
