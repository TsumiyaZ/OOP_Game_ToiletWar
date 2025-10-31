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

    public static JLabel createToiletFlag(int finishLineX) {
        return createToiletFlag(finishLineX, 285);
    }

    public static JLabel createToiletFlag(int finishLineX, int yPosition) {
        String toiletPath = System.getProperty("user.dir") + File.separator +
                "assets" + File.separator + "obj" + File.separator + "ToiletFlag.png";
        
        try {
            ImageIcon toiletIcon = new ImageIcon(toiletPath);
            if (toiletIcon.getIconWidth() > 0) {
                Image toiletScaled = toiletIcon.getImage().getScaledInstance(60, 100, Image.SCALE_SMOOTH);
                JLabel toiletFlag = new JLabel(new ImageIcon(toiletScaled));
                toiletFlag.setBounds(finishLineX - 30, yPosition + 10, 60, 100);
                return toiletFlag;
            }
        } catch (Exception e) {
            System.out.println("Could not load toilet flag image: " + e.getMessage());
        }
        
        JLabel toiletFlag = new JLabel("🚽");
        toiletFlag.setFont(new Font("Arial", Font.BOLD, 40));
        toiletFlag.setBounds(finishLineX - 20, yPosition + 20, 40, 80);
        return toiletFlag;
    }

    public static JPanel createMultiplayerToiletFlags(int finishLineX, int roadStartY, int laneHeight, int playerCount) {
        JPanel flagPanel = new JPanel();
        flagPanel.setLayout(null);
        flagPanel.setOpaque(false);
        flagPanel.setBounds(0, 0, 1200, 600);
        
        for (int i = 0; i < playerCount; i++) {
            int yPosition = roadStartY + (i * laneHeight);
            JLabel toiletFlag = createToiletFlag(finishLineX, yPosition);
            flagPanel.add(toiletFlag);
        }
        
        return flagPanel;
    }

    public static JLabel createWinnerImage() {
        String winnerPath = System.getProperty("user.dir") + File.separator +
                "assets" + File.separator + "bg" + File.separator + "Winner.png";
        
        try {
            ImageIcon winnerIcon = new ImageIcon(winnerPath);
            if (winnerIcon.getIconWidth() > 0) {
                Image winnerScaled = winnerIcon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
                JLabel winnerLabel = new JLabel(new ImageIcon(winnerScaled));
                winnerLabel.setBounds(Config.WINDOW_WIDTH / 2 - 200, Config.WINDOW_HEIGHT / 2 - 150, 400, 200);
                winnerLabel.setVisible(false);
                return winnerLabel;
            }
        } catch (Exception e) {
            System.out.println("Could not load winner image: " + e.getMessage());
        }
        
        JLabel winnerLabel = new JLabel("🏆 WINNER! 🏆", SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 50));
        winnerLabel.setForeground(Color.YELLOW);
        winnerLabel.setBounds(0, Config.WINDOW_HEIGHT / 2 - 100, Config.WINDOW_WIDTH, 100);
        winnerLabel.setVisible(false);
        return winnerLabel;
    }

    public static JLabel createLoserImage() {
        String loserPath = System.getProperty("user.dir") + File.separator +
                "assets" + File.separator + "bg" + File.separator + "Lose.png";
        
        try {
            ImageIcon loserIcon = new ImageIcon(loserPath);
            if (loserIcon.getIconWidth() > 0) {
                Image loserScaled = loserIcon.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
                JLabel loserLabel = new JLabel(new ImageIcon(loserScaled));
                loserLabel.setBounds(Config.WINDOW_WIDTH / 2 - 200, Config.WINDOW_HEIGHT / 2 - 150, 400, 200);
                loserLabel.setVisible(false);
                return loserLabel;
            }
        } catch (Exception e) {
            System.out.println("Could not load loser image: " + e.getMessage());
        }
        
        JLabel loserLabel = new JLabel("😢 YOU LOSE 😢", SwingConstants.CENTER);
        loserLabel.setFont(new Font("Arial", Font.BOLD, 50));
        loserLabel.setForeground(Color.RED);
        loserLabel.setBounds(0, Config.WINDOW_HEIGHT / 2 - 100, Config.WINDOW_WIDTH, 100);
        loserLabel.setVisible(false);
        return loserLabel;
    }
}