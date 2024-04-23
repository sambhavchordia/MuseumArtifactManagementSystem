import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ArtifactsViewer extends JFrame {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/museum";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "shlok160404";

    private static Connection connection;
    private static JComboBox<String> categoryComboBox;
    private static JTextArea textArea;

    public ArtifactsViewer() {
        setTitle("Artifacts Viewer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JLabel categoryLabel = new JLabel("Category:");
        topPanel.add(categoryLabel);

        categoryComboBox = new JComboBox<>();
        populateCategoryComboBox();
        topPanel.add(categoryComboBox);

        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) categoryComboBox.getSelectedItem();
                fetchArtifacts(selectedCategory);
            }
        });
        topPanel.add(filterButton);

        add(topPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    private static void populateCategoryComboBox() {
        try {
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Name FROM Categories");

            while (resultSet.next()) {
                String categoryName = resultSet.getString("Name");
                categoryComboBox.addItem(categoryName);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void fetchArtifacts(String category) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet;
            if (category.equals("All")) {
                resultSet = statement.executeQuery("SELECT * FROM Artifacts");
            } else {
                resultSet = statement.executeQuery("SELECT * FROM Artifacts WHERE CategoryID IN " +
                        "(SELECT CategoryID FROM Categories WHERE Name = '" + category + "')");
            }

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

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ArtifactsViewer().setVisible(true);
        });
    }
}
