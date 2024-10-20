package bank_project;

import java.sql.*;
import java.util.Scanner;

public class User {
    private Connection connection;
    private Scanner scanner;
    public User(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }
    public void register(){
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email id: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String user_password = scanner.nextLine();
        if(userExists(email)){
            System.out.println("User already exists for this email address");
            System.out.println();
            return;
        }
        try{
            String query = "INSERT INTO user(full_name, email, password) VALUES(?, ?, ?)";
            PreparedStatement pre = connection.prepareStatement(query);
            pre.setString(1, name);
            pre.setString(2, email);
            pre.setString(3, user_password);
            int rowsAffected = pre.executeUpdate();
            if(rowsAffected > 0){
                System.out.println("Registration successful!");
                System.out.println();
            }
            else{
                System.out.println("Registration Failed!");
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public String login(){
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        String query = "SELECT * FROM user WHERE email = ? AND password = ?";
        try{
            PreparedStatement pre = connection.prepareStatement(query);
            pre.setString(1, email);
            pre.setString(2, password);
            ResultSet resultSet = pre.executeQuery();
            if(resultSet.next()){
                return email;
            }
            else{
                return null;
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public boolean userExists(String email){
        String query = "SELECT * FROM user WHERE EMAIL = ?";
        try{
            PreparedStatement pre = connection.prepareStatement(query);
            pre.setString(1, email);
            ResultSet resultSet = pre.executeQuery();
            if(resultSet.next()){
                return true;
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
