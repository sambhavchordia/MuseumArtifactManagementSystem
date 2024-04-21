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
        JPanel mainPanel = new JPanel(new GridLayout(0, 1, 0, 10)); // Vertical layout with equal spacing

        // Create buttons
        JButton artifactsButton = createButton("Artifacts");
        JButton auctionsButton = createButton("Auctions");
        JButton employeesButton = createButton("Employees");
        JButton exhibitionsButton = createButton("Exhibitions");
        JButton aboutButton = createButton("About");

        // Add buttons to the main panel
        mainPanel.add(artifactsButton);
        mainPanel.add(auctionsButton);
        mainPanel.add(employeesButton);
        mainPanel.add(exhibitionsButton);
        mainPanel.add(aboutButton);

        // Add main panel to the frame's content pane
        getContentPane().add(mainPanel);
    }

    // Create a button with given text
    private JButton createButton(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Button text cannot be null or empty");
        }

        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally
        button.setPreferredSize(new Dimension(200, 40)); // Set button size
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
        // Add similar conditions for other buttons
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HomePageForm().setVisible(true);
        });
    }
}
