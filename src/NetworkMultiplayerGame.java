import javax.swing.*;
import java.awt.*;

public class NetworkMultiplayerGame extends JFrame {
    
    public NetworkMultiplayerGame() {
        setupFrame();
        createComponents();
    }

    private void setupFrame() {
        setTitle(Config.NAME_GAME + " - Network Multiplayer");
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createComponents() {
        JLabel label = new JLabel("Network Multiplayer Game - Coming Soon!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.CENTER);
        
        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> {
            setVisible(false);
            new SkinSelector().setVisible(true);
        });
        add(backButton, BorderLayout.SOUTH);
    }
}