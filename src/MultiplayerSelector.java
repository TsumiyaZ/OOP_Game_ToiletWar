import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class MultiplayerSelector extends JFrame {
    private Image bg;
    private ImageIcon[] btnMode = new ImageIcon[Config.btn_Multiplay.length];
    private ModeSelector modeSelector;

    public MultiplayerSelector(ModeSelector modeSelector) {
        this.modeSelector = modeSelector;
        setupFrame();
        loadImages();
        createPanel();
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

        for (int i = 0; i < Config.btn_Multiplay.length; i++) {
            String pathMode = System.getProperty("user.dir") + File.separator +
                    "assets" + File.separator + "obj" + File.separator + Config.btn_Multiplay[i];
            btnMode[i] = new ImageIcon(pathMode);
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

        panel.setLayout(new GridBagLayout());
        getContentPane().add(panel);

        Box box = Box.createVerticalBox();

        int width = 350;
        int height = 100;

        JLabel btnHost = new JLabel(scaleIcon(btnMode[0], width, height));
        JLabel btnJoin = new JLabel(scaleIcon(btnMode[1], width, height));
        JLabel btnBack = new JLabel(scaleIcon(btnMode[2], width, height));

        btnHost.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnJoin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnHost.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnJoin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);

        box.add(btnHost);
        box.add(Box.createRigidArea(new Dimension(0, 30)));
        box.add(btnJoin);
        box.add(Box.createRigidArea(new Dimension(0, 30)));
        box.add(btnBack);

        panel.add(box);

        setupButtonListeners(btnHost, btnJoin, btnBack, width, height);
    }

    private void setupButtonListeners(JLabel btnHost, JLabel btnJoin, JLabel btnBack, int width, int height) {
        btnHost.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                scaleButton(btnHost, btnMode[0], width, height, 1.1);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnHost.setIcon(scaleIcon(btnMode[0], width, height));
            }
        });

        btnJoin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                scaleButton(btnJoin, btnMode[1], width, height, 1.1);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnJoin.setIcon(scaleIcon(btnMode[1], width, height));
            }
        });

        btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                scaleButton(btnBack, btnMode[2], width, height, 1.1);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnBack.setIcon(scaleIcon(btnMode[2], width, height));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setVisible(false);
                modeSelector.setVisible(true);
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
}