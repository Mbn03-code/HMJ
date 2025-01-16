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
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));  // Using BoxLayout for vertical layout

        // Create buttons for staff management
        JButton addStaffButton = new JButton("Add Staff");
        JButton removeStaffButton = new JButton("Remove Staff");
        JButton updateStaffButton = new JButton("Update Staff");
        JButton viewStaffButton = new JButton("View Staff");
        JButton searchStaffButton = new JButton("Search Staff by National ID");
        JButton backButton = new JButton("Back to Menu");

        // Styling buttons (Optional, to make them look similar)
        addStaffButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeStaffButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateStaffButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewStaffButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchStaffButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add buttons to the panel with some space between them
        menuPanel.add(addStaffButton);
        menuPanel.add(Box.createVerticalStrut(10));  // Add space between buttons
        menuPanel.add(removeStaffButton);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(updateStaffButton);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(viewStaffButton);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(searchStaffButton);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(backButton);

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

        return menuPanel;
    }


    private JPanel createAddStaffPanel() {
        JPanel addStaffPanel = new JPanel(new BorderLayout());

        // Title
        JLabel label = new JLabel("Add a New Staff");
        addStaffPanel.add(label, BorderLayout.NORTH);

        // Form Panel with fields
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));

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

        // Submit Button
        JPanel bottomPanel = new JPanel();
        JButton submitButton = new JButton("Add Staff");
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
        bottomPanel.add(submitButton);

        // Back Button to return to menu
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "Menu"); // Show the menu card
            }
        });
        bottomPanel.add(backButton);

        addStaffPanel.add(bottomPanel, BorderLayout.SOUTH);
        return addStaffPanel;
    }


    private JPanel createRemoveStaffPanel() {
        JPanel removeStaffPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Remove a Staff");
        removeStaffPanel.add(label, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(1, 2));
        formPanel.add(new JLabel("National ID:"));
        JTextField nationalIdField = new JTextField();
        formPanel.add(nationalIdField);

        removeStaffPanel.add(formPanel, BorderLayout.CENTER);

        // Submit button to remove the staff
        JPanel bottomPanel = new JPanel();
        JButton submitButton = new JButton("Remove Staff");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic to remove the staff based on the National ID
                String nationalId = nationalIdField.getText();
                StaffDB.removeStaff(nationalId);
            }
        });
        bottomPanel.add(submitButton);

        // Back Button to return to menu
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "Menu"); // Show the menu card
            }
        });
        bottomPanel.add(backButton);

        removeStaffPanel.add(bottomPanel, BorderLayout.SOUTH);
        return removeStaffPanel;
    }


    private JPanel createUpdateStaffPanel() {
        JPanel updateStaffPanel = new JPanel(new BorderLayout());

        // Title
        JLabel label = new JLabel("Update Staff Information");
        updateStaffPanel.add(label, BorderLayout.NORTH);

        // Form Panel with fields
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));

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

        // Submit Button to update staff information
        JPanel bottomPanel = new JPanel();
        JButton submitButton = new JButton("Update Staff");
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

                // Call method to update staff data
                StaffDB.updateStaffDetails(nationalId, firstName, lastName, age, gender, phone, position, salary);
            }
        });
        bottomPanel.add(submitButton);

        // Back Button to return to menu
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "Menu"); // Show the menu card
            }
        });
        bottomPanel.add(backButton);

        updateStaffPanel.add(bottomPanel, BorderLayout.SOUTH);
        return updateStaffPanel;
    }


    private JPanel createViewStaffPanel() {
        JPanel viewStaffPanel = new JPanel(new BorderLayout());

        // Title
        JLabel label = new JLabel("View All Staff");
        viewStaffPanel.add(label, BorderLayout.NORTH);

        // Create a JTable to display the patients' data
        String[] columns = {"National ID", "First Name", "Last Name", "Age", "Gender", "Phone", "position", "salary"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable StaffTable = new JTable(tableModel);

        // Load patients' data from the database into the JTable
        StaffDB.readAllStaff(tableModel);

        // Scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(StaffTable);
        viewStaffPanel.add(scrollPane, BorderLayout.CENTER);

        // Back Button to return to menu
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "Menu"); // Show the menu card
            }
        });

        // You can add the back button either to the South or in a different place as per your design preference.
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        viewStaffPanel.add(bottomPanel, BorderLayout.SOUTH);
        return viewStaffPanel;
    }


    private JPanel createSearchStaffPanel() {
        JPanel searchStaffPanel = new JPanel(new BorderLayout());

        // Title
        JLabel label = new JLabel("Search Staff by National ID");
        searchStaffPanel.add(label, BorderLayout.NORTH);

        // Form Panel with fields
        JPanel formPanel = new JPanel(new GridLayout(1, 2));
        formPanel.add(new JLabel("National ID:"));
        JTextField nationalIdField = new JTextField();
        formPanel.add(nationalIdField);

        searchStaffPanel.add(formPanel, BorderLayout.CENTER);

        // Result Panel
        JPanel resultPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        JLabel firstNameLabel = new JLabel("First Name: ");
        JLabel lastNameLabel = new JLabel("Last Name: ");
        JLabel ageLabel = new JLabel("Age: ");
        JLabel genderLabel = new JLabel("Gender: ");
        JLabel phoneLabel = new JLabel("Phone: ");
        JLabel positionLabel = new JLabel("position: ");
        JLabel salaryLabel = new JLabel("salary: ");

        resultPanel.add(firstNameLabel);
        resultPanel.add(lastNameLabel);
        resultPanel.add(ageLabel);
        resultPanel.add(genderLabel);
        resultPanel.add(phoneLabel);
        resultPanel.add(positionLabel);
        resultPanel.add(salaryLabel);


        searchStaffPanel.add(resultPanel, BorderLayout.SOUTH);

        // Search button to fetch patient data by National ID
        JButton searchButton = new JButton("Search Staff");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nationalId = nationalIdField.getText();
                if (!nationalId.isEmpty()) {
                    // Call method to search for patient by National ID
                    Staff staff = StaffDB.searchStaffByNationalId(nationalId);

                    if (staff != null) {
                        // Update result labels with patient data
                        firstNameLabel.setText("First Name: " + staff.getName());
                        lastNameLabel.setText("Last Name: " + staff.getLastName());
                        ageLabel.setText("Age: " + staff.getAge());
                        genderLabel.setText("Gender: " + staff.getGender());
                        phoneLabel.setText("Phone: " + staff.getPhone());
                        positionLabel.setText("position: " + staff.getPosition());
                        salaryLabel.setText("salary: " + staff.getSalary());
                    } else {
                        JOptionPane.showMessageDialog(searchStaffPanel, "No Staff found with National ID: " + nationalId);
                    }
                }
            }
        });

        searchStaffPanel.add(searchButton, BorderLayout.NORTH);
        // Back Button to return to menu
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "Menu"); // Show the menu card
            }
        });

        // You can add the back button either to the South or in a different place as per your design preference.
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        searchStaffPanel.add(bottomPanel, BorderLayout.SOUTH);

        return searchStaffPanel;
    }
}

