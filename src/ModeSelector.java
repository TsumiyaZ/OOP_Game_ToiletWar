import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class ModeSelector extends JFrame {
    private MultiplayerSelector multiplayerSelector;
    private Image bg;
    private ImageIcon[] buttonMode = new ImageIcon[Config.btn_Mode.length];
    private int skinIndex;
    private SkinPreview skinPreview;

    public ModeSelector(int skinIndex) {
        this.skinIndex = skinIndex;
        this.multiplayerSelector = new MultiplayerSelector(this);
        this.skinPreview = new SkinPreview(skinIndex, 200, 200);
        setupFrame();
        loadImages();
        createPanel();
        skinPreview.startAnimation();
    }

    private void setupFrame() {
        setTitle(Config.NAME_GAME + " - Select Mode");
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
    }

    private void loadImages() {
        String bgPath = System.getProperty("user.dir") + File.separator +
                "assets" + File.separator + "bg" + File.separator + Config.bg_Mode;
        bg = new ImageIcon(bgPath).getImage();

        for (int i = 0; i < Config.btn_Mode.length; i++) {
            String pathMode = System.getProperty("user.dir") + File.separator +
                    "assets" + File.separator + "obj" + File.separator + Config.btn_Mode[i];
            buttonMode[i] = new ImageIcon(pathMode);
        }
    }

    private void createPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };

        panel.setLayout(new BorderLayout());
        getContentPane().add(panel);

        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.add(skinPreview.getLabel());

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new GridBagLayout());

        Box box = Box.createVerticalBox();

        int width = 350;
        int height = 100;

        JLabel btnSingle = new JLabel(scaleIcon(buttonMode[0], width, height));
        JLabel btnMulti = new JLabel(scaleIcon(buttonMode[1], width, height));
        JLabel btnHow = new JLabel(scaleIcon(buttonMode[2], width, height));

        btnSingle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnMulti.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnHow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnSingle.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMulti.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnHow.setAlignmentX(Component.CENTER_ALIGNMENT);

        box.add(btnSingle);
        box.add(Box.createRigidArea(new Dimension(0, 30)));
        box.add(btnMulti);
        box.add(Box.createRigidArea(new Dimension(0, 30)));
        box.add(btnHow);

        centerPanel.add(box);
        
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(centerPanel, BorderLayout.CENTER);

        setupButtonListeners(btnSingle, btnMulti, btnHow, width, height);
    }

    private void setupButtonListeners(JLabel btnSingle, JLabel btnMulti, JLabel btnHow, int width, int height) {
        btnSingle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                scaleButton(btnSingle, buttonMode[0], width, height, 1.1);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSingle.setIcon(scaleIcon(buttonMode[0], width, height));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                SinglePlayerGame single = new SinglePlayerGame(skinIndex);
                setVisible(false);
            }
        });

        btnMulti.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                scaleButton(btnMulti, buttonMode[1], width, height, 1.1);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnMulti.setIcon(scaleIcon(buttonMode[1], width, height));
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }
        });

        btnHow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                scaleButton(btnHow, buttonMode[2], width, height, 1.1);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnHow.setIcon(scaleIcon(buttonMode[2], width, height));
            }
        });
    }

    private void scaleButton(JLabel button, ImageIcon icon, int width, int height, double scale) {
        int newW = (int) (width * scale);
        int newH = (int) (height * scale);
        Image bigger = icon.getImage().getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(bigger));
    }

    private static ImageIcon scaleIcon(ImageIcon icon, int w, int h) {
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private ImageIcon createDisabledIcon(ImageIcon original, int width, int height) {
        Image img = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        java.awt.image.BufferedImage buffered = new java.awt.image.BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffered.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        return new ImageIcon(buffered);
    }

    @Override
    public void dispose() { 
        if (skinPreview != null) {
            skinPreview.stopAnimation();
        }
        super.dispose();
    }
}