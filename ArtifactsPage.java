import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ArtifactsPage extends JFrame {

    public ArtifactsPage() {
        setTitle("Artifacts");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose this frame only
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 0, 10)); // Vertical layout with equal spacing

        // Button to display all artifacts
        JButton viewAllButton = new JButton("View All Artifacts");
        viewAllButton.addActionListener(this::handleViewAll);
        mainPanel.add(viewAllButton);

        // Button to add a new artifact
        JButton addButton = new JButton("Add New Artifact");
        addButton.addActionListener(this::handleAdd);
        mainPanel.add(addButton);

        // Back button
        JButton backButton = new JButton("Back to Home");
        backButton.addActionListener(this::handleBack);
        mainPanel.add(backButton);

        getContentPane().add(mainPanel);
    }

    private void handleViewAll(ActionEvent e) {
        // Launch ArtifactsViewer
        SwingUtilities.invokeLater(() -> {
            new ArtifactsViewer().main(new String[0]);
        });
    }

    private void handleAdd(ActionEvent e) {
        // Launch ArtifactsViewer2
        SwingUtilities.invokeLater(() -> {
            new ArtifactsViewer2().main(new String[0]);
        });
    }

    private void handleBack(ActionEvent e) {
        // Close the current window and open HomePageForm
        dispose(); // Close current frame
        HomePageForm.main(new String[0]); // Open HomePageForm
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ArtifactsPage().setVisible(true);
        });
    }
}
