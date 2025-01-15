package Database;

import MainClasses.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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
    public static void updatePatientDetails(String nationalID, String name, String lastName, Integer age, String gender, String phone) {
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
            if (age != null) statement.setInt(paramIndex++, age);
            if (gender != null) statement.setString(paramIndex++, gender);
            if (phone != null) statement.setString(paramIndex++, phone);
            statement.setString(paramIndex, nationalID);

            // اجرای دستور
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Patient details updated successfully.");
            } else {
                System.out.println("No patient found with the specified nationalID.");
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

    public static void readPatients() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // اتصال به پایگاه داده
            connection = DatabaseConnection.getConnection();

            // دستور SQL برای خواندن تمام بیماران
            String sql = "SELECT nationalID, name, lastname, age, gender, phone, roomId, dischargeDate FROM patients";

            // آماده‌سازی statement
            statement = connection.prepareStatement(sql);

            // اجرای query
            resultSet = statement.executeQuery();

            // نمایش اطلاعات
            System.out.println("Patients List:");
            System.out.println("-------------------------------------------------------------");
            while (resultSet.next()) {
                String nationalID = resultSet.getString("nationalID");
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                String phone = resultSet.getString("phone");
                int roomId = resultSet.getInt("roomId");
                java.sql.Date dischargeDate = resultSet.getDate("dischargeDate");

                System.out.printf("National ID: %s, Name: %s %s, Age: %d, Gender: %s, Phone: %s, Room ID: %d, Discharge Date: %s%n",
                        nationalID, name, lastname, age, gender, phone, roomId, dischargeDate != null ? dischargeDate.toString() : "N/A");
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
    public static void searchPatientByNationalID(String nationalID) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // بررسی مقدار ورودی
            if (nationalID == null || nationalID.isEmpty()) {
                System.out.println("Please provide a valid National ID.");
                return;
            }

            // اتصال به پایگاه داده
            connection = DatabaseConnection.getConnection();

            // کوئری SQL برای جستجوی بیمار بر اساس کد ملی
            String sql = "SELECT nationalID, name, lastname, age, gender, phone, roomId, dischargeDate FROM patients WHERE nationalID = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, nationalID);

            // اجرای query
            resultSet = statement.executeQuery();

            // بررسی نتایج و نمایش اطلاعات
            if (resultSet.next()) {
                System.out.println("Patient found:");
                System.out.println("-------------------------------------------------------------");
                do {
                    String foundNationalID = resultSet.getString("nationalID");
                    String foundName = resultSet.getString("name");
                    String foundLastname = resultSet.getString("lastname");
                    int age = resultSet.getInt("age");
                    String gender = resultSet.getString("gender");
                    String phone = resultSet.getString("phone");
                    int roomId = resultSet.getInt("roomId");
                    java.sql.Date dischargeDate = resultSet.getDate("dischargeDate");

                    System.out.printf("National ID: %s, Name: %s %s, Age: %d, Gender: %s, Phone: %s, Room ID: %d, Discharge Date: %s%n",
                            foundNationalID, foundName, foundLastname, age, gender, phone, roomId, dischargeDate != null ? dischargeDate.toString() : "N/A");
                } while (resultSet.next());
                System.out.println("-------------------------------------------------------------");
            } else {
                System.out.println("No patient found with the given National ID.");
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
