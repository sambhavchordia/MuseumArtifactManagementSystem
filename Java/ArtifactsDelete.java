import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ArtifactsDelete extends JFrame {
    private JTextField artifactIdField;
    private JButton deleteButton, cancelButton;

    public ArtifactsDelete() {
        setTitle("Delete Artifact");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

        panel.add(new JLabel("Artifact ID:"));
        artifactIdField = new JTextField(20);
        panel.add(artifactIdField);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this::deleteArtifact);
        panel.add(deleteButton);

        cancelButton = new JButton("Back");
        cancelButton.addActionListener(e -> dispose());
        panel.add(cancelButton);

        add(panel);
    }

    private void deleteArtifact(ActionEvent e) {
        String artifactId = artifactIdField.getText().trim();
        if (artifactId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Artifact ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate that the input is an integer
        int id;
        try {
            id = Integer.parseInt(artifactId);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Invalid Artifact ID format", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/museum", "root", "shlok160404");
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Artifacts WHERE ArtifactID = ?")) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Artifact deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                artifactIdField.setText("");  // Clear the field after successful deletion
            } else {
                JOptionPane.showMessageDialog(this, "No artifact found with that ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error deleting artifact: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ArtifactsDelete().setVisible(true));
    }
}
