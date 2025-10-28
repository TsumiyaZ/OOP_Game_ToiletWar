import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GameUI {
    
    public static JLabel createBackground() {
        String bgPath = System.getProperty("user.dir") + File.separator +
                "assets" + File.separator + "bg" + File.separator + Config.backgroundGame;
        ImageIcon bgIcon = new ImageIcon(bgPath);
        Image bgScaled = bgIcon.getImage().getScaledInstance(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgScaled));
        background.setBounds(0, 0, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        return background;
    }

    public static JLabel createWinnerLabel() {
        JLabel winner = new JLabel("", SwingConstants.CENTER);
        winner.setFont(new Font("Arial", Font.BOLD, 60));
        winner.setForeground(Color.YELLOW);
        winner.setBounds(0, Config.WINDOW_HEIGHT / 2 - 100, Config.WINDOW_WIDTH, 100);
        winner.setVisible(false);
        return winner;
    }

    public static JLabel createTimerLabel() {
        JLabel timer = new JLabel("Time: 0.0 s", SwingConstants.RIGHT);
        timer.setFont(new Font("Arial", Font.BOLD, 24));
        timer.setForeground(Color.WHITE);
        timer.setBounds(Config.WINDOW_WIDTH - 220, 20, 200, 40);
        return timer;
    }

    public static JLabel createCountdownLabel() {
        JLabel countdown = new JLabel("", SwingConstants.CENTER);
        countdown.setFont(new Font("Arial", Font.BOLD, 80));
        countdown.setForeground(Color.ORANGE);
        countdown.setBounds(0, Config.WINDOW_HEIGHT / 2 - 150, Config.WINDOW_WIDTH, 150);
        return countdown;
    }
}