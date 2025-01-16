package Database;

import File.ReportFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/hospital";
    private static final String USER = "root";
    private static final String PASSWORD = "M.r.1383";

    // استفاده از Singleton برای اتصال
    private static Connection connection;

    // متد برای دریافت اتصال
    public static Connection getConnection() throws SQLException {
        // بررسی اینکه آیا اتصال قبلاً ایجاد شده است یا نه
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    // متد برای بستن اتصال
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            ReportFile.logMessage(e.getMessage());
        }
    }
}
