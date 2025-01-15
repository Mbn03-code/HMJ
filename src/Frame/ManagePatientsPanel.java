package Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ManagePatientsPanel {
    public ManagePatientsPanel(JPanel mainPanel) {
        mainPanel.removeAll();
        mainPanel.setLayout(new GridLayout(6, 1, 10, 10));

        // Create buttons for patient management
        JButton addPatientButton = new JButton("Add Patient");
        JButton removePatientButton = new JButton("Remove Patient");
        JButton updatePatientButton = new JButton("Update Patient");
        JButton viewPatientsButton = new JButton("View Patients");
        JButton searchPatientsButton = new JButton("Search Patients by National ID");
        JButton backButton = new JButton("Back to Menu");

        // Add buttons to the panel
        mainPanel.add(addPatientButton);
        mainPanel.add(removePatientButton);
        mainPanel.add(updatePatientButton);
        mainPanel.add(viewPatientsButton);
        mainPanel.add(searchPatientsButton);
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
