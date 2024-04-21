import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

// Employees viewer form class
public class EmployeesViewer extends JFrame {

    private JTextArea textArea;

    public EmployeesViewer() {
        setTitle("Employees Viewer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose this frame only
        initComponents();
        displayEmployees(); // Display employees when the frame is initialized
        setSize(600, 400); // Set window size
        setLocationRelativeTo(null);
    }

    // Initialize components
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout()); // Border layout

        // Text area to display employee details
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
        // Close this frame and show the EmployeesPage
        dispose();
        new EmployeesPage().setVisible(true);
    }

    // Display all employees
    private void displayEmployees() {
        try {
            // Connect to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/museum", "root", "shlok160404");

            // Query to fetch all employees
            String query = "SELECT * FROM Employees";

            // Execute the query
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Display employees in the text area
            StringBuilder employeesText = new StringBuilder();
            employeesText.append("Employees:\n");
            while (resultSet.next()) {
                int employeeID = resultSet.getInt("EmployeeID");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String role = resultSet.getString("Role");
                int departmentID = resultSet.getInt("DepartmentID");
                Date hireDate = resultSet.getDate("HireDate");
                String email = resultSet.getString("Email");
                String phone = resultSet.getString("Phone");

                employeesText.append("Employee ID: ").append(employeeID).append("\n");
                employeesText.append("Name: ").append(firstName).append(" ").append(lastName).append("\n");
                employeesText.append("Role: ").append(role).append("\n");
                employeesText.append("Department ID: ").append(departmentID).append("\n");
                employeesText.append("Hire Date: ").append(hireDate).append("\n");
                employeesText.append("Email: ").append(email).append("\n");
                employeesText.append("Phone: ").append(phone).append("\n");
                employeesText.append("-------------------------\n");
            }

            // Close the database connection
            resultSet.close();
            statement.close();
            connection.close();

            // Set the text in the text area
            textArea.setText(employeesText.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EmployeesViewer().setVisible(true);
        });
    }
}
