package jdbc;

import java.sql.*;
import java.util.Scanner;

public class StudentDataBase {
    private final String url = "jdbc:mysql://localhost:3306/mydatabase";
    private final String username = "root";
    private final String password = "7439rohit";
    public void insert(){
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "INSERT INTO students(name, age, marks) VALUES(?, ?, ?)";
            PreparedStatement pr = connection.prepareStatement(query);
            Scanner sc= new Scanner(System.in);
            while(true){
                System.out.print("Enter name: ");
                String name = sc.next();
                System.out.print("Enter age: ");
                int age = sc.nextInt();
                System.out.print("Enter marks: ");
                double marks = sc.nextDouble();
                System.out.print("Enter more data: (Y/N)");
                String ch = sc.next();
                pr.setString(1, name);
                pr.setInt(2, age);
                pr.setDouble(3, marks);
                pr.addBatch();
                if(ch.equalsIgnoreCase("N")){
                    break;
                }
            }
            pr.executeBatch();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void retrieve(){
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement stm = connection.createStatement();
            String query = "SELECT * FROM students";
            ResultSet set = stm.executeQuery(query);
            while(set.next()){
                System.out.println("Student Id: " + set.getInt("id"));
                System.out.println("Name: " + set.getString("name"));
                System.out.println("Age: " + set.getInt("age"));
                System.out.println("Marks: " + set.getDouble("marks"));
                System.out.println("_".repeat(30));
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void update(int id, double marks){
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            String query = String.format("UPDATE students SET marks = %f WHERE id = %d",marks, id);
            int rows = statement.executeUpdate(query);
            if(rows > 0){
                System.out.println("Update successful!");
            }
            else{
                System.out.println("Update Failed");
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
