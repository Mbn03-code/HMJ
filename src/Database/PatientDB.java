package Database;

import File.ReportFile;
import MainClasses.*;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Arrays;

public class PatientDB {

    // متد برای افزودن بیمار به جدول patients
    public static void addPatient(Patient patient, String roomType) {
        Connection connection = null;
        PreparedStatement statement = null;
        PreparedStatement roomStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();

            // درخواست اتاق خالی با نوع مشخص شده از کاربر
            String roomSql = "SELECT roomId FROM rooms WHERE isOccupied = false AND type = ? LIMIT 1";
            roomStatement = connection.prepareStatement(roomSql);
            roomStatement.setString(1, roomType);  // استفاده از نوع اتاق
            resultSet = roomStatement.executeQuery();

            if (resultSet.next()) {
                // اتاق خالی پیدا شد
                String roomId = resultSet.getString("roomId");

                // به روز رسانی وضعیت اتاق برای اینکه دیگر در دسترس نباشد
                String updateRoomSql = "UPDATE rooms SET isOccupied = true WHERE roomId = ?";
                statement = connection.prepareStatement(updateRoomSql);
                statement.setString(1, roomId);
                statement.executeUpdate();

                // اختصاص اتاق به بیمار
                Room room = new Room(roomId, roomType, true);
                patient.setRoom(room);
                patient.getRoom().setRoomID(roomId);  // اختصاص شناسه اتاق به بیمار
                patient.getRoom().setOccupied(true);  // بروزرسانی وضعیت isOccupied در شیء Room

                // دستور SQL برای اضافه کردن بیمار به جدول patients
                String sql = "INSERT INTO patients (nationalID, name, lastName, age, gender, phone, roomId, admissionDate, dischargeDate) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                // آماده‌سازی statement
                statement = connection.prepareStatement(sql);

                // تعیین مقادیر برای پارامترهای دستور SQL
                statement.setString(1, patient.getNationalID());
                statement.setString(2, patient.getName());
                statement.setString(3, patient.getLastName());
                statement.setString(4, patient.getAge());
                statement.setString(5, patient.getGender());
                statement.setString(6, patient.getPhone());
                statement.setString(7, patient.getRoom().getRoomID());
                statement.setString(8,patient.getAdmissionDate());
                statement.setString(9, patient.getDischargeDate());

                // اجرای دستور
                statement.executeUpdate();
                ReportFile.logMessage("Patient added successfully.");

            } else {
                ReportFile.logMessage("No available rooms with the selected type.");
                return; // اگر هیچ اتاق خالی نباشد، عملیات را متوقف می‌کنیم
            }

        } catch (SQLException e) {
            ReportFile.logMessage(e.getMessage());
        } finally {
            // بستن منابع
            try {
                if (resultSet != null) resultSet.close();
                if (roomStatement != null) roomStatement.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                ReportFile.logMessage(e.getMessage());
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
                ReportFile.logMessage("Patient removed successfully.");

                // دستور SQL برای به‌روزرسانی وضعیت اتاق
                String updateRoomSQL = "UPDATE rooms SET isOccupied = 0 WHERE RoomID NOT IN (SELECT roomId FROM patients)";
                updateRoomStatement = connection.prepareStatement(updateRoomSQL);

                // اجرای دستور به‌روزرسانی
                updateRoomStatement.executeUpdate();
                ReportFile.logMessage("Room status updated successfully.");
            } else {
                ReportFile.logMessage("No patient found with the provided national ID.");
            }

        } catch (SQLException e) {
            ReportFile.logMessage(e.getMessage());

        } finally {
            // بستن منابع
            try {
                if (deletePatientStatement != null) deletePatientStatement.close();
                if (updateRoomStatement != null) updateRoomStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                ReportFile.logMessage(e.getMessage());
            }
        }
    }
    public static void updatePatientDetails(String nationalID, String name, String lastName, String age, String gender, String phone) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // اتصال به پایگاه داده
            connection = DatabaseConnection.getConnection();

            // ساخت دستور SQL پویا برای به‌روزرسانی فیلدهای مشخص
            StringBuilder sql = new StringBuilder("UPDATE patients SET ");
            boolean isFirst = true;

            if (name != null) {
                sql.append("name = ?");
                isFirst = false;
            }
            if (lastName != null) {
                if (!isFirst) sql.append(", ");
                sql.append("lastName = ?");
                isFirst = false;
            }
            if (age != null) {
                if (!isFirst) sql.append(", ");
                sql.append("age = ?");
                isFirst = false;
            }
            if (gender != null) {
                if (!isFirst) sql.append(", ");
                sql.append("gender = ?");
                isFirst = false;
            }
            if (phone != null) {
                if (!isFirst) sql.append(", ");
                sql.append("phone = ?");
                isFirst = false;
            }

            sql.append(" WHERE nationalID = ?");

            // آماده‌سازی statement
            statement = connection.prepareStatement(sql.toString());

            // مقداردهی به پارامترها
            int paramIndex = 1;
            if (name != null) statement.setString(paramIndex++, name);
            if (lastName != null) statement.setString(paramIndex++, lastName);
            if (age != null) statement.setString(paramIndex++, age);
            if (gender != null) statement.setString(paramIndex++, gender);
            if (phone != null) statement.setString(paramIndex++, phone);
            statement.setString(paramIndex, nationalID);

            // اجرای دستور
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                ReportFile.logMessage("Patient details updated successfully.");
            } else {
                ReportFile.logMessage("No patient found with the specified nationalID.");
            }

        } catch (SQLException e) {
            ReportFile.logMessage(e.getMessage());

        } finally {
            // بستن منابع
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                ReportFile.logMessage(e.getMessage());

            }
        }
    }

    public static void readAllPatients(DefaultTableModel tableModel) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();

            String query = "SELECT * FROM patients";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                String nationalId = rs.getString("nationalId");
                String firstName = rs.getString("name");
                String lastName = rs.getString("lastname");
                String age = rs.getString("age");
                String gender = rs.getString("gender");
                String phone = rs.getString("phone");
                int roomId = rs.getInt("roomid");
                String admissionDate = rs.getString("admissiondate");
                String dischargeDate = rs.getString("dischargedate");

                Object[] row = {nationalId, firstName, lastName, age, gender, phone, roomId, admissionDate, dischargeDate};
                tableModel.addRow(row);
                System.out.println(Arrays.toString(row));

            }
        } catch (SQLException e) {
            ReportFile.logMessage(e.getMessage());

        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                ReportFile.logMessage(e.getMessage());

            }
        }
    }

    public static Patient searchPatientsByNationalId(String nationalId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn =DatabaseConnection.getConnection();

            String query = "SELECT * FROM patients WHERE nationalId = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, nationalId);
            rs = ps.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("name");
                String lastName = rs.getString("lastname");
                String age = rs.getString("age");
                String gender = rs.getString("gender");
                String phone = rs.getString("phone");
                String roomId = rs.getString("roomid");
                String admissionDate = rs.getString("admissiondate");
                String dischargeDate = rs.getString("dischargedate");


                return new Patient(nationalId, firstName, lastName, age, gender, phone,roomId, admissionDate, dischargeDate != null ? dischargeDate : "");
            }
        } catch (SQLException e) {
            ReportFile.logMessage(e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                ReportFile.logMessage(e.getMessage());
            }
        }
        return null;
    }

}
