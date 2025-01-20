package Frame;

import Database.PatientDB;
import File.ReportFile;
import MainClasses.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagePatientsPanel {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JPanel cardContainer;

    public ManagePatientsPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
        this.cardLayout = new CardLayout();
        this.cardContainer = new JPanel(cardLayout);

        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());

        // Create buttons for patient management
        JPanel menuPanel = createMenuPanel();
        cardContainer.add(menuPanel, "Menu");

        // Add more panels for different actions (add, update, etc.)
        cardContainer.add(createAddPatientPanel(), "AddPatient");
        cardContainer.add(createRemovePatientPanel(), "RemovePatient");
        cardContainer.add(createUpdatePatientPanel(), "UpdatePatient");
        cardContainer.add(createViewPatientsPanel(), "ViewPatients");
        cardContainer.add(createSearchPatientPanel(), "SearchPatient");

        mainPanel.add(cardContainer, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(new Color(230, 240, 250)); // Light blue background for the panel

        // Title
        JLabel label = new JLabel("Patient Management Menu", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(0, 102, 204)); // Dark blue color for the title
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add padding around the title
        menuPanel.add(label, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        buttonPanel.setBackground(Color.WHITE); // White background for the form
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the buttons

        // Create buttons for patient management
        JButton addPatientButton = new JButton("Add Patient");
        JButton removePatientButton = new JButton("Remove Patient");
        JButton updatePatientButton = new JButton("Update Patient");
        JButton viewPatientsButton = new JButton("View Patients");
        JButton searchPatientsButton = new JButton("Search Patients by National ID");
        JButton backButton = new JButton("Back to Menu");

        // Styling buttons
        styleButton(addPatientButton);
        styleButton(removePatientButton);
        styleButton(updatePatientButton);
        styleButton(viewPatientsButton);
        styleButton(searchPatientsButton);
        styleButton(backButton);

        // Add buttons to the panel
        buttonPanel.add(addPatientButton);
        buttonPanel.add(removePatientButton);
        buttonPanel.add(updatePatientButton);
        buttonPanel.add(viewPatientsButton);
        buttonPanel.add(searchPatientsButton);
        buttonPanel.add(backButton);

        // Action listeners for each button
        addPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "AddPatient");
            }
        });

        removePatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "RemovePatient");
            }
        });

        updatePatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "UpdatePatient");
            }
        });

        viewPatientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "ViewPatients");
            }
        });

        searchPatientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "SearchPatient");
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

    private void styleButton(JButton button) {
        button.setBackground(new Color(0, 153, 76)); // Green color
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
    }


    private JPanel createAddPatientPanel() {
        JPanel addPatientPanel = new JPanel(new BorderLayout());
        addPatientPanel.setBackground(new Color(230, 240, 250)); // Light blue background for the panel

        // Title
        JLabel label = new JLabel("Add a New Patient", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(0, 102, 204)); // Dark blue color for the title
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add padding around the title
        addPatientPanel.add(label, BorderLayout.NORTH);

        // Form Panel with fields
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        formPanel.setBackground(Color.WHITE); // White background for the form
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the form

        // Set default field dimensions
        Dimension fieldDimension = new Dimension(200, 30);

        // Fields for the form
        formPanel.add(new JLabel("National ID:"));
        JTextField nationalIdField = new JTextField();
        nationalIdField.setPreferredSize(fieldDimension);
        formPanel.add(nationalIdField);

        formPanel.add(new JLabel("First Name:"));
        JTextField firstNameField = new JTextField();
        firstNameField.setPreferredSize(fieldDimension);
        formPanel.add(firstNameField);

        formPanel.add(new JLabel("Last Name:"));
        JTextField lastNameField = new JTextField();
        lastNameField.setPreferredSize(fieldDimension);
        formPanel.add(lastNameField);

        formPanel.add(new JLabel("Age:"));
        JTextField ageField = new JTextField();
        ageField.setPreferredSize(fieldDimension);
        formPanel.add(ageField);

        formPanel.add(new JLabel("Gender:"));
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Male", "Female"});
        genderComboBox.setPreferredSize(fieldDimension);
        formPanel.add(genderComboBox);

        formPanel.add(new JLabel("Phone:"));
        JTextField phoneField = new JTextField();
        phoneField.setPreferredSize(fieldDimension);
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Room Type:"));
        JComboBox<String> roomTypeComboBox = new JComboBox<>(new String[]{"ICU", "GENERAL", "PRIVATE", "SURGICAL", "EMERGENCY", "RECOVERY"});
        roomTypeComboBox.setPreferredSize(fieldDimension);
        formPanel.add(roomTypeComboBox);

        formPanel.add(new JLabel("Admission Date (YYYY-MM-DD):"));
        JTextField admissionDateField = new JTextField();
        admissionDateField.setPreferredSize(fieldDimension);
        formPanel.add(admissionDateField);

        formPanel.add(new JLabel("Discharge Date (YYYY-MM-DD):"));
        JTextField dischargeDateField = new JTextField();
        dischargeDateField.setPreferredSize(fieldDimension);
        formPanel.add(dischargeDateField);

        addPatientPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.setBackground(new Color(230, 240, 250)); // Same background color as the main panel

        // Submit Button
        JButton submitButton = new JButton("Add Patient");
        submitButton.setBackground(new Color(0, 153, 76)); // Green color
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setFocusPainted(false);

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
                String roomType = (String) roomTypeComboBox.getSelectedItem();
                String admissionDate = admissionDateField.getText();
                String dischargeDate = dischargeDateField.getText();

                // Add patient to the database
                Patient patient = new Patient(nationalId, firstName, lastName, age, gender, phone, admissionDate, dischargeDate);
                PatientDB.addPatient(patient, roomType);
            }
        });

        // Back Button
        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(new Color(204, 0, 0)); // Red color
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFocusPainted(false);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "Menu");
            }
        });

        buttonsPanel.add(submitButton);
        buttonsPanel.add(backButton);
        addPatientPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return addPatientPanel;
    }



    private JPanel createRemovePatientPanel() {
        JPanel removePatientPanel = new JPanel(new BorderLayout());
        removePatientPanel.setBackground(new Color(230, 240, 250)); // Light blue background for the panel

        // Title
        JLabel label = new JLabel("Remove a Patient", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(0, 102, 204)); // Dark blue color for the title
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add padding around the title
        removePatientPanel.add(label, BorderLayout.NORTH);

        // Form Panel with National ID field
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Use FlowLayout for better control
        formPanel.setBackground(Color.WHITE); // White background for the form
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the form

        JLabel idLabel = new JLabel("National ID:");
        formPanel.add(idLabel);

        JTextField nationalIdField = new JTextField();
        nationalIdField.setPreferredSize(new Dimension(200, 30)); // Smaller size for the field
        formPanel.add(nationalIdField);

        removePatientPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.setBackground(new Color(230, 240, 250)); // Same background color as the main panel

        // Submit Button
        JButton submitButton = new JButton("Remove Patient");
        submitButton.setBackground(new Color(255, 87, 34)); // Orange color
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setFocusPainted(false);
        submitButton.setPreferredSize(new Dimension(200, 40));

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nationalId = nationalIdField.getText();
                if (nationalId.isEmpty()) {
                    JOptionPane.showMessageDialog(removePatientPanel, "Please enter a National ID.");
                    return;
                }
                PatientDB.removePatient(nationalId);
            }
        });

        // Back Button
        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(new Color(204, 0, 0)); // Red color for back button
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(120, 40));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "Menu"); // Show the menu card
            }
        });

        buttonsPanel.add(submitButton);
        buttonsPanel.add(backButton);

        removePatientPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return removePatientPanel;
    }




    private JPanel createUpdatePatientPanel() {
        JPanel updatePatientPanel = new JPanel(new BorderLayout());
        updatePatientPanel.setBackground(new Color(230, 240, 250)); // Light blue background for the panel

        // Title
        JLabel label = new JLabel("Update Patient Information", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(0, 102, 204)); // Dark blue color for the title
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add padding around the title
        updatePatientPanel.add(label, BorderLayout.NORTH);

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
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Male", "Female"});
        formPanel.add(genderComboBox);

        formPanel.add(new JLabel("Phone:"));
        JTextField phoneField = new JTextField();
        formPanel.add(phoneField);

        updatePatientPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.setBackground(new Color(230, 240, 250)); // Same background color as the main panel

        // Submit Button
        JButton submitButton = new JButton("Update Patient");
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

                PatientDB.updatePatientDetails(nationalId, firstName, lastName, age, gender, phone);
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

        updatePatientPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return updatePatientPanel;
    }


    private JPanel createViewPatientsPanel() {
        JPanel viewPatientsPanel = new JPanel(new BorderLayout());
        viewPatientsPanel.setBackground(new Color(230, 240, 250)); // Light blue background for the panel

        // Title
        JLabel label = new JLabel("View All Patients", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(0, 102, 204)); // Dark blue color for the title
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add padding around the title
        viewPatientsPanel.add(label, BorderLayout.NORTH);

        // Create a JTable to display the patients' data
        String[] columns = {"National ID", "First Name", "Last Name", "Age", "Gender", "Phone", "Room ID", "Admission Date", "Discharge Date"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable patientTable = new JTable(tableModel);

        // Load patients' data from the database into the JTable
        PatientDB.readAllPatients(tableModel);

        // Scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(patientTable);
        viewPatientsPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.setBackground(new Color(230, 240, 250)); // Same background color as the main panel

        // Back Button
        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(new Color(204, 0, 0)); // Red color for the back button
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(200, 40));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "Menu"); // Show the menu card
            }
        });

        buttonsPanel.add(backButton);
        viewPatientsPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return viewPatientsPanel;
    }



    private JPanel createSearchPatientPanel() {
        JPanel searchPatientPanel = new JPanel(new BorderLayout());
        searchPatientPanel.setBackground(new Color(230, 240, 250)); // Light blue background

        // Title
        JLabel label = new JLabel("Search Patient by National ID", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(0, 102, 204)); // Dark blue color for title
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Padding for title
        searchPatientPanel.add(label, BorderLayout.NORTH);

        // Form Panel for National ID input
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Use FlowLayout for better control
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding for the form

        JLabel idLabel = new JLabel("National ID:");
        formPanel.add(idLabel);

        JTextField nationalIdField = new JTextField();
        nationalIdField.setPreferredSize(new Dimension(200, 30)); // Set the size for the input field
        formPanel.add(nationalIdField);

        searchPatientPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.setBackground(new Color(230, 240, 250)); // Same background color

        // Search Button
        JButton searchButton = new JButton("Search");
        styleButton(searchButton, new Color(0, 153, 76)); // Green button style

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nationalId = nationalIdField.getText();
                if (nationalId.isEmpty()) {
                    JOptionPane.showMessageDialog(searchPatientPanel, "Please enter National ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Search for the patient
                Patient patient = PatientDB.searchPatientsByNationalId(nationalId);
                if (patient != null) {
                    // Show patient information in a dialog
                    String patientInfo = "National ID: " + patient.getNationalID() + "\n"
                            + "First Name: " + patient.getName() + "\n"
                            + "Last Name: " + patient.getLastName() + "\n"
                            + "Age: " + patient.getAge() + "\n"
                            + "Gender: " + patient.getGender() + "\n"
                            + "Phone: " + patient.getPhone() + "\n"
                            + "Room ID: " + patient.getRoom().getRoomID() + "\n"
                            + "Admission Date: " + patient.getAdmissionDate() + "\n"
                            + "Discharge Date: " + patient.getDischargeDate();

                    JOptionPane.showMessageDialog(searchPatientPanel, patientInfo, "Patient Information", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(searchPatientPanel, "No patient found with this National ID.", "Not Found", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Back Button
        JButton backButton = new JButton("Back to Menu");
        styleButton(backButton, new Color(204, 0, 0)); // Red button style
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "Menu"); // Navigate back to menu
            }
        });

        buttonsPanel.add(searchButton);
        buttonsPanel.add(backButton);

        searchPatientPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return searchPatientPanel;
    }


    // Helper method to style buttons
    private void styleButton(JButton button, Color backgroundColor) {
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

}

