package Frame;

import javax.swing.*;
import java.awt.*;

public class CustomLoginGUI extends JFrame {

    public CustomLoginGUI() {
        setTitle("Hospital Management System - Login");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Background panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("path/to/your/image.jpg"); // مسیر تصویر
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout()); // برای قرار دادن اجزا در مرکز

        // Login panel
        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        loginPanel.setOpaque(false); // شفاف‌سازی پس‌زمینه

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Log In");

        loginPanel.add(userLabel);
        loginPanel.add(userField);
        loginPanel.add(passLabel);
        loginPanel.add(passField);
        loginPanel.add(new JLabel()); // فضای خالی
        loginPanel.add(loginButton);

        // Add to background panel
        backgroundPanel.add(loginPanel);

        // Add to frame
        add(backgroundPanel);

        // Button action
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            if (username.equals("admin") && password.equals("admin123")) {
                JOptionPane.showMessageDialog(null, "Login successful!");
                new MainMenu(); // انتقال به منوی اصلی
                dispose(); // بستن صفحه لاگین
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password.");
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomLoginGUI());
    }
}
