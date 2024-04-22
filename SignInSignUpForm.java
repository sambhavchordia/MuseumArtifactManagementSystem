import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

// Interface for database connection
interface DatabaseConnection {
    Connection getConnection() throws SQLException;
}

// Utility class for database connection
class DatabaseUtil implements DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/museum";
    private static final String USER = "root";
    private static final String PASSWORD = "shlok160404";

    @Override
    public Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            throw e; // Re-throw to ensure that upper layers can handle it too if necessary
        }
    }
}

// Abstract class for Sign In and Sign Up panels
abstract class AuthPanel extends JPanel {
    protected JTextField usernameField;
    protected JPasswordField passwordField;
    protected JButton actionButton;
    protected JFrame parentFrame;

    protected abstract void initComponents();
    protected abstract void addActionListeners();

    public AuthPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    protected void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(parentFrame, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}

// SignIn panel implementation
class SignInPanel extends AuthPanel {
    public SignInPanel(JFrame parentFrame) {
        super(parentFrame);
        initComponents();
        addActionListeners();
    }

    @Override
    protected void initComponents() {
        setLayout(new GridLayout(3, 2, 10, 10));
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);
        actionButton = new JButton("Sign In");
        add(actionButton);
    }

    @Override
    protected void addActionListeners() {
        actionButton.addActionListener(this::handleAction);
    }

    protected void handleAction(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Username and password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Assume sign-in logic is successful
        showSuccessMessage("Sign In Successful!");
        parentFrame.dispose();
        new HomePageForm().setVisible(true);  // Assuming HomePageForm is the home screen
    }
}

// SignUp panel implementation
class SignUpPanel extends AuthPanel {
    private JPasswordField confirmPasswordField;

    public SignUpPanel(JFrame parentFrame) {
        super(parentFrame);
        initComponents();
        addActionListeners();
    }

    @Override
    protected void initComponents() {
        setLayout(new GridLayout(4, 2, 10, 10));
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);
        add(new JLabel("Confirm Password:"));
        confirmPasswordField = new JPasswordField();
        add(confirmPasswordField);
        actionButton = new JButton("Sign Up");
        add(actionButton);
    }

    @Override
    protected void addActionListeners() {
        actionButton.addActionListener(this::handleAction);
    }

    protected void handleAction(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(parentFrame, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Assume sign-up logic is successful
        showSuccessMessage("Sign Up Successful!");
        parentFrame.dispose();
        new HomePageForm().setVisible(true);  // Assuming HomePageForm is the home screen
    }
}

// Main frame for the application
public class SignInSignUpForm extends JFrame {

    public SignInSignUpForm() {
        setTitle("Sign In / Sign Up");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(createTabbedPane());
        pack();
        setLocationRelativeTo(null);
    }

    private JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Sign In", new SignInPanel(this));
        tabbedPane.addTab("Sign Up", new SignUpPanel(this));
        return tabbedPane;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new SignInSignUpForm().setVisible(true));
    }
}
