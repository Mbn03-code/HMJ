package Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginMenuFrame extends JFrame {
    private JPanel mainPanel;

    public LoginMenuFrame() {
        // Set up the frame
        setTitle("Manage Hospital System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center the window on screen

        // Initialize the main panel
        mainPanel = new JPanel(new GridBagLayout());
        setupLoginPanel();

        // Add main panel to the frame
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void setupLoginPanel() {
        mainPanel.removeAll(); // Clear any existing content
        mainPanel.setBackground(new Color(245, 245, 245)); // Light gray background for the login panel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 30, 30, 30);

        // Username Label and TextField
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setForeground(new Color(51, 51, 51)); // Dark text color
        JTextField usernameField = new JTextField(15);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(usernameField, gbc);

        // Password Label and PasswordField
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setForeground(new Color(51, 51, 51)); // Dark text color
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(passwordField, gbc);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(0, 153, 0)); // Green color for the login button
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(150, 40));
        loginButton.setFocusPainted(false);
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
        mainPanel.setBackground(new Color(240, 240, 240)); // Light background for menu panel

        // Create menu buttons with new colors and fonts
        JButton managePatientsButton = new JButton("Manage Patients");
        styleButton(managePatientsButton);

        JButton manageRoomsButton = new JButton("Manage Rooms");
        styleButton(manageRoomsButton);

        JButton manageStaffButton = new JButton("Manage Staff");
        styleButton(manageStaffButton);

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

        manageRoomsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManageRoomsPanel(mainPanel);
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

    // Helper method to style buttons
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0, 100, 120)); // Blue color for buttons
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(10, 30)); // Smaller size for buttons
        button.setFocusPainted(false);
    }


    public static void main(String[] args) {
        new LoginMenuFrame();
    }
}
