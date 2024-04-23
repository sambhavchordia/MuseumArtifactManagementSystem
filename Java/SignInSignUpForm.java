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
            throw e;
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
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        add(new JLabel("Username:"), gbc);
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(120, 20)); // Reasonable size
        add(usernameField, gbc);

        add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(120, 20));
        add(passwordField, gbc);

        actionButton = new JButton("Sign In");
        actionButton.setPreferredSize(new Dimension(120, 20));
        add(actionButton, gbc);
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
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        add(new JLabel("Username:"), gbc);
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(120, 20));
        add(usernameField, gbc);

        add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(120, 20));
        add(passwordField, gbc);

        add(new JLabel("Confirm Password:"), gbc);
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setPreferredSize(new Dimension(120, 20));
        add(confirmPasswordField, gbc);

        actionButton = new JButton("Sign Up");
        actionButton.setPreferredSize(new Dimension(120, 20));
        add(actionButton, gbc);
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
        setSize(600, 400); // Ensure window size is adequate
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


