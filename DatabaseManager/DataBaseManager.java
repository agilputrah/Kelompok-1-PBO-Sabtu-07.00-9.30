import java.sql.*;

public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:mysql://localhost/cost_management_system";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }

    public static User getUser(String username) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String password = resultSet.getString("password");
            return new User(username, password);
        }

        return null;
    }

    public static void addExpense(Expense expense, User user) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO expenses (user_id, amount, category, date) VALUES (?, ?, ?, ?)");
        statement.setInt(1, user.getId());
        statement.setDouble(2, expense.getAmount());
        statement.setString(3, expense.getCategory());
        statement.setDate(4, new java.sql.Date(expense.getDate().getTime()));
        statement.executeUpdate();
    }

    // Add similar methods for budgets and income

    // Add more methods as needed for CRUD operations on the database
}
