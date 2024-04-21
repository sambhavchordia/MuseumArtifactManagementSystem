import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

// Add exhibition page form class
public class AddExhibitionPage extends JFrame {

    public AddExhibitionPage() {
        setTitle("Add Exhibition");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose this frame only
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    // Initialize components
    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // Grid layout with 2 columns and spacing

        // Labels and text fields for exhibition details
        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField();
        JLabel themeLabel = new JLabel("Theme:");
        JTextField themeField = new JTextField();
        JLabel dateOpenedLabel = new JLabel("Date Opened (YYYY-MM-DD):");
        JTextField dateOpenedField = new JTextField();
        JLabel dateClosedLabel = new JLabel("Date Closed (YYYY-MM-DD):");
        JTextField dateClosedField = new JTextField();
        JLabel locationIDLabel = new JLabel("Location ID:");
        JTextField locationIDField = new JTextField();
        JLabel curatorIDLabel = new JLabel("Curator ID:");
        JTextField curatorIDField = new JTextField();
        JLabel budgetLabel = new JLabel("Budget:");
        JTextField budgetField = new JTextField();

        mainPanel.add(titleLabel);
        mainPanel.add(titleField);
        mainPanel.add(themeLabel);
        mainPanel.add(themeField);
        mainPanel.add(dateOpenedLabel);
        mainPanel.add(dateOpenedField);
        mainPanel.add(dateClosedLabel);
        mainPanel.add(dateClosedField);
        mainPanel.add(locationIDLabel);
        mainPanel.add(locationIDField);
        mainPanel.add(curatorIDLabel);
        mainPanel.add(curatorIDField);
        mainPanel.add(budgetLabel);
        mainPanel.add(budgetField);

        // Button to add the exhibition
        JButton addButton = new JButton("Add Exhibition");
        addButton.addActionListener(this::handleAddExhibition);
        mainPanel.add(addButton);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(this::handleBack);
        mainPanel.add(backButton);

        getContentPane().add(mainPanel);
    }

    // Handle button click event to add exhibition
    private void handleAddExhibition(ActionEvent e) {
        // Implement logic to add exhibition to the database
    }

    // Handle button click event to go back
    private void handleBack(ActionEvent e) {
        // Close this frame and show the home page
        dispose();
        new HomePageForm().setVisible(true);
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AddExhibitionPage().setVisible(true);
        });
    }
}
