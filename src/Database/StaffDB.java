package Database;

import File.ReportFile;
import MainClasses.*;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDB {

    public static void addStaff(Staff staff) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();

            String sql = "INSERT INTO staff (nationalID, name, lastName, age, gender, phone, position, salary) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(sql);

            statement.setString(1, staff.getNationalID());
            statement.setString(2, staff.getName());
            statement.setString(3, staff.getLastName());
            statement.setString(4, staff.getAge());
            statement.setString(5, staff.getGender());
            statement.setString(6, staff.getPhone());
            statement.setString(7, staff.getPosition());
            statement.setString(8, staff.getSalary());

            statement.executeUpdate();
            ReportFile.logMessage("Staff added successfully.");

        } catch (SQLException e) {
            ReportFile.logMessage(e.getMessage());
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                ReportFile.logMessage(e.getMessage());
            }
        }
    }

    public static void removeStaff(String nationalID) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();

            String sql = "DELETE FROM staff WHERE nationalID = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, nationalID);

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                ReportFile.logMessage("Staff removed successfully.");
            } else {
                ReportFile.logMessage("No staff found with the provided national ID.");
            }

        } catch (SQLException e) {
            ReportFile.logMessage(e.getMessage());
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                ReportFile.logMessage(e.getMessage());

            }
        }
    }

    public static void updateStaffDetails(String nationalID, String name, String lastName, String age, String gender, String phone, String position, String salary) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();

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

            int paramIndex = 1;
            if (name != null) statement.setString(paramIndex++, name);
            if (lastName != null) statement.setString(paramIndex++, lastName);
            if (age != null) statement.setString(paramIndex++, age);
            if (gender != null) statement.setString(paramIndex++, gender);
            if (phone != null) statement.setString(paramIndex++, phone);
            if (position != null) statement.setString(paramIndex++, position);
            if (salary != null) statement.setString(paramIndex++, salary);
            statement.setString(paramIndex, nationalID);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                ReportFile.logMessage("Staff details updated successfully.");
            } else {
                ReportFile.logMessage("No staff found with the specified nationalID.");
            }

        } catch (SQLException e) {
            ReportFile.logMessage(e.getMessage());

        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                ReportFile.logMessage(e.getMessage());

            }
        }
    }

    public static void readAllStaff(DefaultTableModel tableModel) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();

            String query = "SELECT * FROM Staff";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                String nationalId = rs.getString("nationalId");
                String firstName = rs.getString("name");
                String lastName = rs.getString("lastname");
                String age = rs.getString("age");
                String gender = rs.getString("gender");
                String phone = rs.getString("phone");
                String position = rs.getString("position");
                String salary = rs.getString("salary");

                Object[] row = {nationalId, firstName, lastName, age, gender, phone,position, salary};
                tableModel.addRow(row);
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

    public static Staff searchStaffByNationalId(String nationalId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn=DatabaseConnection.getConnection();

            String query = "SELECT * FROM Staff WHERE nationalId = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, nationalId);
            rs = ps.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("name");
                String lastName = rs.getString("lastname");
                String age = rs.getString("age");
                String gender = rs.getString("gender");
                String phone = rs.getString("phone");
                String position = rs.getString("position");
                String salary = rs.getString("salary");

                return new Staff(nationalId, firstName, lastName, age, gender, phone, position , salary );
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
