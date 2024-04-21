import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ArtifactsViewer2 {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/museum";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "shlok160404";

    private static Connection connection;
    private static JTextArea textArea;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Artifacts Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("Add Artifact");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a dialog to add a new artifact
                addArtifactDialog();
            }
        });
        topPanel.add(addButton);

        JButton showAllButton = new JButton("Show All");
        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchAllArtifacts();
            }
        });
        topPanel.add(showAllButton);

        frame.add(topPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static void addArtifactDialog() {
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
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String description = descriptionField.getText();
                String dateAcquired = dateAcquiredField.getText();
                String origin = originField.getText();
                String dimensions = dimensionsField.getText();
                int categoryID = Integer.parseInt(categoryIDField.getText());

                addArtifact(name, description, dateAcquired, origin, dimensions, categoryID);
                dialogFrame.dispose();
            }
        });
        panel.add(addButton);

        dialogFrame.add(panel);
        dialogFrame.setVisible(true);
    }

    private static void addArtifact(String name, String description, String dateAcquired, String origin, String dimensions, int categoryID) {
        try {
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Artifacts (Name, Description, DateAcquired, Origin, Dimensions, CategoryID) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setString(3, dateAcquired);
            statement.setString(4, origin);
            statement.setString(5, dimensions);
            statement.setInt(6, categoryID);
            statement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Artifact added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void fetchAllArtifacts() {
        try {
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Artifacts");

            displayArtifacts(resultSet);

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void displayArtifacts(ResultSet resultSet) throws SQLException {
        StringBuilder artifactsText = new StringBuilder();
        artifactsText.append("Artifacts:\n");
        while (resultSet.next()) {
            int artifactID = resultSet.getInt("ArtifactID");
            String name = resultSet.getString("Name");
            String description = resultSet.getString("Description");
            String dateAcquired = resultSet.getString("DateAcquired");
            String origin = resultSet.getString("Origin");
            String dimensions = resultSet.getString("Dimensions");
            int categoryID = resultSet.getInt("CategoryID");

            artifactsText.append("Artifact ID: ").append(artifactID).append("\n");
            artifactsText.append("Name: ").append(name).append("\n");
            artifactsText.append("Description: ").append(description).append("\n");
            artifactsText.append("Date Acquired: ").append(dateAcquired).append("\n");
            artifactsText.append("Origin: ").append(origin).append("\n");
            artifactsText.append("Dimensions: ").append(dimensions).append("\n");
            artifactsText.append("Category ID: ").append(categoryID).append("\n");
            artifactsText.append("-------------------------\n");
        }

        textArea.setText(artifactsText.toString());
    }
}
