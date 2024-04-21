import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// Auctions page form class
public class AuctionsPage extends JFrame {

    public AuctionsPage() {
        setTitle("Auctions");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose this frame only
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    // Initialize components
    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 0, 10)); // Vertical layout with equal spacing

        // Button to open AuctionsViewer
        JButton viewAllButton = new JButton("View All Auctions");
        viewAllButton.addActionListener(this::handleViewAll);
        mainPanel.add(viewAllButton);

        // Button to open AddAuctionPage
        JButton addButton = new JButton("Add New Auction");
        addButton.addActionListener(this::handleAdd);
        mainPanel.add(addButton);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(this::handleBack);
        mainPanel.add(backButton);

        getContentPane().add(mainPanel);
    }

    // Handle button click events
    private void handleViewAll(ActionEvent e) {
        // Open AuctionsViewer
        new AuctionsViewer().setVisible(true);
    }

    private void handleAdd(ActionEvent e) {
        // Open AddAuctionPage
        new AddAuctionPage().setVisible(true);
    }

    private void handleBack(ActionEvent e) {
        // Close this frame and show the home page
        dispose();
        new HomePageForm().setVisible(true);
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AuctionsPage().setVisible(true);
        });
    }
}
