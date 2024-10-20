import java.sql.Connection;
import java.sql.DriverManager;

class Main{
    public static void main(String[] args) {
        System.out.println("Hello world");
        try{
            String driver = "com.mysql.cj.jdbc.Driver";
            String databaseUrl = "jdbc:mysql://localhost:3306/";
            String databaseName = "Rohit";
            String username = "root";
            String password = "7439rohit";
            Class.forName(driver);
            Connection con = DriverManager.getConnection(databaseUrl, username, password);
            System.out.println("Database connected");

        }
        catch(Exception e){
            System.out.println("some error " + e);
        }
    }
}