package Database;

import MainClasses.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDB {

    // متد برای افزودن کارکن به جدول staff
    public static void addStaff(Staff staff) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();

            // دستور SQL برای افزودن کارکن
            String sql = "INSERT INTO staff (nationalID, name, lastName, age, gender, phone, position, salary) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(sql);

            // تعیین مقادیر برای پارامترهای دستور SQL
            statement.setString(1, staff.getNationalID());
            statement.setString(2, staff.getName());
            statement.setString(3, staff.getLastName());
            statement.setInt(4, staff.getAge());
            statement.setString(5, staff.getGender());
            statement.setString(6, staff.getPhone());
            statement.setString(7, staff.getPosition());
            statement.setString(8, staff.getSalary());

            // اجرای دستور
            statement.executeUpdate();
            System.out.println("Staff added successfully.");

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

    // متد برای حذف کارکن از جدول staff
    public static void removeStaff(String nationalID) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();

            // دستور SQL برای حذف کارکن
            String sql = "DELETE FROM staff WHERE nationalID = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, nationalID);

            // اجرای دستور حذف
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Staff removed successfully.");
            } else {
                System.out.println("No staff found with the provided national ID.");
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

    // متد برای به‌روزرسانی اطلاعات کارکن
    public static void updateStaffDetails(String nationalID, String name, String lastName, Integer age, String gender, String phone, String position, String salary) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();

            // ساخت دستور SQL پویا برای به‌روزرسانی فیلدهای مشخص
            StringBuilder sql = new StringBuilder("UPDATE staff SET ");
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
            if (position != null) {
                if (!isFirst) sql.append(", ");
                sql.append("position = ?");
                isFirst = false;
            }
            if (salary != null) {
                if (!isFirst) sql.append(", ");
                sql.append("salary = ?");
            }

            sql.append(" WHERE nationalID = ?");

            statement = connection.prepareStatement(sql.toString());

            // مقداردهی به پارامترها
            int paramIndex = 1;
            if (name != null) statement.setString(paramIndex++, name);
            if (lastName != null) statement.setString(paramIndex++, lastName);
            if (age != null) statement.setInt(paramIndex++, age);
            if (gender != null) statement.setString(paramIndex++, gender);
            if (phone != null) statement.setString(paramIndex++, phone);
            if (position != null) statement.setString(paramIndex++, position);
            if (salary != null) statement.setString(paramIndex++, salary);
            statement.setString(paramIndex, nationalID);

            // اجرای دستور
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Staff details updated successfully.");
            } else {
                System.out.println("No staff found with the specified nationalID.");
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

    // متد برای خواندن اطلاعات تمام کارکنان
    public static List<Staff> readStaff() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Staff> staffList = new ArrayList<>();  // لیست کارکنان

        try {
            connection = DatabaseConnection.getConnection();

            // دستور SQL برای خواندن تمام کارکنان
            String sql = "SELECT nationalID, name, lastName, age, gender, phone, position, salary FROM staff";

            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nationalID = resultSet.getString("nationalID");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                String phone = resultSet.getString("phone");
                String position = resultSet.getString("position");
                String salary = resultSet.getString("salary");

                // اضافه کردن هر کارمند به لیست
                Staff staffMember = new Staff(nationalID, name, lastName, age, gender, phone, position, salary);
                staffList.add(staffMember);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return staffList;  // بازگشت لیست کارکنان
    }


    // متد برای جستجو کارکن بر اساس کد ملی
    public static void searchStaffByNationalID(String nationalID) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            if (nationalID == null || nationalID.isEmpty()) {
                System.out.println("Please provide a valid National ID.");
                return;
            }

            connection = DatabaseConnection.getConnection();

            // کوئری SQL برای جستجوی کارکن بر اساس کد ملی
            String sql = "SELECT nationalID, name, lastName, age, gender, phone, position, salary FROM staff WHERE nationalID = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, nationalID);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Staff found:");
                System.out.println("-------------------------------------------------------------");
                do {
                    String foundNationalID = resultSet.getString("nationalID");
                    String foundName = resultSet.getString("name");
                    String foundLastname = resultSet.getString("lastName");
                    int age = resultSet.getInt("age");
                    String gender = resultSet.getString("gender");
                    String phone = resultSet.getString("phone");
                    String position = resultSet.getString("position");
                    String salary = resultSet.getString("salary");

                    System.out.printf("National ID: %s, Name: %s %s, Age: %d, Gender: %s, Phone: %s, Position: %s, Salary: %s%n",
                            foundNationalID, foundName, foundLastname, age, gender, phone, position, salary);
                } while (resultSet.next());
                System.out.println("-------------------------------------------------------------");
            } else {
                System.out.println("No staff found with the given National ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
