import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class selectSkin extends JFrame{
    GamePanel gamePanel = new GamePanel();
    JPanel panelSkin[] = new JPanel[Config.COUNT_SKIN];
    Image chooseCharacter;
    Image[] plaque = new Image[Config.COUNT_SKIN]; // <-- ป้าย p1..p4

    public selectSkin() {
        setFrame();
    }

    private void setFrame() {
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new GridLayout(1, Config.COUNT_SKIN));

        loadSkinPanel();
        loadPicture();
    }

    private void loadSkinPanel() {
        for (int i = 0; i < Config.COUNT_SKIN; i++) {
            int index = i;
            add(createSkinPanel(Config.COLOR_TOP[i], Config.COLOR_BOTTOM[i], index));
        }
    }

    private void loadPicture() {
        String path = System.getProperty("user.dir") + File.separator +
                "assets" + File.separator + "obj" + File.separator + "Sign1.png";
        chooseCharacter = new ImageIcon(path).getImage();

        // โหลดป้าย p1..p4
        for (int i = 0; i < Config.COUNT_SKIN; i++) {
            String p = System.getProperty("user.dir") + File.separator +
                    "assets" + File.separator + "obj" + File.separator + "p" + (i + 1) + ".png";
            plaque[i] = new ImageIcon(p).getImage();
        }
    }

    // ถ้ายังมีการวาด chooseCharacter ใน JFrame.paint อยู่ สามารถคงไว้ได้
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (chooseCharacter != null) {
            int w = chooseCharacter.getWidth(this), h = chooseCharacter.getHeight(this);
            double scale = 1.3;
            int nw = (int) (w * scale), nh = (int) (h * scale);
            int x = (getWidth() - nw) / 2, y = 35;
            g.drawImage(chooseCharacter, x, y, nw, nh, this);
        }
    }

    // วาด gradient + ป้ายของช่องนั้นๆ
    private JPanel createSkinPanel(Color top, Color bottom, int index) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // gradient
                GradientPaint gp = new GradientPaint(0, 0, top, 0, getHeight(), bottom);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // วาดป้าย p[index] ตรงกลางล่าง (คงสัดส่วน)
                Image plate = plaque[index];
                if (plate != null) {
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR); // ให้พิกเซลคม

                    int ow = plate.getWidth(this);
                    int oh = plate.getHeight(this);

                    // 🔹 ปรับขนาดใหญ่ขึ้น (จาก 70% → 85% และ scale 1.4 เท่า)
                    double scale = 2.0; // เพิ่มขนาดได้ที่นี่
                    int newW = (int) (getWidth() * 0.85 * scale);
                    int newH = (int) ((double) oh / ow * newW);

                    // 🔹 ให้อยู่ตรงกลางพอดี
                    int x = (getWidth() - newW) / 2;
                    int y = getHeight() - newH - 10; // ห่างจากขอบล่างเล็กน้อย

                    g2d.drawImage(plate, x - 22, y + 150, newW, newH, this);
                }
            }
        };
    }
}
