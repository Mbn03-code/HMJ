package Database;

import File.ReportFile;
import MainClasses.Patient;
import MainClasses.Room;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class RoomDB {

    public static void addRoom(Room room) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();

            String sql = "INSERT INTO rooms (Type, isOccupied) VALUES (?, ?)";

            statement = connection.prepareStatement(sql);

            statement.setString(1, room.getType()); // نوع اتاق
            statement.setBoolean(2, room.isOccupied()); // وضعیت اشغال بودن

            statement.executeUpdate();
            ReportFile.logMessage("Room added successfully.");

        } catch (SQLException e) {
            ReportFile.logMessage(e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                ReportFile.logMessage(e.getMessage());
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
            connection = DatabaseConnection.getConnection();

            String findRoomSQL = "SELECT type FROM rooms WHERE roomId = ?";
            findRoomStatement = connection.prepareStatement(findRoomSQL);
            findRoomStatement.setInt(1, roomId);
            resultSet = findRoomStatement.executeQuery();

            if (resultSet.next()) {
                String roomType = resultSet.getString("type");

                String findPatientSQL = "SELECT nationalID FROM patients WHERE roomId = ?";
                findPatientStatement = connection.prepareStatement(findPatientSQL);
                findPatientStatement.setInt(1, roomId);
                resultSet = findPatientStatement.executeQuery();

                if (resultSet.next()) {
                    String nationalID = resultSet.getString("nationalID");

                    String findAvailableRoomSQL = "SELECT roomId FROM rooms WHERE type = ? AND isOccupied = false LIMIT 1";
                    findAvailableRoomStatement = connection.prepareStatement(findAvailableRoomSQL);
                    findAvailableRoomStatement.setString(1, roomType);
                    ResultSet availableRoomResult = findAvailableRoomStatement.executeQuery();

                    if (availableRoomResult.next()) {
                        int availableRoomId = availableRoomResult.getInt("roomId");

                        String updateRoomSQL = "UPDATE rooms SET isOccupied = true WHERE roomId = ?";
                        updateRoomStatement = connection.prepareStatement(updateRoomSQL);
                        updateRoomStatement.setInt(1, availableRoomId);
                        updateRoomStatement.executeUpdate();

                        String updatePatientSQL = "UPDATE patients SET roomId = ? WHERE nationalID = ?";
                        PreparedStatement updatePatientStatement = connection.prepareStatement(updatePatientSQL);
                        updatePatientStatement.setInt(1, availableRoomId);
                        updatePatientStatement.setString(2, nationalID);
                        updatePatientStatement.executeUpdate();

                        ReportFile.logMessage("Room reassigned successfully for patient.");
                    } else {
                        String deletePatientSQL = "DELETE FROM patients WHERE nationalID = ?";
                        deletePatientStatement = connection.prepareStatement(deletePatientSQL);
                        deletePatientStatement.setString(1, nationalID);
                        deletePatientStatement.executeUpdate();

                        ReportFile.logMessage("No available room with the same type. Patient discharged and removed from the database.");
                    }
                }

                String deleteRoomSQL = "DELETE FROM rooms WHERE roomId = ?";
                deleteRoomStatement = connection.prepareStatement(deleteRoomSQL);
                deleteRoomStatement.setInt(1, roomId);
                deleteRoomStatement.executeUpdate();

                ReportFile.logMessage("Room deleted successfully.");
            } else {
                ReportFile.logMessage("No room found with the specified roomId.");
            }

        } catch (SQLException e) {
            ReportFile.logMessage(e.getMessage());
        } finally {
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
                ReportFile.logMessage(e.getMessage());
            }
        }
    }
    public static void updateRoomType(int roomId, String newType) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnection.getConnection();

            String sql = "UPDATE rooms SET type = ? WHERE roomId = ?";

            statement = connection.prepareStatement(sql);

            statement.setString(1, newType);
            statement.setInt(2, roomId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                ReportFile.logMessage("Room type updated successfully.");
            } else {
                ReportFile.logMessage("No room found with the specified roomId.");
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
    public static void readRoom(DefaultTableModel tableModel) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();

            String query = "SELECT * FROM rooms";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                int roomId = rs.getInt("roomid");
                String roomType=rs.getString("type");
                int occupied=rs.getInt("isOccupied");

                Object[] row = {roomId,roomType ,occupied};
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

    public static ArrayList<Room> getAvailableRooms() {
        ArrayList<Room> availableRooms = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            String query = "SELECT roomId, type, isOccupied FROM rooms WHERE isOccupied = 0";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                String roomId = rs.getString("roomId"); // Use String for roomId
                String type = rs.getString("type");
                boolean isOccupied = rs.getBoolean("isOccupied");
                Room room = new Room(roomId, type, isOccupied);
                availableRooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return availableRooms;
    }



}
