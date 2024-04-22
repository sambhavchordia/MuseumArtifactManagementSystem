import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;


public class AddAuctionPage extends JFrame {

    public AddAuctionPage() {
        setTitle("Add Auction");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(0, 2, 10, 10)); 

        
        JLabel nameLabel = new JLabel("Auction Name:");
        JTextField nameField = new JTextField();
        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        JTextField dateField = new JTextField();
        JLabel locationLabel = new JLabel("Location ID:");
        JTextField locationField = new JTextField();
        JLabel organizerLabel = new JLabel("Organizer ID:");
        JTextField organizerField = new JTextField();
        JLabel startPriceLabel = new JLabel("Start Price:");
        JTextField startPriceField = new JTextField();
        JLabel endPriceLabel = new JLabel("End Price:");
        JTextField endPriceField = new JTextField();

        
        mainPanel.add(nameLabel);
        mainPanel.add(nameField);
        mainPanel.add(dateLabel);
        mainPanel.add(dateField);
        mainPanel.add(locationLabel);
        mainPanel.add(locationField);
        mainPanel.add(organizerLabel);
        mainPanel.add(organizerField);
        mainPanel.add(startPriceLabel);
        mainPanel.add(startPriceField);
        mainPanel.add(endPriceLabel);
        mainPanel.add(endPriceField);

        
        JButton addButton = new JButton("Add Auction");
        addButton.addActionListener((ActionEvent e) -> {
            
            String auctionName = nameField.getText();
            String date = dateField.getText();
            int locationID = Integer.parseInt(locationField.getText());
            int organizerID = Integer.parseInt(organizerField.getText());
            double startPrice = Double.parseDouble(startPriceField.getText());
            double endPrice = Double.parseDouble(endPriceField.getText());

            
            addAuction(auctionName, date, locationID, organizerID, startPrice, endPrice);

            
            dispose();
        });

        
        mainPanel.add(addButton);

        getContentPane().add(mainPanel);
    }

    
    private void addAuction(String auctionName, String date, int locationID, int organizerID, double startPrice, double endPrice) {
        try {
            
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/museum", "root", "shlok160404");

            
            String query = "INSERT INTO Auctions (AuctionName, Date, LocationID, OrganizerID, StartPrice, EndPrice) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, auctionName);
            statement.setString(2, date);
            statement.setInt(3, locationID);
            statement.setInt(4, organizerID);
            statement.setDouble(5, startPrice);
            statement.setDouble(6, endPrice);

            
            statement.executeUpdate();

            
            statement.close();
            connection.close();

            
            JOptionPane.showMessageDialog(this, "Auction added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AddAuctionPage().setVisible(true);
        });
    }
}
