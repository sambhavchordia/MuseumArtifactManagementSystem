import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.sql.*;

// Auctions viewer form class
public class AuctionsViewer extends JFrame {

    private JTextArea textArea;

    public AuctionsViewer() {
        setTitle("Auctions Viewer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose this frame only
        initComponents();
        setSize(600, 400); // Set the window size
        setLocationRelativeTo(null); // Center the window on the screen

        // Call the method to display auctions
        displayAuctions();
    }

    // Initialize components
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout()); // Border layout

        // Text area to display auction details
        textArea = new JTextArea();
        textArea.setEditable(false); // Read-only
        JScrollPane scrollPane = new JScrollPane(textArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(this::handleBack);
        mainPanel.add(backButton, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }

    // Handle button click events
    private void handleBack(ActionEvent e) {
        // Close this frame and show the home page
        dispose();
        new HomePageForm().setVisible(true);
    }

    // Display all auctions
    private void displayAuctions() {
        try {
            // Connect to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/museum", "root", "shlok160404");

            // Query to fetch all auctions
            String query = "SELECT * FROM Auctions";

            // Execute the query
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Display auctions in the text area
            StringBuilder auctionsText = new StringBuilder();
            auctionsText.append("Auctions:\n");
            while (resultSet.next()) {
                int auctionID = resultSet.getInt("AuctionID");
                String auctionName = resultSet.getString("AuctionName");
                Date date = resultSet.getDate("Date");
                int locationID = resultSet.getInt("LocationID");
                int organizerID = resultSet.getInt("OrganizerID");
                double startPrice = resultSet.getDouble("StartPrice");
                double endPrice = resultSet.getDouble("EndPrice");
                double winningBid = resultSet.getDouble("WinningBid");

                auctionsText.append("Auction ID: ").append(auctionID).append("\n");
                auctionsText.append("Auction Name: ").append(auctionName).append("\n");
                auctionsText.append("Date: ").append(date).append("\n");
                auctionsText.append("Location ID: ").append(locationID).append("\n");
                auctionsText.append("Organizer ID: ").append(organizerID).append("\n");
                auctionsText.append("Start Price: ").append(startPrice).append("\n");
                auctionsText.append("End Price: ").append(endPrice).append("\n");
                auctionsText.append("Winning Bid: ").append(winningBid).append("\n");
                auctionsText.append("-------------------------\n");
            }

            // Close the database connection
            resultSet.close();
            statement.close();
            connection.close();

            // Set the text in the text area
            textArea.setText(auctionsText.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AuctionsViewer().setVisible(true);
        });
    }
}
