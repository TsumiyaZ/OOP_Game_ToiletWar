import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class windowTitle extends JFrame {
    private Image backgroundImage;

    public windowTitle() {
        setTitle(Config.NAME_GAME + " - Title");
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        loadBackground();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    GameWindow frame = new GameWindow();
                    System.out.println("Welcome to Game");
                    setVisible(false);
                    frame.setVisible(true);
                }
            }
        });

    }

    private void loadBackground() {
        String path = System.getProperty("user.dir") + File.separator +
                "assets" + File.separator + "bg" + File.separator + "Page1.png";
        backgroundImage = new ImageIcon(path).getImage();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
