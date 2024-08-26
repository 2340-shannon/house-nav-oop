import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InteractiveHouse {
    private int houseWidth;
    private int houseHeight;
    private int numberOfRooms;
    private int selectedRoom = -1; // Initially, no room is selected

    private JPanel housePanel;

    // Array of pastel colors
    private Color[] pastelColors = {
            new Color(255, 218, 185), // PeachPuff
            new Color(173, 216, 230), // LightBlue
            new Color(152, 251, 152), // PaleGreen
            new Color(255, 182, 193), // LightPink
            new Color(240, 230, 140) // Khaki
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InteractiveHouse interactiveHouse = new InteractiveHouse();
            interactiveHouse.createInteractiveUI();
        });
    }

    private void createInteractiveUI() {
        JFrame frame = new JFrame("Interactive House");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 1)); // Two rows

        JPanel inputRow1 = new JPanel();
        inputRow1.setLayout(new FlowLayout());

        JLabel widthLabel = new JLabel("Enter House Width: ");
        JTextField widthTextField = new JTextField(10);

        JLabel heightLabel = new JLabel("Enter House Height: ");
        JTextField heightTextField = new JTextField(10);

        JLabel roomsLabel = new JLabel("Enter Number of Rooms: ");
        JTextField roomsTextField = new JTextField(10);

        JButton createButton = new JButton("Create House");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    houseWidth = Integer.parseInt(widthTextField.getText());
                    houseHeight = Integer.parseInt(heightTextField.getText());
                    numberOfRooms = Integer.parseInt(roomsTextField.getText());
                    selectedRoom = -1; // Reset the selected room
                    createHouseUI(frame);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid numbers.");
                }
            }
        });

        inputRow1.add(widthLabel);
        inputRow1.add(widthTextField);
        inputRow1.add(heightLabel);
        inputRow1.add(heightTextField);
        inputRow1.add(roomsLabel);
        inputRow1.add(roomsTextField);
        inputRow1.add(createButton);

        JPanel inputRow2 = new JPanel();
        inputRow2.setLayout(new FlowLayout());

        // Add room selection input and navigation button
        JLabel selectRoomLabel = new JLabel("Select Room: ");
        JTextField roomSelectionTextField = new JTextField(10);
        JButton navigateButton = new JButton("Navigate");
        navigateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int roomNumber = Integer.parseInt(roomSelectionTextField.getText());
                    if (roomNumber >= 1 && roomNumber <= numberOfRooms) {
                        selectedRoom = roomNumber - 1; // Adjust for 0-based index
                        housePanel.repaint();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid room number.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid room number.");
                }
            }
        });

        JButton clearSelectionButton = new JButton("Clear Selection");
        clearSelectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedRoom = -1; // Clear the selected room
                housePanel.repaint();
            }
        });

        inputRow2.add(selectRoomLabel);
        inputRow2.add(roomSelectionTextField);
        inputRow2.add(navigateButton);
        inputRow2.add(clearSelectionButton);

        inputPanel.add(inputRow1);
        inputPanel.add(inputRow2);

        frame.add(inputPanel, BorderLayout.NORTH);

        frame.pack();
        frame.setVisible(true);
    }

    private void createHouseUI(JFrame frame) {
        // Create the house panel
        housePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawRect(10, 10, houseWidth, houseHeight);

                // Calculate the number of rows and columns based on the number of rooms
                int numRows = (int) Math.ceil(Math.sqrt(numberOfRooms));
                int numCols = (int) Math.ceil((double) numberOfRooms / numRows);

                // Calculate the size of each room
                int roomWidth = houseWidth / numCols;
                int roomHeight = houseHeight / numRows;

                String[] roomLabels = { "Kitchen", "Bedroom", "Bathroom", "Living Room", "Dining Room" };

                for (int i = 0; i < numberOfRooms; i++) {
                    int row = i / numCols;
                    int col = i % numCols;
                    int roomX = 10 + col * roomWidth;
                    int roomY = 10 + row * roomHeight;

                    // Assign a pastel color to each room
                    g.setColor(pastelColors[i % pastelColors.length]);
                    g.fillRect(roomX, roomY, roomWidth, roomHeight);

                    // Draw a border around the room
                    g.setColor(Color.BLACK);
                    g.drawRect(roomX, roomY, roomWidth, roomHeight);

                    if (i < roomLabels.length) {
                        // Use predefined labels for the first 5 rooms
                        String label = roomLabels[i];
                        drawCenteredText(g, label, roomX, roomY, roomWidth, roomHeight);
                    } else {
                        // For additional rooms, label them as "Room1," "Room2," and so on
                        String label = "Room" + (i - roomLabels.length + 1);
                        drawCenteredText(g, label, roomX, roomY, roomWidth, roomHeight);
                    }

                    // Highlight the selected room in red
                    if (i == selectedRoom) {
                        g.setColor(Color.RED);
                        g.fillRect(roomX, roomY, roomWidth, roomHeight);
                    }
                }
            }

            // Helper method to draw centered text
            private void drawCenteredText(Graphics g, String text, int x, int y, int width, int height) {
                FontMetrics fm = g.getFontMetrics();
                int textX = x + (width - fm.stringWidth(text)) / 2;
                int textY = y + (height - fm.getHeight()) / 2 + fm.getAscent();
                g.drawString(text, textX, textY);
            }
        };

        frame.add(housePanel, BorderLayout.CENTER);
        frame.setSize(houseWidth + 50, houseHeight + 50);
        frame.revalidate();
        frame.repaint();
    }
}
