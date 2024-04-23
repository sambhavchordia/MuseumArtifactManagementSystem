import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EmployeesPage extends JFrame {

    public EmployeesPage() {
        setTitle("Employees");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(4, 1, 0, 10)); // Updated grid layout to 4 rows

        JButton viewAllButton = new JButton("View All Employees");
        viewAllButton.addActionListener(this::handleViewAll);
        mainPanel.add(viewAllButton);

        JButton addButton = new JButton("Add New Employee");
        addButton.addActionListener(this::handleAdd);
        mainPanel.add(addButton);

        JButton modifyButton = new JButton("Modify Employee");
        modifyButton.addActionListener(this::handleModify);
        mainPanel.add(modifyButton);

        JButton deleteButton = new JButton("Delete Employee");
        deleteButton.addActionListener(this::handleDelete);
        mainPanel.add(deleteButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(this::handleBack);
        mainPanel.add(backButton);

        getContentPane().add(mainPanel);
    }

    private void handleViewAll(ActionEvent e) {
        new EmployeesViewer().setVisible(true);
    }

    private void handleAdd(ActionEvent e) {
        new AddEmployeePage().setVisible(true);
    }

    private void handleModify(ActionEvent e) {
        new ModifyEmployeePage().setVisible(true);
    }

    private void handleDelete(ActionEvent e) {
        new DeleteEmployeePage().setVisible(true);
    }

    private void handleBack(ActionEvent e) {
        dispose();
        new HomePageForm().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EmployeesPage().setVisible(true);
        });
    }
}
