package Database;

import model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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
        PreparedStatement updatePatientStatement = null;
        PreparedStatement updateRoomStatement = null;
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
                String findPatientSQL = "SELECT nationalID, roomId FROM patients WHERE roomId = ?";
                findPatientStatement = connection.prepareStatement(findPatientSQL);
                findPatientStatement.setInt(1, roomId);
                resultSet = findPatientStatement.executeQuery();

                if (resultSet.next()) {
                    String nationalID = resultSet.getString("nationalID");

                    // 3. پیدا کردن اتاق خالی با همان نوع
                    String findAvailableRoomSQL = "SELECT roomId FROM rooms WHERE type = ? AND isOccupied = false LIMIT 1";
                    findRoomStatement = connection.prepareStatement(findAvailableRoomSQL);
                    findRoomStatement.setString(1, roomType);
                    resultSet = findRoomStatement.executeQuery();

                    if (resultSet.next()) {
                        int availableRoomId = resultSet.getInt("roomId");

                        // 4. بروزرسانی وضعیت بیمار و تاریخ مرخصی به تاریخ امروز
                        String updatePatientSQL = "UPDATE patients SET roomId = ? WHERE nationalID = ?";
                        updatePatientStatement = connection.prepareStatement(updatePatientSQL);
                        updatePatientStatement.setInt(1, availableRoomId);
                        updatePatientStatement.setString(2, nationalID);
                        updatePatientStatement.executeUpdate();

                        // 5. بروزرسانی وضعیت اتاق اصلی به خالی و اتاق جدید به اشغال
                        String updateRoomSQL = "UPDATE rooms SET isOccupied = false WHERE roomId = ?";
                        updateRoomStatement = connection.prepareStatement(updateRoomSQL);
                        updateRoomStatement.setInt(1, roomId);
                        updateRoomStatement.executeUpdate();

                        String updateNewRoomSQL = "UPDATE rooms SET isOccupied = true WHERE roomId = ?";
                        updateRoomStatement = connection.prepareStatement(updateNewRoomSQL);
                        updateRoomStatement.setInt(1, availableRoomId);
                        updateRoomStatement.executeUpdate();

                        // 6. حذف اتاق از جدول
                        String deleteRoomSQL = "DELETE FROM rooms WHERE roomId = ?";
                        deleteRoomStatement = connection.prepareStatement(deleteRoomSQL);
                        deleteRoomStatement.setInt(1, roomId);
                        deleteRoomStatement.executeUpdate();

                        System.out.println("Patient discharged and room reassigned successfully.");
                    } else {
                        System.out.println("No available rooms of type " + roomType + ".");
                        // اگر اتاق خالی پیدا نشد، تاریخ مرخصی بیمار به تاریخ امروز تغییر می‌کند
                        String updatePatientSQL = "UPDATE patients SET dischargeDate = ? WHERE roomId = ?";
                        updatePatientStatement = connection.prepareStatement(updatePatientSQL);
                        updatePatientStatement.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
                        updatePatientStatement.setInt(2, roomId);
                        updatePatientStatement.executeUpdate();

                        System.out.println("Patient discharged (no available room) and discharge date updated.");
                    }
                } else {
                    System.out.println("No patient found with the specified room.");
                }
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
                if (updatePatientStatement != null) updatePatientStatement.close();
                if (updateRoomStatement != null) updateRoomStatement.close();
                if (deleteRoomStatement != null) deleteRoomStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    

}
