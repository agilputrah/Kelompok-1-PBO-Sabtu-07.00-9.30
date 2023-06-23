package csm.mainview;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static User loggedInUser;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User login
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            loggedInUser = DatabaseManager.getUser(username);
            if (loggedInUser != null && loggedInUser.getPassword().equals(password)) {
                System.out.println("Login successful!");
                showMainMenu(scanner);
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showMainMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("==== Main Menu ====");
            System.out.println("1. Add Expense");
            System.out.println("2. Add Budget");
            System.out.println("3. Add Income");
            System.out.println("4. View Expenses");
            System.out.println("5. View Budgets");
            System.out.println("6. View Income");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addExpense(scanner);
                    break;
                case 2:
                    addBudget(scanner);
                    break;
                case 3:
                    addIncome(scanner);
                    break;
                case 4:
                    viewExpenses();
                    break;
                case 5:
                    viewBudgets();
                    break;
                case 6:
                    viewIncome();
                    break;
                case 7:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void addExpense(Scanner scanner) {
        System.out.print("Enter expense amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline character
        System.out.print("Enter expense category: ");
        String category = scanner.nextLine();
        Date date = new Date(); // Use current date
        Expense expense = new Expense(amount, category, date);

        try {
            DatabaseManager.addExpense(expense, loggedInUser);
            System.out.println("Expense added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addBudget(Scanner scanner) {
        System.out.print("Enter budget amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline character
        System.out.print("Enter budget category: ");
        String category = scanner.nextLine();
        Budget budget = new Budget(amount, category);

        try {
            DatabaseManager.addBudget(budget, loggedInUser);
            System.out.println("Budget added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addIncome(Scanner scanner) {
        System.out.print("Enter income amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline character
        Date date = new Date(); // Use current date
        Income income = new Income(amount, date);

        try {
            DatabaseManager.addIncome(income, loggedInUser);
            System.out.println("Income added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewExpenses() {
        try {
            List<Expense> expenses = DatabaseManager.getExpenses(loggedInUser);
            if (expenses.isEmpty()) {
                System.out.println("No expenses found.");
            } else {
                System.out.println("==== Expenses ====");
                for (Expense expense : expenses) {
                    System.out.println("Amount: " + expense.getAmount());
                    System.out.println("Category: " + expense.getCategory());
                    System.out.println("Date: " + expense.getDate());
                    System.out.println("--------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewBudgets() {
        try {
            List<Budget> budgets = DatabaseManager.getBudgets(loggedInUser);
            if (budgets.isEmpty()) {
                System.out.println("No budgets found.");
            } else {
                System.out.println("==== Budgets ====");
                for (Budget budget : budgets) {
                    System.out.println("Amount: " + budget.getAmount());
                    System.out.println("Category: " + budget.getCategory());
                    System.out.println("--------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewIncome() {
        try {
            List<Income> incomeList = DatabaseManager.getIncome(loggedInUser);
            if (incomeList.isEmpty()) {
                System.out.println("No income found.");
            } else {
                System.out.println("==== Income ====");
                for (Income income : incomeList) {
                    System.out.println("Amount: " + income.getAmount());
                    System.out.println("Date: " + income.getDate());
                    System.out.println("--------------------");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
