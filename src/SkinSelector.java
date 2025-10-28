import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class SkinSelector extends JFrame {
    private Image chooseCharacter;
    private Image[] plaque = new Image[Config.COUNT_SKIN];
    private SkinPreview[] skinPreviews = new SkinPreview[Config.COUNT_SKIN];

    public SkinSelector() {
        setupFrame();
        loadImages();
        createSkinPanels();
        startAllAnimations();
    }

    private void setupFrame() {
        setTitle(Config.NAME_GAME + " - Select Character");
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new GridLayout(1, Config.COUNT_SKIN));
    }

    private void loadImages() {
        String signChoose = System.getProperty("user.dir") + File.separator +
                "assets" + File.separator + "obj" + File.separator + "Sign1.png";
        chooseCharacter = new ImageIcon(signChoose).getImage();

        for (int i = 0; i < Config.COUNT_SKIN; i++) {
            String path = System.getProperty("user.dir") + File.separator +
                    "assets" + File.separator + "obj" + File.separator + "p" + (i+1) + ".png";
            plaque[i] = new ImageIcon(path).getImage();
        }

        for (int i = 0; i < Config.COUNT_SKIN; i++) {
            skinPreviews[i] = new SkinPreview(i, 350, 350);
        }
    }

    private void createSkinPanels() {
        for (int i = 0; i < Config.COUNT_SKIN; i++) {
            int index = i;
            add(createSkinPanel(Config.COLOR_TOP[i], Config.COLOR_BOTTOM[i], index));
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

        Image plateImg = plaque[index];
        JLabel plate = new JLabel();
        plate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel charLabel = skinPreviews[index].getLabel();
        panel.add(charLabel);
        panel.add(plate);

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
                ModeSelector modeSelector = new ModeSelector(index);
                setVisible(false);
                modeSelector.setVisible(true);
            }

            private void movePlate(JLabel p, int dx, int dy, int width, int height) {
                p.setBounds(p.getX() + dx, p.getY() + dy, width, height);
            }
        });

        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override 
            public void componentResized(java.awt.event.ComponentEvent e) {
                int pw = panel.getWidth();
                int ph = panel.getHeight();

                int plateW = (int) (pw * 0.75);
                int ow = plateImg.getWidth(null);
                int oh = plateImg.getHeight(null);
                int plateH = (int) ((double) oh / ow * plateW);
                int plateX = (pw - plateW) / 2;
                int plateY = ph - plateH - 50;

                plate.setBounds(plateX, plateY, plateW, plateH);
                plate.setIcon(new ImageIcon(plateImg.getScaledInstance(plateW, plateH, Image.SCALE_SMOOTH)));

                int charW = 350;
                int charH = 350;
                int charX = (panel.getWidth() - charW) / 2;
                int charY = panel.getHeight() - charH - 150;

                skinPreviews[index].setBounds(charX, charY, charW, charH);
            }
        });

        return panel;
    }

    private void startAllAnimations() {
        for (SkinPreview preview : skinPreviews) {
            preview.startAnimation();
        }
    }

    @Override
    public void dispose() {
        for (SkinPreview preview : skinPreviews) {
            preview.stopAnimation();
        }
        super.dispose();
    }
}