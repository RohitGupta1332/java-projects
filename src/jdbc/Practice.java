package jdbc;

import java.sql.*;

public class Practice {
    private static final String url = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String username = "root";
    private static final String password = "7439rohit";

    public static void main(String[] args) {
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "INSERT INTO student(name, age, marks) VALUES(?, ?, ?)";
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setString(1, "Sakshi");
            pr.setInt(2, 26);
            pr.setDouble(3, 76.5);
            int rows = pr.executeUpdate();
            if(rows > 0){
                System.out.println("Update Successful");
            }
            else{
                System.out.println("Update Failed");
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
