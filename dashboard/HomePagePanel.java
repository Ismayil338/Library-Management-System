package dashboard;

import javax.swing.*;
import java.awt.*;

public class HomePagePanel extends JPanel {
    public HomePagePanel() {
        setLayout(new BorderLayout());
        
        ImageIcon bookImageIcon = new ImageIcon("Media/1_6Jp3vJWe7VFlFHZ9WhSJng.jpg");
        JLabel bookImageLabel = new JLabel(bookImageIcon, SwingConstants.CENTER);
        add(bookImageLabel, BorderLayout.CENTER);
    }
}
