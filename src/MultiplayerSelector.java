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

        Box mainBox = Box.createVerticalBox();
        
        JLabel instructionLabel = new JLabel("<html><center>To play multiplayer:<br>1. Run ServerLauncher.java separately<br>2. Enter server IP and click 'Join Game'</center></html>");
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainBox.add(instructionLabel);
        mainBox.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JPanel ipPanel = new JPanel(new FlowLayout());
        ipPanel.setOpaque(false);
        JLabel ipLabel = new JLabel("Server IP:");
        ipLabel.setForeground(Color.WHITE);
        ipLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField ipField = new JTextField("localhost", 15);
        ipPanel.add(ipLabel);
        ipPanel.add(ipField);
        mainBox.add(ipPanel);
        mainBox.add(Box.createRigidArea(new Dimension(0, 20)));

        Box box = Box.createVerticalBox();

        int width = 350;
        int height = 100;

        JLabel btnJoin = new JLabel(scaleIcon(btnMode[1], width, height));
        JLabel btnBack = new JLabel(scaleIcon(btnMode[2], width, height));

        btnJoin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnJoin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);

        box.add(btnJoin);
        box.add(Box.createRigidArea(new Dimension(0, 30)));
        box.add(btnBack);

        mainBox.add(box);
        panel.add(mainBox);

        setupButtonListeners(btnJoin, btnBack, width, height, ipField);
    }

    private void setupButtonListeners(JLabel btnJoin, JLabel btnBack, int width, int height, JTextField ipField) {
        btnJoin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                scaleButton(btnJoin, btnMode[1], width, height, 1.1);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnJoin.setIcon(scaleIcon(btnMode[1], width, height));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                String serverIP = ipField.getText().trim();
                if (serverIP.isEmpty()) {
                    serverIP = "localhost";
                }
                
                setVisible(false);
                SimpleLobby lobby = new SimpleLobby(modeSelector.getSkinIndex());
                lobby.setVisible(true);
                lobby.connectToServer(serverIP);
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