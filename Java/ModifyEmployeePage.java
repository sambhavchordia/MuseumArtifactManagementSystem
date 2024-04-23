import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModifyEmployeePage extends JFrame {
    private JTextField idField, firstNameField, roleField;
    private JButton updateButton, backButton;

    // Database credentials and URL (replace these with your actual database details)
    private static final String URL = "jdbc:mysql://localhost:3306/museum";
    private static final String USER = "root";
    private static final String PASSWORD = "shlok160404";

    public ModifyEmployeePage() {
        setTitle("Modify Employee");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Employee ID:"));
        idField = new JTextField(20);
        add(idField);

        add(new JLabel("New First Name:")); // Label changed to specify "First Name"
        firstNameField = new JTextField(20);
        add(firstNameField);

        add(new JLabel("New Role:"));
        roleField = new JTextField(20);
        add(roleField);

        updateButton = new JButton("Update Employee");
        updateButton.addActionListener(this::updateEmployee);
        add(updateButton);

        backButton = new JButton("Back");
        backButton.addActionListener(e -> dispose());
        add(backButton);
    }

    private void updateEmployee(ActionEvent e) {
        String employeeId = idField.getText().trim();
        String newFirstName = firstNameField.getText().trim(); // Updated to use newFirstName
        String newRole = roleField.getText().trim();

        if (employeeId.isEmpty() || newFirstName.isEmpty() || newRole.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (updateEmployeeInDatabase(employeeId, newFirstName, newRole)) {
            JOptionPane.showMessageDialog(this, "Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update employee.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean updateEmployeeInDatabase(String id, String firstName, String role) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                 "UPDATE Employees SET FirstName = ?, Role = ? WHERE EmployeeID = ?")) {
            statement.setString(1, firstName);
            statement.setString(2, role);
            statement.setInt(3, Integer.parseInt(id));

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid format for Employee ID", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ModifyEmployeePage().setVisible(true));
    }
}
