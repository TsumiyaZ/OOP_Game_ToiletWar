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
        setTitle(Config.NAME_GAME + " - Select Character");
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new GridLayout(1, Config.COUNT_SKIN));

        loadPicture();
        loadSkinPanel();
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
        // พาเนลพื้นหลัง gradient
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, top, 0, getHeight(), bottom);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(null); // จัดตำแหน่ง JLabel เองให้อยู่กลางล่าง

        // ---- JLabel ป้าย ----
        Image plateImg = plaque[index];
        JLabel plate = new JLabel();
        plate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        plate.addMouseListener(new MouseAdapter() {
            ImageIcon iconNormal;
            int w, h;

            @Override
            public void mouseEntered(MouseEvent e) {
                // ถ้ายังไม่ได้เก็บ icon เดิม ให้เก็บไว้ก่อน
                if (iconNormal == null) {
                    iconNormal = (ImageIcon) plate.getIcon();
                    w = iconNormal.getIconWidth();
                    h = iconNormal.getIconHeight();
                }

                // ขยาย 10%
                double scale = 1.1;
                int newW = (int) (w * scale);
                int newH = (int) (h * scale);

                // ตั้ง icon ใหม่ที่ขยายแล้ว
                Image bigger = iconNormal.getImage().getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
                plate.setIcon(new ImageIcon(bigger));

                // ยกขึ้นเล็กน้อย
                movePlate(plate, -5, -5, newW, newH);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // กลับสู่ขนาดเดิม
                plate.setIcon(iconNormal);
                movePlate(plate, 5, 5, w, h);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                selectMode select_Mode = new selectMode();
                System.out.println("Clicked plate index = " + index);
                setVisible(false);
                select_Mode.setVisible(true);
            }

            // ฟังก์ชันย่อย อ่านง่ายขึ้นมาก
            private void movePlate(JLabel p, int dx, int dy, int width, int height) {
                p.setBounds(p.getX() + dx, p.getY() + dy, width, height);
            }
        });

        panel.add(plate);

        // วาง/สเกลป้ายให้พอดีกับขนาด panel ทุกครั้งที่ขนาดเปลี่ยน
        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override public void componentResized(java.awt.event.ComponentEvent e) {
                if (plateImg == null) return;

                int pw = panel.getWidth();
                int ph = panel.getHeight();

                // ความกว้างป้าย ~ 70% ของช่อง (ปรับได้)
                int newW = (int) (pw * 0.75);

                int ow = plateImg.getWidth(null);
                int oh = plateImg.getHeight(null);

                int newH = (int) ((double) oh / ow * newW);

                // จัดให้อยู่ "กลางล่าง" เว้นขอบล่าง 14 px
                int x = (pw - newW) / 2;
                int y = ph - newH - 50;

                plate.setBounds(x, y, newW, newH);
                // สร้างไอคอนขนาดใหม่
                plate.setIcon(new ImageIcon(plateImg.getScaledInstance(newW, newH, Image.SCALE_SMOOTH)));
                plate.revalidate();
                plate.repaint();
            }
        });

        return panel;
    }
}
