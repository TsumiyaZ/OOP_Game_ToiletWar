import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class windowTitle extends JFrame {
    private Image backgroundImage;
    private Image textStart;

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
                    SkinSelector skinSelector = new SkinSelector();
                    System.out.println("Welcome to Game");
                    setVisible(false);
                    skinSelector.setVisible(true);
                }
            }
        });

    }

    private void loadBackground() {
        String path = System.getProperty("user.dir") + File.separator +
                "assets" + File.separator + "bg" + File.separator + Config.background;
        backgroundImage = new ImageIcon(path).getImage();

        String path_text = System.getProperty("user.dir") + File.separator +
                "assets" + File.separator + "obj" + File.separator + Config.TEXT_SPACE_START;
        textStart = new ImageIcon(path_text).getImage();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        if (textStart != null) {
            int textWidth = textStart.getWidth(this);
            int textHeight = textStart.getHeight(this);

            // คำนวณตำแหน่งให้อยู่ "กลางล่าง" ของจอ
            int x = (getWidth() - textWidth) / 2;
            int y = getHeight() - textHeight - 60; // เว้นจากขอบล่างนิดหน่อย (~60px)

            // ✅ ตั้งค่าความโปร่งใส (0.0 = ใสหมด, 1.0 = ทึบ)
            float alpha = 0.6f; // จางประมาณ 60%
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

            g2d.drawImage(textStart, x, y, this);
        }
    }
}
