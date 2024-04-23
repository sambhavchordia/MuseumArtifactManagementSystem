import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// Home page form class
public class HomePageForm extends JFrame {

    public HomePageForm() {
        setTitle("Home Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    // Initialize components
    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout()); // Using GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Each component in its own row
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20); // Uniform padding

        // Create buttons and add them to the main panel using GridBagConstraints
        JButton artifactsButton = createButton("Artifacts");
        JButton auctionsButton = createButton("Auctions");
        JButton employeesButton = createButton("Employees");
        JButton exhibitionsButton = createButton("Exhibitions");
        JButton aboutButton = createButton("About");

        mainPanel.add(artifactsButton, gbc);
        mainPanel.add(auctionsButton, gbc);
        mainPanel.add(employeesButton, gbc);
        mainPanel.add(exhibitionsButton, gbc);
        mainPanel.add(aboutButton, gbc);

        // Add the main panel to the frame's content pane
        getContentPane().add(mainPanel);
    }

    // Create a button with given text
    private JButton createButton(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Button text cannot be null or empty");
        }

        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally
        button.setPreferredSize(new Dimension(200, 40)); // Set button size as originally specified
        button.addActionListener(this::handleButtonClick); // Add action listener
        return button;
    }

    // Handle button click events
    private void handleButtonClick(ActionEvent e) {
        String buttonText = ((JButton) e.getSource()).getText();
        JOptionPane.showMessageDialog(this, "You clicked: " + buttonText);
        if (buttonText.equals("Artifacts")) {
            new ArtifactsPage().setVisible(true);
        } else if (buttonText.equals("Auctions")) {
            new AuctionsPage().setVisible(true);
        } else if (buttonText.equals("Employees")) {
            new EmployeesPage().setVisible(true);
        } else if (buttonText.equals("Exhibitions")) {
            new ExhibitionsPage().setVisible(true);
        } else if (buttonText.equals("About")) {
            new AboutPage().setVisible(true);
        }
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HomePageForm().setVisible(true);
        });
    }
}
