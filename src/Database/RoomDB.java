package Database;

import MainClasses.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class RoomDB {

    public static void addRoom(Room room) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // اتصال به پایگاه داده
            connection = DatabaseConnection.getConnection();

            // دستور SQL برای درج یک اتاق (بدون نیاز به مقدار roomId)
            String sql = "INSERT INTO rooms (Type, isOccupied) VALUES (?, ?)";

            // آماده‌سازی statement
            statement = connection.prepareStatement(sql);

            // تعیین مقادیر برای پارامترهای دستور SQL
            statement.setString(1, room.getType()); // نوع اتاق
            statement.setBoolean(2, room.isOccupied()); // وضعیت اشغال بودن

            // اجرای دستور
            statement.executeUpdate();
            System.out.println("Room added successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // بستن منابع
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void removeRoomAndReassign(int roomId) {
        Connection connection = null;
        PreparedStatement findRoomStatement = null;
        PreparedStatement findPatientStatement = null;
        PreparedStatement findAvailableRoomStatement = null;
        PreparedStatement updateRoomStatement = null;
        PreparedStatement deletePatientStatement = null;
        PreparedStatement deleteRoomStatement = null;
        ResultSet resultSet = null;

        try {
            // اتصال به پایگاه داده
            connection = DatabaseConnection.getConnection();

            // 1. پیدا کردن نوع اتاق با استفاده از roomId
            String findRoomSQL = "SELECT type FROM rooms WHERE roomId = ?";
            findRoomStatement = connection.prepareStatement(findRoomSQL);
            findRoomStatement.setInt(1, roomId);
            resultSet = findRoomStatement.executeQuery();

            if (resultSet.next()) {
                String roomType = resultSet.getString("type");

                // 2. پیدا کردن بیمار مربوطه که این اتاق را رزرو کرده است
                String findPatientSQL = "SELECT nationalID FROM patients WHERE roomId = ?";
                findPatientStatement = connection.prepareStatement(findPatientSQL);
                findPatientStatement.setInt(1, roomId);
                resultSet = findPatientStatement.executeQuery();

                if (resultSet.next()) {
                    // بیمار پیدا شد
                    String nationalID = resultSet.getString("nationalID");

                    // 3. پیدا کردن اتاق خالی با همان نوع
                    String findAvailableRoomSQL = "SELECT roomId FROM rooms WHERE type = ? AND isOccupied = false LIMIT 1";
                    findAvailableRoomStatement = connection.prepareStatement(findAvailableRoomSQL);
                    findAvailableRoomStatement.setString(1, roomType);
                    ResultSet availableRoomResult = findAvailableRoomStatement.executeQuery();

                    if (availableRoomResult.next()) {
                        int availableRoomId = availableRoomResult.getInt("roomId");

                        // بروزرسانی وضعیت اتاق جدید به اشغال
                        String updateRoomSQL = "UPDATE rooms SET isOccupied = true WHERE roomId = ?";
                        updateRoomStatement = connection.prepareStatement(updateRoomSQL);
                        updateRoomStatement.setInt(1, availableRoomId);
                        updateRoomStatement.executeUpdate();

                        // بروزرسانی بیمار به اتاق جدید
                        String updatePatientSQL = "UPDATE patients SET roomId = ? WHERE nationalID = ?";
                        PreparedStatement updatePatientStatement = connection.prepareStatement(updatePatientSQL);
                        updatePatientStatement.setInt(1, availableRoomId);
                        updatePatientStatement.setString(2, nationalID);
                        updatePatientStatement.executeUpdate();

                        System.out.println("Room reassigned successfully for patient.");
                    } else {
                        // اتاق خالی پیدا نشد، بیمار از جدول حذف می‌شود
                        String deletePatientSQL = "DELETE FROM patients WHERE nationalID = ?";
                        deletePatientStatement = connection.prepareStatement(deletePatientSQL);
                        deletePatientStatement.setString(1, nationalID);
                        deletePatientStatement.executeUpdate();

                        System.out.println("No available room with the same type. Patient discharged and removed from the database.");
                    }
                }

                // 4. حذف اتاق
                String deleteRoomSQL = "DELETE FROM rooms WHERE roomId = ?";
                deleteRoomStatement = connection.prepareStatement(deleteRoomSQL);
                deleteRoomStatement.setInt(1, roomId);
                deleteRoomStatement.executeUpdate();

                System.out.println("Room deleted successfully.");
            } else {
                System.out.println("No room found with the specified roomId.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // بستن منابع
            try {
                if (resultSet != null) resultSet.close();
                if (findRoomStatement != null) findRoomStatement.close();
                if (findPatientStatement != null) findPatientStatement.close();
                if (findAvailableRoomStatement != null) findAvailableRoomStatement.close();
                if (updateRoomStatement != null) updateRoomStatement.close();
                if (deletePatientStatement != null) deletePatientStatement.close();
                if (deleteRoomStatement != null) deleteRoomStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void updateRoomType(int roomId, String newType) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // اتصال به پایگاه داده
            connection = DatabaseConnection.getConnection();

            // دستور SQL برای به‌روزرسانی نوع اتاق
            String sql = "UPDATE rooms SET type = ? WHERE roomId = ?";

            // آماده‌سازی statement
            statement = connection.prepareStatement(sql);

            // مقداردهی به پارامترهای دستور
            statement.setString(1, newType); // نوع جدید
            statement.setInt(2, roomId);    // شناسه اتاق

            // اجرای دستور
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Room type updated successfully.");
            } else {
                System.out.println("No room found with the specified roomId.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // بستن منابع
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void readRooms() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // اتصال به پایگاه داده
            connection = DatabaseConnection.getConnection();

            // کوئری SQL برای خواندن تمامی اتاق‌ها
            String sql = "SELECT roomId, type, isOccupied FROM rooms";
            statement = connection.prepareStatement(sql);

            // اجرای query
            resultSet = statement.executeQuery();

            // بررسی نتایج
            System.out.println("Rooms in the database:");
            System.out.println("-------------------------------------------------------------");
            while (resultSet.next()) {
                int roomId = resultSet.getInt("roomId");
                String type = resultSet.getString("type");
                boolean isOccupied = resultSet.getBoolean("isOccupied");

                System.out.printf("Room ID: %d, Type: %s, Occupied: %s%n",
                        roomId, type, isOccupied ? "Yes" : "No");
            }
            System.out.println("-------------------------------------------------------------");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // بستن منابع
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void readAvailableRooms() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // اتصال به پایگاه داده
            connection = DatabaseConnection.getConnection();

            // کوئری برای دریافت اتاق‌های خالی
            String sql = "SELECT roomId, type FROM rooms WHERE isOccupied = false";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            // نمایش اتاق‌های خالی
            System.out.println("Available Rooms:");
            boolean found = false;
            while (resultSet.next()) {
                int roomId = resultSet.getInt("roomId");
                String type = resultSet.getString("type");
                System.out.println("Room ID: " + roomId + ", Type: " + type);
                found = true;
            }

            if (!found) {
                System.out.println("No available rooms found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // بستن منابع
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
