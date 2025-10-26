import javax.swing.*;
import java.awt.*;
import java.io.File;

public class singlePlay extends JFrame {
    Image bg;
    private int indexSkin;
    Image Character;

    public singlePlay(int indexSkin) {
        this.indexSkin = indexSkin;

        setFrame();
        loadImage();
    }

    private void setFrame() {
        setTitle(Config.NAME_GAME + " - Single Mode");
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void loadImage() {
        String path = System.getProperty("user.dir") + File.separator + "assets" +
                    File.separator + "bg" + File.separator + Config.backgroundGame;
        bg = new ImageIcon(path).getImage();
    }


    @Override
    public void paint(Graphics g) {
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
