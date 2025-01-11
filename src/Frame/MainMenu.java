package Frame;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Hospital Management System - Main Menu");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panels
        JPanel menuPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        JLabel titleLabel = new JLabel("Hospital Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JButton patientButton = new JButton("Manage Patients");
        JButton staffButton = new JButton("Manage Staff");
        JButton roomButton = new JButton("Manage Rooms");

        menuPanel.add(patientButton);
        menuPanel.add(staffButton);
        menuPanel.add(roomButton);

        // Footer
        JLabel footerLabel = new JLabel("© 2025 - Your Hospital Name", SwingConstants.CENTER);

        // Add to frame
        setLayout(new BorderLayout(10, 10));
        add(titleLabel, BorderLayout.NORTH);
        add(menuPanel, BorderLayout.CENTER);
        add(footerLabel, BorderLayout.SOUTH);

        // Button actions
        patientButton.addActionListener(e -> new PatientGUI());
        staffButton.addActionListener(e -> new StaffGUI());
        roomButton.addActionListener(e -> new RoomManagementGUI());

        setVisible(true);
    }
}
