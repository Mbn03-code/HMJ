package Frame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PatientGUI extends JFrame {

    private DefaultTableModel tableModel;

    public PatientGUI() {
        setTitle("Manage Patients");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table
        String[] columns = {"National ID", "Name", "Last Name", "Age", "Gender", "Phone"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable patientTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(patientTable);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Patient");
        JButton removeButton = new JButton("Remove Patient");
        JButton updateButton = new JButton("Update Patient");
        JButton viewButton = new JButton("View Patients");
        JButton searchButton = new JButton("Search by National ID");

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(searchButton);

        // Add components to frame
        setLayout(new BorderLayout(10, 10));
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(e -> addPatient());
        removeButton.addActionListener(e -> removePatient(patientTable));
        updateButton.addActionListener(e -> updatePatient(patientTable));
        viewButton.addActionListener(e -> viewPatients());
        searchButton.addActionListener(e -> searchPatientById());

        setVisible(true);
    }

    private void addPatient() {
        String nationalID = JOptionPane.showInputDialog("Enter National ID:");
        String name = JOptionPane.showInputDialog("Enter Name:");
        String lastName = JOptionPane.showInputDialog("Enter Last Name:");
        String age = JOptionPane.showInputDialog("Enter Age:");
        String gender = JOptionPane.showInputDialog("Enter Gender:");
        String phone = JOptionPane.showInputDialog("Enter Phone:");

        if (nationalID != null && name != null && lastName != null && age != null && gender != null && phone != null) {
            tableModel.addRow(new Object[]{nationalID, name, lastName, age, gender, phone});
        }
    }

    private void removePatient(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(null, "No patient selected!");
        }
    }

    private void updatePatient(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String nationalID = JOptionPane.showInputDialog("Enter new National ID:", tableModel.getValueAt(selectedRow, 0));
            String name = JOptionPane.showInputDialog("Enter new Name:", tableModel.getValueAt(selectedRow, 1));
            String lastName = JOptionPane.showInputDialog("Enter new Last Name:", tableModel.getValueAt(selectedRow, 2));
            String age = JOptionPane.showInputDialog("Enter new Age:", tableModel.getValueAt(selectedRow, 3));
            String gender = JOptionPane.showInputDialog("Enter new Gender:", tableModel.getValueAt(selectedRow, 4));
            String phone = JOptionPane.showInputDialog("Enter new Phone:", tableModel.getValueAt(selectedRow, 5));

            tableModel.setValueAt(nationalID, selectedRow, 0);
            tableModel.setValueAt(name, selectedRow, 1);
            tableModel.setValueAt(lastName, selectedRow, 2);
            tableModel.setValueAt(age, selectedRow, 3);
            tableModel.setValueAt(gender, selectedRow, 4);
            tableModel.setValueAt(phone, selectedRow, 5);
        } else {
            JOptionPane.showMessageDialog(null, "No patient selected!");
        }
    }

    private void viewPatients() {
        JOptionPane.showMessageDialog(null, "Displaying all patients in the table.");
    }

    private void searchPatientById() {
        String nationalID = JOptionPane.showInputDialog("Enter National ID to search:");
        boolean found = false;

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(nationalID)) {
                JOptionPane.showMessageDialog(null, "Patient found: " +
                        "\nName: " + tableModel.getValueAt(i, 1) +
                        "\nLast Name: " + tableModel.getValueAt(i, 2) +
                        "\nAge: " + tableModel.getValueAt(i, 3) +
                        "\nGender: " + tableModel.getValueAt(i, 4) +
                        "\nPhone: " + tableModel.getValueAt(i, 5));
                found = true;
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "Patient not found!");
        }
    }

}
