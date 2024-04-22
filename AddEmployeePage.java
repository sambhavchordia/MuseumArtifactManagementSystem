import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;


public class AddEmployeePage extends JFrame {

    public AddEmployeePage() {
        setTitle("Add Employee");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        initComponents();
        setSize(400, 300); 
        setLocationRelativeTo(null);
    }

    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(0, 2, 10, 10)); 

        
        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField();
        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField();
        JLabel roleLabel = new JLabel("Role:");
        JTextField roleField = new JTextField();
        JLabel departmentIDLabel = new JLabel("Department ID:");
        JTextField departmentIDField = new JTextField();
        JLabel hireDateLabel = new JLabel("Hire Date (YYYY-MM-DD):");
        JTextField hireDateField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField();

        
        mainPanel.add(firstNameLabel);
        mainPanel.add(firstNameField);
        mainPanel.add(lastNameLabel);
        mainPanel.add(lastNameField);
        mainPanel.add(roleLabel);
        mainPanel.add(roleField);
        mainPanel.add(departmentIDLabel);
        mainPanel.add(departmentIDField);
        mainPanel.add(hireDateLabel);
        mainPanel.add(hireDateField);
        mainPanel.add(emailLabel);
        mainPanel.add(emailField);
        mainPanel.add(phoneLabel);
        mainPanel.add(phoneField);

        
        JButton addButton = new JButton("Add Employee");
        addButton.addActionListener((e) -> {
            addEmployee(firstNameField.getText(), lastNameField.getText(), roleField.getText(),
                    departmentIDField.getText(), hireDateField.getText(), emailField.getText(), phoneField.getText());
        });
        mainPanel.add(addButton);

        
        JButton backButton = new JButton("Back");
        backButton.addActionListener(this::handleBack);
        mainPanel.add(backButton);

        getContentPane().add(mainPanel);
    }

    
    private void handleBack(ActionEvent e) {
        
        dispose();
        new EmployeesPage().setVisible(true);
    }

    
    private void addEmployee(String firstName, String lastName, String role, String departmentID, String hireDate, String email, String phone) {
        try {
            
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/museum", "root", "shlok160404");

            
            String query = "INSERT INTO Employees (FirstName, LastName, Role, DepartmentID, HireDate, Email, Phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, role);
            statement.setString(4, departmentID);
            statement.setString(5, hireDate);
            statement.setString(6, email);
            statement.setString(7, phone);

            
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add employee", "Error", JOptionPane.ERROR_MESSAGE);
            }

            
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AddEmployeePage().setVisible(true);
        });
    }
}
