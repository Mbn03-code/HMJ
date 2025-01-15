package Frame;

import javax.swing.*;
import java.awt.*;


public class LoginMenuFrame extends JFrame {
    private JPanel mainPanel;

    public LoginMenuFrame() {
        // Set up the frame
        setTitle("Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLayout(new BorderLayout());

        // Initialize the main panel
        mainPanel = new JPanel(new GridBagLayout());
        setupLoginPanel();

        // Add main panel to the frame
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void setupLoginPanel() {
        mainPanel.removeAll(); // Clear any existing content

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Username Label and TextField
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(usernameField, gbc);

        // Password Label and PasswordField
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(passwordField, gbc);

        // Login Button
        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        mainPanel.add(loginButton, gbc);

        // Add ActionListener to the button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Simple authentication logic
                if (username.equals("admin") && password.equals("password")) {
                    JOptionPane.showMessageDialog(LoginMenuFrame.this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    setupMenuPanel();
                } else {
                    JOptionPane.showMessageDialog(LoginMenuFrame.this, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Refresh the panel
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    void setupMenuPanel() {
        mainPanel.removeAll(); // Clear the login form

        mainPanel.setLayout(new GridLayout(3, 1, 10, 10));

        // Create menu buttons
        JButton managePatientsButton = new JButton("Manage Patients");
        JButton manageRoomsButton = new JButton("Manage Rooms");
        JButton manageStaffButton = new JButton("Manage Staff");

        // Add buttons to the panel
        mainPanel.add(managePatientsButton);
        mainPanel.add(manageRoomsButton);
        mainPanel.add(manageStaffButton);

        // Add action listeners for menu buttons
        managePatientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManagePatientsPanel(mainPanel);
            }
        });

        manageStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManageStaffPanel(mainPanel);
            }
        });

        // Refresh the panel
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void main(String[] args) {
        new LoginMenuFrame();
    }
}