import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class HowToPlayFrame extends JFrame {
    ModeSelector mode;

    public HowToPlayFrame(ModeSelector mode) {
        this.mode = mode;
        setTitle("How to Play");
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        add(new HowToPlayPanel(this.mode));

        setVisible(true);
    }
}

class HowToPlayPanel extends JPanel {
    private Image bg;
    private Image backImg;
    private JButton btnBack;

    public HowToPlayPanel(ModeSelector mode) {
        setLayout(null);

        // โหลดภาพพื้นหลัง
        String bgPath = System.getProperty("user.dir") + File.separator +
                "assets" + File.separator + "bg" + File.separator + "How.png";
        bg = new ImageIcon(bgPath).getImage();

        // โหลดภาพปุ่ม Back
        String backPath = System.getProperty("user.dir") + File.separator +
                "assets" + File.separator + "obj" + File.separator + "TextBack.png";
        backImg = new ImageIcon(backPath).getImage();

        // ปุ่ม Back
        btnBack = new JButton(new ImageIcon(backImg));
        btnBack.setBounds(Config.WINDOW_WIDTH - 230, Config.WINDOW_HEIGHT - 130, 180, 70);
        btnBack.setBorderPainted(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setFocusPainted(false);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effect
        btnBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnBack.setSize(190, 75);
                btnBack.setLocation(Config.WINDOW_WIDTH - 235, Config.WINDOW_HEIGHT - 135);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnBack.setSize(180, 70);
                btnBack.setLocation(Config.WINDOW_WIDTH - 230, Config.WINDOW_HEIGHT - 130);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.getWindowAncestor(HowToPlayPanel.this).dispose();
                mode.setVisible(true);
            }
        });

        add(btnBack);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bg != null)
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }
}
