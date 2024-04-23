import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class ArtifactsEditor extends JFrame {
    private JTextField idField, nameField, descriptionField;
    private JButton saveButton, cancelButton;
    private Connection connection;

    public ArtifactsEditor() {
        try {
            // Establishing connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/museum", "root", "shlok160404");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to connect to database: " + e.getMessage(), "Database Connection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        setTitle("Edit Artifact");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));

        panel.add(new JLabel("Artifact ID:"));
        idField = new JTextField(20);
        panel.add(idField);

        panel.add(new JLabel("New Name:"));
        nameField = new JTextField(20);
        panel.add(nameField);

        panel.add(new JLabel("New Description:"));
        descriptionField = new JTextField(20);
        panel.add(descriptionField);

        saveButton = new JButton("Save Changes");
        saveButton.addActionListener(this::saveChanges);
        panel.add(saveButton);

        cancelButton = new JButton("Back");
        cancelButton.addActionListener(e -> dispose());
        panel.add(cancelButton);

        add(panel);
    }

    private void saveChanges(ActionEvent e) {
        String id = idField.getText().trim();
        String newName = nameField.getText().trim();
        String newDescription = descriptionField.getText().trim();

        if (id.isEmpty() || newName.isEmpty() || newDescription.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "UPDATE Artifacts SET Name = ?, Description = ? WHERE ArtifactID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newName);
            statement.setString(2, newDescription);
            statement.setInt(3, Integer.parseInt(id));

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Artifact updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No artifact found with ID: " + id, "Update Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating artifact: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ArtifactsEditor().setVisible(true));
    }
}
