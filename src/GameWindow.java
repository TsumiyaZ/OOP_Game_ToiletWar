import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame{
    GamePanel gamePanel = new GamePanel();

    public GameWindow() {
        setTitle("Choose Skin");
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null); // ใช้ absolute layout เพื่อวางตำแหน่งง่าย

        gamePanel.setBounds(0,0,Config.WINDOW_WIDTH ,Config.WINDOW_HEIGHT);
        add(gamePanel);
    }
}
