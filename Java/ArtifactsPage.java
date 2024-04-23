import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ArtifactsPage extends JFrame {

    public ArtifactsPage() {
        setTitle("Artifacts Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);  // Center the window
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(5, 1, 10, 10)); // Grid layout with 5 rows

        // Button to display all artifacts
        JButton viewAllButton = new JButton("View All Artifacts");
        viewAllButton.addActionListener(this::handleViewAll);
        mainPanel.add(viewAllButton);

        // Button to add a new artifact
        JButton addButton = new JButton("Add New Artifact");
        addButton.addActionListener(this::handleAdd);
        mainPanel.add(addButton);

        // Button to modify an artifact
        JButton modifyButton = new JButton("Modify Artifact");
        modifyButton.addActionListener(this::handleModify);
        mainPanel.add(modifyButton);

        // Button to delete an artifact
        JButton deleteButton = new JButton("Delete Artifact");
        deleteButton.addActionListener(this::handleDelete);
        mainPanel.add(deleteButton);

        // Back button
        JButton backButton = new JButton("Back to Home");
        backButton.addActionListener(this::handleBack);
        mainPanel.add(backButton);

        getContentPane().add(mainPanel);  // Add the panel to the frame
    }

    private void handleViewAll(ActionEvent e) {
        SwingUtilities.invokeLater(() -> {
            new ArtifactsViewer().setVisible(true); // Assuming ArtifactsViewer is a class for viewing artifacts
        });
    }

    private void handleAdd(ActionEvent e) {
        SwingUtilities.invokeLater(() -> {
            new ArtifactsViewer2().setVisible(true); // Assuming ArtifactsAdder is a class for adding new artifacts
        });
    }

    private void handleModify(ActionEvent e) {
        SwingUtilities.invokeLater(() -> {
            new ArtifactsEditor().setVisible(true); // Assuming ArtifactsEditor is a class for modifying artifacts
        });
    }

    private void handleDelete(ActionEvent e) {
        SwingUtilities.invokeLater(() -> {
            new ArtifactsDelete().setVisible(true); // Assuming ArtifactsDelete is a class for deleting artifacts
        });
    }

    private void handleBack(ActionEvent e) {
        dispose(); // Close this window and return to the home page
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ArtifactsPage().setVisible(true));
    }
}
