package Frame;

import Database.PatientDB;
import Database.StaffDB;
import File.ReportFile;
import MainClasses.Patient;
import MainClasses.Staff;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageStaffPanel {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JPanel cardContainer;

    public ManageStaffPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
        this.cardLayout = new CardLayout();
        this.cardContainer = new JPanel(cardLayout);

        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());

        // Create buttons for patient management
        JPanel menuPanel = createMenuPanel();
        cardContainer.add(menuPanel, "Menu");

        // Add more panels for different actions (add, update, etc.)
        cardContainer.add(createAddStaffPanel(), "AddStaff");
        cardContainer.add(createRemoveStaffPanel(), "RemoveStaff");
        cardContainer.add(createUpdateStaffPanel(), "UpdateStaff");
        cardContainer.add(createViewStaffPanel(), "ViewStaff");
        cardContainer.add(createSearchStaffPanel(), "SearchStaff");

        mainPanel.add(cardContainer, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(new Color(230, 240, 250)); // Light blue background for the panel

        // Title
        JLabel label = new JLabel("Staff Management Menu", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(0, 102, 204)); // Dark blue color for the title
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add padding around the title
        menuPanel.add(label, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        buttonPanel.setBackground(Color.WHITE); // White background for the form
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the buttons

        // Create buttons for staff management
        JButton addStaffButton = new JButton("Add Staff");
        JButton removeStaffButton = new JButton("Remove Staff");
        JButton updateStaffButton = new JButton("Update Staff");
        JButton viewStaffButton = new JButton("View Staff");
        JButton searchStaffButton = new JButton("Search Staff by National ID");
        JButton backButton = new JButton("Back to Menu");

        // Styling buttons
        styleButton(addStaffButton);
        styleButton(removeStaffButton);
        styleButton(updateStaffButton);
        styleButton(viewStaffButton);
        styleButton(searchStaffButton);
        styleButton(backButton);

        // Add buttons to the panel
        buttonPanel.add(addStaffButton);
        buttonPanel.add(removeStaffButton);
        buttonPanel.add(updateStaffButton);
        buttonPanel.add(viewStaffButton);
        buttonPanel.add(searchStaffButton);
        buttonPanel.add(backButton);

        // Action listeners for each button
        addStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "AddStaff");
            }
        });

        removeStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "RemoveStaff");
            }
        });

        updateStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "UpdateStaff");
            }
        });

        viewStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "ViewStaff");
            }
        });

        searchStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "SearchStaff");
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginMenuFrame().setupMenuPanel();  // Navigate back to main menu
            }
        });

        menuPanel.add(buttonPanel, BorderLayout.CENTER);

        return menuPanel;
    }



    private JPanel createAddStaffPanel() {
        JPanel addStaffPanel = new JPanel(new BorderLayout());
        addStaffPanel.setBackground(new Color(230, 240, 250)); // Light blue background for the panel

        // Title
        JLabel label = new JLabel("Add a New Staff", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(0, 102, 204)); // Dark blue color for the title
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add padding around the title
        addStaffPanel.add(label, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        formPanel.setBackground(Color.WHITE); // White background for the form
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the form

        // Fields for the form
        formPanel.add(new JLabel("National ID:"));
        JTextField nationalIdField = new JTextField();
        formPanel.add(nationalIdField);

        formPanel.add(new JLabel("First Name:"));
        JTextField firstNameField = new JTextField();
        formPanel.add(firstNameField);

        formPanel.add(new JLabel("Last Name:"));
        JTextField lastNameField = new JTextField();
        formPanel.add(lastNameField);

        formPanel.add(new JLabel("Age:"));
        JTextField ageField = new JTextField();
        formPanel.add(ageField);

        formPanel.add(new JLabel("Gender:"));
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        formPanel.add(genderComboBox);

        formPanel.add(new JLabel("Phone:"));
        JTextField phoneField = new JTextField();
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Position:"));
        JTextField positionField = new JTextField();
        formPanel.add(positionField);

        formPanel.add(new JLabel("Salary:"));
        JTextField salaryField = new JTextField();
        formPanel.add(salaryField);

        addStaffPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.setBackground(new Color(230, 240, 250)); // Same background color as the main panel

        // Submit Button
        JButton submitButton = new JButton("Add Staff");
        submitButton.setBackground(new Color(0, 153, 76)); // Green color
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setFocusPainted(false);

        // Back Button
        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(new Color(204, 0, 0)); // Red color
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFocusPainted(false);

        // Action Listeners
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get input values from the fields
                String nationalId = nationalIdField.getText();
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String age = ageField.getText();
                String gender = (String) genderComboBox.getSelectedItem();
                String phone = phoneField.getText();
                String position = positionField.getText();
                String salary = salaryField.getText();

                // Now we will call a method to add the staff to the database
                Staff staff = new Staff(nationalId, firstName, lastName, age, gender, phone, position, salary);
                StaffDB.addStaff(staff);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "Menu"); // Show the menu card
            }
        });

        buttonsPanel.add(submitButton);
        buttonsPanel.add(backButton);

        addStaffPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return addStaffPanel;
    }



    private JPanel createRemoveStaffPanel() {
        JPanel removeStaffPanel = new JPanel(new BorderLayout());
        removeStaffPanel.setBackground(new Color(230, 240, 250)); // Light blue background for the panel

        // Title
        JLabel label = new JLabel("Remove a Staff", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(0, 102, 204)); // Dark blue color for the title
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add padding around the title
        removeStaffPanel.add(label, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Use FlowLayout for better control
        formPanel.setBackground(Color.WHITE); // White background for the form
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the form

        // National ID field
        JLabel idLabel = new JLabel("National ID:");
        formPanel.add(idLabel);

        JTextField nationalIdField = new JTextField();
        nationalIdField.setPreferredSize(new Dimension(200, 30)); // Set the size of the input field
        formPanel.add(nationalIdField);

        removeStaffPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.setBackground(new Color(230, 240, 250)); // Same background color as the main panel

        // Submit Button
        JButton submitButton = new JButton("Remove Staff");
        submitButton.setBackground(new Color(0, 153, 76)); // Green color
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setFocusPainted(false);
        submitButton.setPreferredSize(new Dimension(150, 40)); // Adjust button size

        // Back Button
        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(new Color(204, 0, 0)); // Red color
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(150, 40)); // Adjust button size

        // Action Listeners
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nationalId = nationalIdField.getText();
                if (nationalId.isEmpty()) {
                    JOptionPane.showMessageDialog(removeStaffPanel, "Please enter a National ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                StaffDB.removeStaff(nationalId); // Logic to remove the staff
                JOptionPane.showMessageDialog(removeStaffPanel, "Staff removed successfully.");
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "Menu"); // Show the menu card
            }
        });

        buttonsPanel.add(submitButton);
        buttonsPanel.add(backButton);

        removeStaffPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return removeStaffPanel;
    }



    private JPanel createUpdateStaffPanel() {
        JPanel updateStaffPanel = new JPanel(new BorderLayout());
        updateStaffPanel.setBackground(new Color(230, 240, 250)); // Light blue background for the panel

        // Title
        JLabel label = new JLabel("Update Staff Information", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(0, 102, 204)); // Dark blue color for the title
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add padding around the title
        updateStaffPanel.add(label, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        formPanel.setBackground(Color.WHITE); // White background for the form
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the form

        // Fields for the form
        formPanel.add(new JLabel("National ID:"));
        JTextField nationalIdField = new JTextField();
        formPanel.add(nationalIdField);

        formPanel.add(new JLabel("First Name:"));
        JTextField firstNameField = new JTextField();
        formPanel.add(firstNameField);

        formPanel.add(new JLabel("Last Name:"));
        JTextField lastNameField = new JTextField();
        formPanel.add(lastNameField);

        formPanel.add(new JLabel("Age:"));
        JTextField ageField = new JTextField();
        formPanel.add(ageField);

        formPanel.add(new JLabel("Gender:"));
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        formPanel.add(genderComboBox);

        formPanel.add(new JLabel("Phone:"));
        JTextField phoneField = new JTextField();
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Position:"));
        JTextField positionField = new JTextField();
        formPanel.add(positionField);

        formPanel.add(new JLabel("Salary:"));
        JTextField salaryField = new JTextField();
        formPanel.add(salaryField);

        updateStaffPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.setBackground(new Color(230, 240, 250)); // Same background color as the main panel

        // Submit Button
        JButton submitButton = new JButton("Update Staff");
        submitButton.setBackground(new Color(0, 153, 76)); // Green color
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setFocusPainted(false);

        // Back Button
        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(new Color(204, 0, 0)); // Red color
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFocusPainted(false);

        // Action Listeners
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nationalId = nationalIdField.getText();
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String age = ageField.getText();
                String gender = (String) genderComboBox.getSelectedItem();
                String phone = phoneField.getText();
                String position = positionField.getText();
                String salary = salaryField.getText();

                // Call method to update staff data
                StaffDB.updateStaffDetails(nationalId, firstName, lastName, age, gender, phone, position, salary);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "Menu"); // Show the menu card
            }
        });

        buttonsPanel.add(submitButton);
        buttonsPanel.add(backButton);

        updateStaffPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return updateStaffPanel;
    }

    private JPanel createViewStaffPanel() {
        JPanel viewStaffPanel = new JPanel(new BorderLayout());
        viewStaffPanel.setBackground(new Color(230, 240, 250)); // Light blue background for the panel

        // Title
        JLabel label = new JLabel("View All Staff", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(0, 102, 204)); // Dark blue color for the title
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add padding around the title
        viewStaffPanel.add(label, BorderLayout.NORTH);

        // Create a JTable to display the staff's data
        String[] columns = {"National ID", "First Name", "Last Name", "Age", "Gender", "Phone", "Position", "Salary"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable staffTable = new JTable(tableModel);

        // Load staff data from the database into the JTable
        StaffDB.readAllStaff(tableModel);

        // Scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(staffTable);
        viewStaffPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.setBackground(new Color(230, 240, 250)); // Same background color as the main panel

        // Back Button
        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(new Color(204, 0, 0)); // Red color
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFocusPainted(false);

        // Action Listener for the back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "Menu"); // Show the menu card
            }
        });

        buttonsPanel.add(backButton);
        viewStaffPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return viewStaffPanel;
    }

    private JPanel createSearchStaffPanel() {
        JPanel searchStaffPanel = new JPanel(new BorderLayout());
        searchStaffPanel.setBackground(new Color(230, 240, 250)); // Light blue background

        // Title
        JLabel label = new JLabel("Search Staff by National ID", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(0, 102, 204)); // Dark blue color for title
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Padding for title
        searchStaffPanel.add(label, BorderLayout.NORTH);

        // Form Panel for National ID input
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Use FlowLayout for better control
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding for the form

        JLabel idLabel = new JLabel("National ID:");
        formPanel.add(idLabel);

        JTextField nationalIdField = new JTextField();
        nationalIdField.setPreferredSize(new Dimension(200, 30)); // Set the size of the input field
        formPanel.add(nationalIdField);

        searchStaffPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.setBackground(new Color(230, 240, 250)); // Same background color

        // Search Button
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(0, 153, 76)); // Green button style
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setFocusPainted(false);
        searchButton.setPreferredSize(new Dimension(150, 40)); // Adjust button size

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nationalId = nationalIdField.getText();
                if (nationalId.isEmpty()) {
                    JOptionPane.showMessageDialog(searchStaffPanel, "Please enter National ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Search for the staff
                Staff staff = StaffDB.searchStaffByNationalId(nationalId);
                if (staff != null) {
                    // Show staff information in a dialog
                    String staffInfo = "National ID: " + staff.getNationalID() + "\n"
                            + "First Name: " + staff.getName() + "\n"
                            + "Last Name: " + staff.getLastName() + "\n"
                            + "Age: " + staff.getAge() + "\n"
                            + "Gender: " + staff.getGender() + "\n"
                            + "Phone: " + staff.getPhone() + "\n"
                            + "Position: " + staff.getPosition() + "\n"
                            + "Salary: " + staff.getSalary();

                    JOptionPane.showMessageDialog(searchStaffPanel, staffInfo, "Staff Information", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(searchStaffPanel, "No staff found with this National ID.", "Not Found", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Back Button
        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(new Color(204, 0, 0)); // Red button style
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(150, 40)); // Adjust button size

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "Menu"); // Navigate back to menu
            }
        });

        buttonsPanel.add(searchButton);
        buttonsPanel.add(backButton);

        searchStaffPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return searchStaffPanel;
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(0, 153, 76)); // Green color
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
    }
}

