import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;


// Exhibitions viewer form class
public class ExhibitionViewer extends JFrame {

    private JTextArea textArea;

    public ExhibitionViewer() {
        setTitle("Exhibitions Viewer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose this frame only
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    // Initialize components
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout()); // Border layout

        // Text area to display exhibition details
        textArea = new JTextArea();
        textArea.setEditable(false); // Read-only
        JScrollPane scrollPane = new JScrollPane(textArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(this::handleBack);
        mainPanel.add(backButton, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        
        // Display all exhibitions
        displayExhibitions();
    }

    // Handle button click events
    private void handleBack(ActionEvent e) {
        // Close this frame and show the home page
        dispose();
        new HomePageForm().setVisible(true);
    }

    // Display all exhibitions
    private void displayExhibitions() {
        try {
            // Connect to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/museum", "root", "shlok160404");

            // Query to fetch all exhibitions
            String query = "SELECT * FROM Exhibitions";

            // Execute the query
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Display exhibitions in the text area
            StringBuilder exhibitionsText = new StringBuilder();
            exhibitionsText.append("Exhibitions:\n");
            while (resultSet.next()) {
                int exhibitionID = resultSet.getInt("ExhibitionID");
                String title = resultSet.getString("Title");
                String theme = resultSet.getString("Theme");
                Date dateOpened = resultSet.getDate("DateOpened");
                Date dateClosed = resultSet.getDate("DateClosed");
                int locationID = resultSet.getInt("LocationID");
                int curatorID = resultSet.getInt("CuratorID");
                double budget = resultSet.getDouble("Budget");

                exhibitionsText.append("Exhibition ID: ").append(exhibitionID).append("\n");
                exhibitionsText.append("Title: ").append(title).append("\n");
                exhibitionsText.append("Theme: ").append(theme).append("\n");
                exhibitionsText.append("Date Opened: ").append(dateOpened).append("\n");
                exhibitionsText.append("Date Closed: ").append(dateClosed).append("\n");
                exhibitionsText.append("Location ID: ").append(locationID).append("\n");
                exhibitionsText.append("Curator ID: ").append(curatorID).append("\n");
                exhibitionsText.append("Budget: ").append(budget).append("\n");
                exhibitionsText.append("-------------------------\n");
            }

            // Close the database connection
            resultSet.close();
            statement.close();
            connection.close();

            // Set the text in the text area
            textArea.setText(exhibitionsText.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ExhibitionViewer().setVisible(true);
        });
    }
}
