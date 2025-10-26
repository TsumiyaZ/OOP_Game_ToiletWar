import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class selectSkin extends JFrame {
    Image chooseCharacter;
    Image[] plaque = new Image[Config.COUNT_SKIN];

    // [ADD CHATGPT] อาร์เรย์เก็บรูปตัวละคร 1..4
    private final Image[] charImg = new Image[Config.COUNT_SKIN]; // [ADD CHATGPT]

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
        // ป้าย Choose Skin
        String signChoose = System.getProperty("user.dir") + File.separator +
                "assets" + File.separator + "obj" + File.separator + "Sign1.png";
        chooseCharacter = new ImageIcon(signChoose).getImage();

        // ป้ายชื่อตัวละคร
        for (int i = 0; i < Config.COUNT_SKIN; i++) {
            String path = System.getProperty("user.dir") + File.separator +
                    "assets" + File.separator + "obj" + File.separator + "p" + (i+1) + ".png";
            plaque[i] = new ImageIcon(path).getImage();
        }

        // ตัวละคร
        for (int i = 0; i < Config.COUNT_SKIN; i++) {
            String path = System.getProperty("user.dir") + File.separator +
                    "assets" + File.separator + "Skin" + File.separator + Config.character_skin[i];
            charImg[i] = new ImageIcon(path).getImage();
        }
    }

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

    // วาด gradient + ป้ายของช่องนั้นๆ + วางตัวละคร
    private JPanel createSkinPanel(Color top, Color bottom, int index) {
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
        panel.setLayout(null);

        // ---- JLabel ป้าย ----
        Image plateImg = plaque[index];
        JLabel plate = new JLabel();
        plate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // [ADD CHATGPT] ตัวละคร (อยู่กึ่งกลาง เหนือป้าย)
        JLabel charLabel = new JLabel(); //
        panel.add(charLabel);            //  ต้อง add ก่อนเพื่อให้อยู่ใต้ป้าย (เพิ่มสกิน)
        panel.add(plate);                  // เพิ่มปุ่ม select ตัวละคร

        plate.addMouseListener(new MouseAdapter() {
            ImageIcon iconNormal;
            int w, h;

            @Override
            public void mouseEntered(MouseEvent e) {
                if (iconNormal == null) {
                    iconNormal = (ImageIcon) plate.getIcon();
                    w = iconNormal.getIconWidth();
                    h = iconNormal.getIconHeight();
                }
                double scale = 1.1;
                int newW = (int) (w * scale);
                int newH = (int) (h * scale);
                Image bigger = iconNormal.getImage().getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
                plate.setIcon(new ImageIcon(bigger));
                movePlate(plate, -5, -5, newW, newH);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                plate.setIcon(iconNormal);
                movePlate(plate, 5, 5, w, h);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                selectMode select_Mode = new selectMode(index);
                System.out.println("Clicked plate index = " + index);
                setVisible(false);
                select_Mode.setVisible(true);
            }

            private void movePlate(JLabel p, int dx, int dy, int width, int height) {
                p.setBounds(p.getX() + dx, p.getY() + dy, width, height);
            }
        });

        // [ADD CHATGPT] จัดวางตัวละคร + ป้ายทุกครั้งที่รีไซส์
        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override public void componentResized(java.awt.event.ComponentEvent e) {
                // ----- ป้าย -----
                int pw = panel.getWidth();
                int ph = panel.getHeight();

                int plateW = (int) (pw * 0.75); // ความกว้างป้าย ~75% ของช่อง
                int ow = plateImg.getWidth(null);
                int oh = plateImg.getHeight(null);
                int plateH = (int) ((double) oh / ow * plateW);
                int plateX = (pw - plateW) / 2;
                int plateY = ph - plateH - 50;

                plate.setBounds(plateX, plateY, plateW, plateH);
                plate.setIcon(new ImageIcon(plateImg.getScaledInstance(plateW, plateH, Image.SCALE_SMOOTH)));

                // ----- ตัวละคร -----
                Image sprite = charImg[index];
                if (sprite != null) {
                    // ขนาดตัวละครตายตัว (กว้าง 200 สูงอัตโนมัติ)
                    int charW = 350;
                    int sw = sprite.getWidth(null);
                    int sh = sprite.getHeight(null);
                    int charH = (int) ((double) sh / sw * charW);

                    // จัดกึ่งกลางแนวนอน และวางเหนือป้าย ~200px
                    int charX = (panel.getWidth() - charW) / 2;
                    int charY = panel.getHeight() - charH - 150;

                    // ตั้งตำแหน่งและใส่รูป
                    charLabel.setBounds(charX, charY, charW, charH);
                    charLabel.setIcon(new ImageIcon(sprite.getScaledInstance(charW, charH, Image.SCALE_SMOOTH)));
                }
            }
        });

        return panel;
    }
}
