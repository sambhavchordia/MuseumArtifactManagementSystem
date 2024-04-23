import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteEmployeePage extends JFrame {
    private JTextField idField;
    private JButton deleteButton, backButton;

    // Database credentials and URL
    private static final String URL = "jdbc:mysql://localhost:3306/museum";
    private static final String USER = "root";
    private static final String PASSWORD = "shlok160404";

    public DeleteEmployeePage() {
        setTitle("Delete Employee");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setLayout(new GridLayout(3, 2)); // Adjusted for additional back button

        add(new JLabel("Employee ID:"));
        idField = new JTextField(20);
        add(idField);

        deleteButton = new JButton("Delete Employee");
        deleteButton.addActionListener(this::deleteEmployee);
        add(deleteButton);

        backButton = new JButton("Back");
        backButton.addActionListener(e -> dispose()); // Close this window
        add(backButton);
    }

    private void deleteEmployee(ActionEvent e) {
        String employeeId = idField.getText().trim();
        if (employeeId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Employee ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int deletedRows = deleteEmployeeById(employeeId);
        if (deletedRows > 0) {
            JOptionPane.showMessageDialog(this, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No employee found with that ID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int deleteEmployeeById(String id) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Employees WHERE EmployeeID = ?")) {
            statement.setInt(1, Integer.parseInt(id));
            return statement.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error deleting employee: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return 0;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID format. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DeleteEmployeePage().setVisible(true));
    }
}
