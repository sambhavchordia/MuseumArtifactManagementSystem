import javax.swing.*;
import java.awt.*;

public class AboutPage extends JFrame {

    public AboutPage() {
        setTitle("About");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        setSize(600, 400); // Set the preferred size
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText("Welcome to our museum's digital platform, where art and history converge to captivate and inspire. At our museum, we're dedicated to preserving and showcasing the beauty and significance of human creativity across diverse cultures and time periods.\n\n" +
                "Our extensive collection spans various categories, including sculptures, paintings, ancient artifacts, modern art, ceramics, textiles, photography, and digital art. Each artifact tells a unique story, offering glimpses into different eras, civilizations, and artistic movements.\n\n" +
                "With meticulously curated exhibitions, we invite you on a journey through time and space, exploring themes ranging from ancient civilizations to contemporary art movements. Our exhibitions delve into the depths of history, shedding light on pivotal moments, cultural exchanges, and artistic innovations.\n\n" +
                "Beyond exhibitions, our museum is a hub of cultural activity, hosting auctions that bring together collectors and enthusiasts from around the globe. From fine art to rare books, our auctions offer a glimpse into the world of collecting, where treasures find new homes and stories continue to unfold.\n\n" +
                "Behind the scenes, our dedicated team of professionals ensures the smooth operation of every aspect of our museum. From curators and auctioneers to conservators and administrators, each member plays a vital role in preserving, promoting, and sharing our rich cultural heritage.\n\n" +
                "Whether you're a seasoned art aficionado or a curious explorer, we invite you to embark on a journey of discovery at our museum. Join us as we celebrate the beauty, diversity, and enduring legacy of human creativity.\n\n" +
                "Welcome to our museum. Welcome to a world of wonder and inspiration.");
        JScrollPane scrollPane = new JScrollPane(textArea);
        getContentPane().add(scrollPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AboutPage().setVisible(true);
        });
    }
}
