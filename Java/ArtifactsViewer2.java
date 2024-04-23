import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class ArtifactsViewer2 extends JFrame {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/museum";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "shlok160404";
    
    private JTextArea textArea;

    public ArtifactsViewer2() {
        super("Artifacts Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("Add Artifact");
        addButton.addActionListener(e -> addArtifactDialog());
        topPanel.add(addButton);

        JButton showAllButton = new JButton("Show All");
        showAllButton.addActionListener(e -> fetchAllArtifacts());
        topPanel.add(showAllButton);

        add(topPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void addArtifactDialog() {
        JFrame dialogFrame = new JFrame("Add Artifact");
        dialogFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialogFrame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(7, 2));

        JTextField nameField = new JTextField();
        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        JTextField descriptionField = new JTextField();
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);

        JTextField dateAcquiredField = new JTextField();
        panel.add(new JLabel("Date Acquired:"));
        panel.add(dateAcquiredField);

        JTextField originField = new JTextField();
        panel.add(new JLabel("Origin:"));
        panel.add(originField);

        JTextField dimensionsField = new JTextField();
        panel.add(new JLabel("Dimensions:"));
        panel.add(dimensionsField);

        JTextField categoryIDField = new JTextField();
        panel.add(new JLabel("Category ID:"));
        panel.add(categoryIDField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String description = descriptionField.getText();
            String dateAcquired = dateAcquiredField.getText();
            String origin = originField.getText();
            String dimensions = dimensionsField.getText();
            int categoryID = Integer.parseInt(categoryIDField.getText());

            addArtifact(name, description, dateAcquired, origin, dimensions, categoryID);
            dialogFrame.dispose();
        });
        panel.add(addButton);

        dialogFrame.add(panel);
        dialogFrame.setVisible(true);
    }

    private void addArtifact(String name, String description, String dateAcquired, String origin, String dimensions, int categoryID) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Artifacts (Name, Description, DateAcquired, Origin, Dimensions, CategoryID) VALUES (?, ?, ?, ?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setString(3, dateAcquired);
            statement.setString(4, origin);
            statement.setString(5, dimensions);
            statement.setInt(6, categoryID);
            statement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Artifact added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fetchAllArtifacts() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Artifacts")) {

            displayArtifacts(resultSet);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayArtifacts(ResultSet resultSet) throws SQLException {
        StringBuilder artifactsText = new StringBuilder("Artifacts:\n");
        while (resultSet.next()) {
            artifactsText.append("Artifact ID: ").append(resultSet.getInt("ArtifactID")).append("\n")
                         .append("Name: ").append(resultSet.getString("Name")).append("\n")
                         .append("Description: ").append(resultSet.getString("Description")).append("\n")
                         .append("Date Acquired: ").append(resultSet.getString("DateAcquired")).append("\n")
                         .append("Origin: ").append(resultSet.getString("Origin")).append("\n")
                         .append("Dimensions: ").append(resultSet.getString("Dimensions")).append("\n")
                         .append("Category ID: ").append(resultSet.getInt("CategoryID")).append("\n")
                         .append("-------------------------\n");
        }
        textArea.setText(artifactsText.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ArtifactsViewer2::new);
    }
}
