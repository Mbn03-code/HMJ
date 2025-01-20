package Frame;

import Database.RoomDB;
import MainClasses.Room;
import MainClasses.RoomType;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ManageRoomsPanel {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JPanel cardContainer;

    public ManageRoomsPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
        this.cardLayout = new CardLayout();
        this.cardContainer = new JPanel(cardLayout);

        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());

        // Create buttons for room management
        JPanel menuPanel = createMenuPanel();
        cardContainer.add(menuPanel, "Menu");

        // Add more panels for different actions (add, update, etc.)
        cardContainer.add(createAddRoomPanel(), "AddRoom");
        cardContainer.add(createRemoveRoomPanel(), "RemoveRoom");
        cardContainer.add(createUpdateRoomPanel(), "UpdateRoom");
        cardContainer.add(createViewRoomPanel(), "ViewRoom");
        cardContainer.add(createAvailableRoomPanel(), "AvailableRooms");

        mainPanel.add(cardContainer, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        menuPanel.setBackground(Color.LIGHT_GRAY); // رنگ پس زمینه پنل

        // Create buttons for room management
        JButton addRoomButton = new JButton("Add Room");
        JButton removeRoomButton = new JButton("Remove Room");
        JButton updateRoomButton = new JButton("Update Room Type");
        JButton viewRoomButton = new JButton("View Rooms");
        JButton availableRoomButton = new JButton("View Available Rooms");
        JButton backButton = new JButton("Back to Menu");

        // تنظیم رنگ و اندازه دکمه‌ها
        addRoomButton.setBackground(new Color(0, 153, 204));  // رنگ آبی برای دکمه‌ها
        removeRoomButton.setBackground(new Color(0, 153, 204));
        updateRoomButton.setBackground(new Color(0, 153, 204));
        viewRoomButton.setBackground(new Color(0, 153, 204));
        availableRoomButton.setBackground(new Color(0, 153, 204));
        backButton.setBackground(new Color(204, 0, 0));  // دکمه بازگشت با رنگ قرمز

        // تنظیم فونت دکمه‌ها
        Font font = new Font("Arial", Font.PLAIN, 16);
        addRoomButton.setFont(font);
        removeRoomButton.setFont(font);
        updateRoomButton.setFont(font);
        viewRoomButton.setFont(font);
        availableRoomButton.setFont(font);
        backButton.setFont(font);

        // تنظیم اندازه دکمه‌ها
        Dimension buttonSize = new Dimension(200, 50);
        addRoomButton.setPreferredSize(buttonSize);
        removeRoomButton.setPreferredSize(buttonSize);
        updateRoomButton.setPreferredSize(buttonSize);
        viewRoomButton.setPreferredSize(buttonSize);
        availableRoomButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);

        // Add buttons to the panel
        menuPanel.add(addRoomButton);
        menuPanel.add(removeRoomButton);
        menuPanel.add(updateRoomButton);
        menuPanel.add(viewRoomButton);
        menuPanel.add(availableRoomButton);
        menuPanel.add(backButton);

        // Action listeners for each button
        addRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "AddRoom");
            }
        });

        removeRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "RemoveRoom");
            }
        });

        updateRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "UpdateRoom");
            }
        });

        viewRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "ViewRoom");
            }
        });

        availableRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "AvailableRooms");
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginMenuFrame().setupMenuPanel(); // Navigate back to main menu
            }
        });

        return menuPanel;
    }

    private JPanel createAddRoomPanel() {
        JPanel addRoomPanel = new JPanel(new BorderLayout());

        // Title
        JLabel label = new JLabel("Add a New Room", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.BLUE); // تغییر رنگ عنوان
        addRoomPanel.add(label, BorderLayout.NORTH);

        // Form Panel with GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();

        // Label for Room Type
        JLabel roomTypeLabel = new JLabel("Room Type:");
        roomTypeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); // فاصله اطراف
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(roomTypeLabel, gbc);

        // Room Type Dropdown
        JComboBox<String> roomTypeComboBox = new JComboBox<>(new String[]{
                "ICU", "GENERAL", "PRIVATE", "SURGICAL", "EMERGENCY", "RECOVERY"
        });
        roomTypeComboBox.setPreferredSize(new Dimension(120, 25)); // کوچک‌تر کردن اندازه
        roomTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 12)); // تغییر فونت
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(roomTypeComboBox, gbc);

        addRoomPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Submit Button
        JButton submitButton = new JButton("Add Room");
        submitButton.setPreferredSize(new Dimension(120, 30)); // کوچک‌تر کردن دکمه
        submitButton.setBackground(new Color(0, 153, 76)); // سبز برای دکمه افزودن
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 12));
        submitButton.setFocusPainted(false);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get selected room type
                String roomType = (String) roomTypeComboBox.getSelectedItem();

                // Add room to the database
                Room room = new Room(RoomType.valueOf(roomType));
                RoomDB.addRoom(room);

                JOptionPane.showMessageDialog(addRoomPanel, "Room added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Back Button
        JButton backButton = new JButton("Back to Menu");
        backButton.setPreferredSize(new Dimension(120, 30)); // کوچک‌تر کردن دکمه
        backButton.setBackground(new Color(204, 0, 0)); // قرمز برای دکمه بازگشت
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        backButton.setFocusPainted(false);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "Menu");
            }
        });

        buttonsPanel.add(submitButton);
        buttonsPanel.add(backButton);

        addRoomPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return addRoomPanel;
    }

    private JPanel createRemoveRoomPanel() {
        JPanel removeRoomPanel = new JPanel(new BorderLayout());

        // Title
        JLabel label = new JLabel("Remove a Room", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.RED); // تغییر رنگ عنوان
        removeRoomPanel.add(label, BorderLayout.NORTH);

        // Form Panel with GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();

        // Label for Room ID
        JLabel roomIdLabel = new JLabel("Room ID:");
        roomIdLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); // فاصله اطراف
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(roomIdLabel, gbc);

        // Room ID Text Field
        JTextField roomIdField = new JTextField();
        roomIdField.setPreferredSize(new Dimension(120, 25)); // کوچک‌تر کردن اندازه
        roomIdField.setFont(new Font("Arial", Font.PLAIN, 12)); // تغییر فونت
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(roomIdField, gbc);

        removeRoomPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Submit Button
        JButton submitButton = new JButton("Remove Room");
        submitButton.setPreferredSize(new Dimension(120, 30)); // کوچک‌تر کردن دکمه
        submitButton.setBackground(new Color(255, 140, 0)); // نارنجی برای دکمه حذف
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 12));
        submitButton.setFocusPainted(false);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roomId = roomIdField.getText();
                try {
                    int roomIdInt = Integer.parseInt(roomId);
                    RoomDB.removeRoomAndReassign(roomIdInt);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(removeRoomPanel, "Invalid Room ID entered.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Back Button
        JButton backButton = new JButton("Back to Menu");
        backButton.setPreferredSize(new Dimension(120, 30)); // کوچک‌تر کردن دکمه
        backButton.setBackground(new Color(204, 0, 0)); // قرمز برای دکمه بازگشت
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        backButton.setFocusPainted(false);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "Menu");
            }
        });

        buttonsPanel.add(submitButton);
        buttonsPanel.add(backButton);

        removeRoomPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return removeRoomPanel;
    }

    private JPanel createUpdateRoomPanel() {
        JPanel updateRoomPanel = new JPanel(new BorderLayout());

        // Title
        JLabel label = new JLabel("Update Room Type", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.BLUE);
        updateRoomPanel.add(label, BorderLayout.NORTH);

        // Form Panel with GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();

        // Room ID Label and Field
        JLabel roomIdLabel = new JLabel("Room ID:");
        roomIdLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(roomIdLabel, gbc);

        JTextField roomIdField = new JTextField();
        roomIdField.setPreferredSize(new Dimension(120, 25));
        roomIdField.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(roomIdField, gbc);

        // New Room Type Label and ComboBox
        JLabel roomTypeLabel = new JLabel("New Room Type:");
        roomTypeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(roomTypeLabel, gbc);

        JComboBox<String> roomTypeComboBox = new JComboBox<>(new String[]{
                "ICU", "GENERAL", "PRIVATE", "SURGICAL", "EMERGENCY", "RECOVERY"
        });
        roomTypeComboBox.setPreferredSize(new Dimension(120, 25));
        roomTypeComboBox.setBackground(Color.LIGHT_GRAY);
        roomTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(roomTypeComboBox, gbc);

        updateRoomPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Submit Button
        JButton submitButton = new JButton("Update Room Type");
        submitButton.setPreferredSize(new Dimension(120, 30));
        submitButton.setBackground(new Color(34, 139, 34)); // رنگ سبز روشن برای دکمه به‌روزرسانی
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 12));
        submitButton.setFocusPainted(false);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roomId = roomIdField.getText();
                String roomType = (String) roomTypeComboBox.getSelectedItem();

                try {
                    int roomIdInt = Integer.parseInt(roomId);
                    RoomDB.updateRoomType(roomIdInt, roomType);
                    JOptionPane.showMessageDialog(updateRoomPanel, "Room type updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(updateRoomPanel, "Invalid Room ID entered.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Back Button
        JButton backButton = new JButton("Back to Menu");
        backButton.setPreferredSize(new Dimension(120, 30));
        backButton.setBackground(new Color(204, 0, 0)); // قرمز برای دکمه بازگشت
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        backButton.setFocusPainted(false);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "Menu");
            }
        });

        buttonsPanel.add(submitButton);
        buttonsPanel.add(backButton);

        updateRoomPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return updateRoomPanel;
    }

    private JPanel createViewRoomPanel() {
        JPanel viewRoomPanel = new JPanel(new BorderLayout());

        // Title Label
        JLabel label = new JLabel("View All Rooms", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(Color.BLUE);
        viewRoomPanel.add(label, BorderLayout.NORTH);

        // Table to Display Room Data
        String[] columns = {"Room ID", "Room Type", "Is Occupied"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        // Customize Table Appearance
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(Color.LIGHT_GRAY);
        table.getTableHeader().setForeground(Color.BLACK);

        // Fetch Data into Table
        RoomDB.readRoom(model);

        // Scroll Pane for Table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        viewRoomPanel.add(scrollPane, BorderLayout.CENTER);

        // Back Button
        JButton backButton = new JButton("Back to Menu");
        backButton.setPreferredSize(new Dimension(120, 30));
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        backButton.setFocusPainted(false);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardContainer, "Menu");
            }
        });

        // Buttons Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.add(backButton);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        viewRoomPanel.add(bottomPanel, BorderLayout.SOUTH);

        return viewRoomPanel;
    }

    private JPanel createAvailableRoomPanel() {
        JPanel availableRoomPanel = new JPanel(new BorderLayout());
        availableRoomPanel.setBackground(Color.LIGHT_GRAY); // Background color

        // Title
        JLabel label = new JLabel("Check Available Rooms", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16)); // Font style
        label.setForeground(Color.BLUE); // Font color
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Padding
        availableRoomPanel.add(label, BorderLayout.NORTH);

        // Display area for room details
        JTextArea roomDetailsArea = new JTextArea(10, 30);
        roomDetailsArea.setEditable(false);
        roomDetailsArea.setBackground(Color.WHITE);
        roomDetailsArea.setForeground(Color.BLACK);
        roomDetailsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        availableRoomPanel.add(new JScrollPane(roomDetailsArea), BorderLayout.CENTER);

        // Button to fetch available rooms
        JButton checkRoomButton = new JButton("Find Available Rooms");
        checkRoomButton.setBackground(Color.GREEN);
        checkRoomButton.setForeground(Color.WHITE);
        checkRoomButton.setFont(new Font("Arial", Font.BOLD, 14));
        checkRoomButton.addActionListener(e -> {
            // Fetch all available rooms
            ArrayList <Room>availableRooms = RoomDB.getAvailableRooms();
            if (!availableRooms.isEmpty()) {
                StringBuilder roomDetails = new StringBuilder("Available Rooms:\n");
                for (Room room : availableRooms) {
                    roomDetails.append(String.format(
                            "Room ID: %s | Type: %s | Occupied: %s\n",
                            room.getRoomID() != null ? room.getRoomID() : "N/A", // Room ID
                            room.getType() != null ? room.getType() : "N/A",     // Room Type
                            room.isOccupied() ? "Yes" : "No"                   // Occupied Status
                    ));
                }
                roomDetailsArea.setText(roomDetails.toString());
            } else {
                roomDetailsArea.setText("No available rooms at the moment.");
            }
        });

        // Back to menu button
        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> cardLayout.show(cardContainer, "Menu"));

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding for buttons
        buttonPanel.add(checkRoomButton, gbc);

        gbc.gridy = 1;
        buttonPanel.add(backButton, gbc);

        availableRoomPanel.add(buttonPanel, BorderLayout.SOUTH);

        return availableRoomPanel;
    }


}
