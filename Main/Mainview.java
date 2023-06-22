import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User login
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            User user = DatabaseManager.getUser(username);
            if (user != null && user.getPassword().equals(password)) {
                System.out.println("Login successful!");

                // Expense entry
                System.out.print("Enter expense amount: ");
                double amount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline character
                System.out.print("Enter expense category: ");
                String category = scanner.nextLine();
                Date date = new Date(); // Use current date
                Expense expense = new Expense(amount, category, date);

                DatabaseManager.addExpense(expense, user);

                System.out.println("Expense added successfully!");
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
