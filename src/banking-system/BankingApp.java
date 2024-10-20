package bank_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class BankingApp {
    private static final String url = "jdbc:mysql://localhost:3306/banking_system";
    private static final String username = "root";
    private static final String password = "7439rohit";
    public static void main(String[] args) {
        System.out.println("WELCOME TO BANKING SYSTEM!!!!");
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Scanner scanner = new Scanner(System.in);
            User user = new User(connection, scanner);
            Accounts accounts = new Accounts(connection, scanner);
            AccountManager accountManager = new AccountManager(connection, scanner);
            while(true){
                System.out.println("1. Registration ");
                System.out.println("2. Login");
                System.out.println("3. Exit ");
                System.out.print("Enter your choice: ");
                int ch = scanner.nextInt();
                scanner.nextLine();
                switch (ch) {
                    case 1:
                        user.register();
                        break;
                    case 2:
                        String email = user.login();
                        if(email != null){
                            System.out.println("Logged in successfully!");
                            System.out.println();
                            if(!accounts.accountExists(email)){
                                System.out.println("1. Open bank account");
                                System.out.println("2. Exit");
                                System.out.print("Enter your choice: ");
                                int choice = scanner.nextInt();
                                if(choice == 1){
                                    System.out.println("Your account number is: " + accounts.open_account(email));
                                    System.out.println();
                                }
                                else{
                                    return;
                                }
                            }
                            int choice2 = 0;
                            long account_number = accounts.getAccount_number(email);
                            while(choice2 != 5){
                                System.out.println("1. Debit money");
                                System.out.println("2. Credit money");
                                System.out.println("3. Transfer money");
                                System.out.println("4. Check balance");
                                System.out.println("5. Log out");
                                System.out.print("Enter your choice: ");
                                choice2 = scanner.nextInt();
                                switch (choice2) {
                                    case 1:
                                        accountManager.debitMoney(account_number);
                                        break;
                                    case 2:
                                        accountManager.creditMoney(account_number);
                                        break;
                                    case 3:
                                        accountManager.transferMoney(account_number);
                                        break;
                                    case 4:
                                        double balance = accountManager.checkBalance(account_number);
                                        System.out.println("Balance: " + balance);
                                        System.out.println();
                                        break;
                                    case 5:
                                        break;
                                }
                            }
                        }
                        else{
                            System.out.println("Incorrect email or password!");
                            System.out.println();
                            break;
                        }
                    case 3:
                        System.out.println("Thank you for using banking system!");
                        System.out.println("Exiting....");
                        return;
                    default:
                        System.out.println("invalid choice!");
                        break;
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
