import javax.swing.*;
import java.awt.*;
import java.io.File;

public class selectMode extends JFrame {
    Image bg;

    public selectMode() {
        setFrame();
    }

    private void setFrame() {
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        loadBackground();
    }

    private void loadBackground() {
        String path = System.getProperty("user.dir") + File.separator +
                "assets" + File.separator + "bg" + File.separator + Config.bg_Mode;
        bg = new ImageIcon(path).getImage();
    }

    @Override
    public void paint(Graphics g) {
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
