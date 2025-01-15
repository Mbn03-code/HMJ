package Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ManageStaffPanel {
    public ManageStaffPanel(JPanel mainPanel) {
        mainPanel.removeAll();
        mainPanel.setLayout(new GridLayout(6, 1, 10, 10));

        // Create buttons for staff management
        JButton addStaffButton = new JButton("Add Staff");
        JButton removeStaffButton = new JButton("Remove Staff");
        JButton updateStaffButton = new JButton("Update Staff");
        JButton viewStaffButton = new JButton("View Staff");
        JButton searchStaffButton = new JButton("Search Staff by ID");
        JButton backButton = new JButton("Back to Menu");

        // Add buttons to the panel
        mainPanel.add(addStaffButton);
        mainPanel.add(removeStaffButton);
        mainPanel.add(updateStaffButton);
        mainPanel.add(viewStaffButton);
        mainPanel.add(searchStaffButton);
        mainPanel.add(backButton);

        // Action listener for back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginMenuFrame().setupMenuPanel();
            }
        });

        // Refresh the panel
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}