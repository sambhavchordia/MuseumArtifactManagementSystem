import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

// Add exhibition page form class
public class AddExhibitionPage extends JFrame {

    // Text fields as class-level variables to be accessed in event handlers
    private JTextField titleField = new JTextField();
    private JTextField themeField = new JTextField();
    private JTextField dateOpenedField = new JTextField();
    private JTextField dateClosedField = new JTextField();
    private JTextField locationIDField = new JTextField();
    private JTextField curatorIDField = new JTextField();
    private JTextField budgetField = new JTextField();

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

        mainPanel.add(new JLabel("Title:"));
        mainPanel.add(titleField);
        mainPanel.add(new JLabel("Theme:"));
        mainPanel.add(themeField);
        mainPanel.add(new JLabel("Date Opened (YYYY-MM-DD):"));
        mainPanel.add(dateOpenedField);
        mainPanel.add(new JLabel("Date Closed (YYYY-MM-DD):"));
        mainPanel.add(dateClosedField);
        mainPanel.add(new JLabel("Location ID:"));
        mainPanel.add(locationIDField);
        mainPanel.add(new JLabel("Curator ID:"));
        mainPanel.add(curatorIDField);
        mainPanel.add(new JLabel("Budget:"));
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
        String title = titleField.getText().trim();
        String theme = themeField.getText().trim();
        String dateOpened = dateOpenedField.getText().trim();
        String dateClosed = dateClosedField.getText().trim();
        String locationID = locationIDField.getText().trim();
        String curatorID = curatorIDField.getText().trim();
        String budget = budgetField.getText().trim();

        if (title.isEmpty() || theme.isEmpty() || dateOpened.isEmpty() || dateClosed.isEmpty()
            || locationID.isEmpty() || curatorID.isEmpty() || budget.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Database connection and operations
        try (Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/museum", "root", "shlok160404");
             PreparedStatement pstmt = conn.prepareStatement(
            "INSERT INTO exhibitions (Title, Theme, DateOpened, DateClosed, locationID, curatorID, Budget) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            pstmt.setString(1, title);
            pstmt.setString(2, theme);
            pstmt.setString(3, dateOpened);
            pstmt.setString(4, dateClosed);
            pstmt.setString(5, locationID);
            pstmt.setString(6, curatorID);
            pstmt.setDouble(7, Double.parseDouble(budget));

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Exhibition added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add the exhibition.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error while inserting the exhibition: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for budget.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Handle button click event to go back
    private void handleBack(ActionEvent e) {
        dispose();
        new HomePageForm().setVisible(true);
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddExhibitionPage().setVisible(true));
    }
}
