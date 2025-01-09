package Database;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDB {

    // متد برای افزودن بیمار به جدول patients
    public static void addPatient(Patient patient) {
        Connection connection = null;
        PreparedStatement statement = null;
        PreparedStatement roomStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();

            // مرحله اول: انتخاب اولین اتاق خالی (isOccupied = false)
            String roomSql = "SELECT roomId FROM rooms WHERE isOccupied = false LIMIT 1";
            roomStatement = connection.prepareStatement(roomSql);
            resultSet = roomStatement.executeQuery();

            if (resultSet.next()) {
                // اتاق موجود را پیدا کردیم
                int roomId = resultSet.getInt("roomId");

                // به روز رسانی وضعیت اتاق برای اینکه دیگر در دسترس نباشد
                String updateRoomSql = "UPDATE rooms SET isOccupied = true WHERE roomId = ?";
                statement = connection.prepareStatement(updateRoomSql);
                statement.setInt(1, roomId);
                statement.executeUpdate();

                // انتخاب اتاق برای بیمار
                patient.getRoom().setRoomID(roomId); // اختصاص شناسه اتاق به بیمار
                patient.getRoom().setOccupied(true); // بروزرسانی وضعیت isOccupied در شیء Room
            } else {
                System.out.println("No available rooms.");
                return; // اگر هیچ اتاق خالی نباشد، عملیات را متوقف می‌کنیم
            }

            // دستور SQL برای اضافه کردن بیمار
            String sql = "INSERT INTO patients (nationalID, name, lastName, age, gender, phone, roomId, admissionDate, dischargeDate) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // آماده‌سازی statement
            statement = connection.prepareStatement(sql);

            // تعیین مقادیر برای پارامترهای دستور SQL
            statement.setString(1, patient.getNationalID());
            statement.setString(2, patient.getName());
            statement.setString(3, patient.getLastName());
            statement.setInt(4, patient.getAge());
            statement.setString(5, patient.getGender());
            statement.setString(6, patient.getPhone());
            statement.setInt(7, patient.getRoom().getRoomID());  // شناسه اتاق
            statement.setDate(8, java.sql.Date.valueOf(patient.getAdmissionDate()));  // تبدیل LocalDate به Date
            statement.setDate(9, java.sql.Date.valueOf(patient.getDischargeDate()));  // تبدیل LocalDate به Date

            // اجرای دستور
            statement.executeUpdate();
            System.out.println("Patient added successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // بستن منابع
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (roomStatement != null) {
                    roomStatement.close();
                }
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
    public static void removePatient(String nationalID) {
        Connection connection = null;
        PreparedStatement deletePatientStatement = null;
        PreparedStatement updateRoomStatement = null;

        try {
            // اتصال به پایگاه داده
            connection = DatabaseConnection.getConnection();

            // دستور SQL برای حذف بیمار از جدول
            String deletePatientSQL = "DELETE FROM patients WHERE nationalID = ?";
            deletePatientStatement = connection.prepareStatement(deletePatientSQL);
            deletePatientStatement.setString(1, nationalID);

            // اجرای دستور حذف
            int affectedRows = deletePatientStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Patient removed successfully.");

                // دستور SQL برای به‌روزرسانی وضعیت اتاق
                String updateRoomSQL = "UPDATE rooms SET isOccupied = 0 WHERE RoomID NOT IN (SELECT roomId FROM patients)";
                updateRoomStatement = connection.prepareStatement(updateRoomSQL);

                // اجرای دستور به‌روزرسانی
                updateRoomStatement.executeUpdate();
                System.out.println("Room status updated successfully.");
            } else {
                System.out.println("No patient found with the provided national ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // بستن منابع
            try {
                if (deletePatientStatement != null) deletePatientStatement.close();
                if (updateRoomStatement != null) updateRoomStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void updatePatients(){

    }
    public void readPatients(){

    }
    public void searchIDPatients(){

    }
    public void searchNAMEPatients(){

    }
}
