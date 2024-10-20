package jdbc;

import java.sql.*;

public class DatabaseConnection {
    private static String url = "jdbc:mysql://localhost:3306/mydatabase";
    private static String username = "root";
    private static String password = "7439rohit";

    public static void main(String[] args) {
        try{
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stm = conn.createStatement();
            String query = "SELECT * FROM student";
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
}
