import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// Exhibitions page form class
public class ExhibitionsPage extends JFrame {

    public ExhibitionsPage() {
        setTitle("Exhibitions");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose this frame only
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    // Initialize components
    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 0, 10)); // Vertical layout with equal spacing

        // Button to open exhibitions viewer
        JButton viewExhibitionButton = new JButton("View Exhibitions");
        viewExhibitionButton.addActionListener(this::openExhibitionViewer);
        mainPanel.add(viewExhibitionButton);

        // Button to open add exhibition page
        JButton addExhibitionButton = new JButton("Add Exhibition");
        addExhibitionButton.addActionListener(this::openAddExhibitionPage);
        mainPanel.add(addExhibitionButton);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(this::handleBack);
        mainPanel.add(backButton);

        getContentPane().add(mainPanel);
    }

    // Method to open exhibitions viewer page
    private void openExhibitionViewer(ActionEvent e) {
        // Close this frame and open exhibitions viewer
        dispose();
        new ExhibitionViewer().setVisible(true);
    }

    // Method to open add exhibition page
    private void openAddExhibitionPage(ActionEvent e) {
        // Close this frame and open add exhibition page
        dispose();
        new AddExhibitionPage().setVisible(true);
    }

    // Method to handle back button click
    private void handleBack(ActionEvent e) {
        // Close this frame and show the home page
        dispose();
        new HomePageForm().setVisible(true);
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ExhibitionsPage().setVisible(true);
        });
    }
}
