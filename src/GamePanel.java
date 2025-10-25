import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    public GamePanel() {
        setPreferredSize(new Dimension(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT));
        setBackground(Color.BLACK);
        setLayout(null);
        createComponent();
    }

    private void createComponent() {
        setLayout(null); // สำคัญ ต้องมี

        JButton btnPrev = new JButton("<- Previous");
        JButton btnNext = new JButton("Next ->");
        JButton btnEnter = new JButton("Enter");

        int panelW = Config.WINDOW_WIDTH;
        int panelH = Config.WINDOW_HEIGHT;

        int btnW = 130;
        int btnH = 40;

        // จัดให้ปุ่ม Prev/Next อยู่กลางแนวนอน ระดับเดียวกัน
        int yButtons = 10;
        btnPrev.setBounds(panelW / 2 - 200, yButtons, btnW, btnH);
        btnNext.setBounds(panelW / 2 + 70, yButtons, btnW, btnH);

        // ปุ่ม Enter อยู่ตรงกลางล่างพอดี
        int xEnter = (panelW - btnW) / 2;
        int yEnter = panelH - 120;
        btnEnter.setBounds(xEnter, yEnter, btnW, btnH);

        // ตั้งค่าฟอนต์และสีเดิม
        btnPrev.setFont(new Font("Arial", Font.BOLD, 14));
        btnNext.setFont(new Font("Arial", Font.BOLD, 14));
        btnEnter.setFont(new Font("Arial", Font.BOLD, 16));

        btnPrev.setBackground(new Color(70, 130, 180));
        btnNext.setBackground(new Color(70, 130, 180));
        btnEnter.setBackground(new Color(34, 139, 34));

        btnPrev.setForeground(Color.WHITE);
        btnNext.setForeground(Color.WHITE);
        btnEnter.setForeground(Color.WHITE);

        btnPrev.setFocusPainted(false);
        btnNext.setFocusPainted(false);
        btnEnter.setFocusPainted(false);

        btnPrev.setBorder(BorderFactory.createRaisedBevelBorder());
        btnNext.setBorder(BorderFactory.createRaisedBevelBorder());
        btnEnter.setBorder(BorderFactory.createRaisedBevelBorder());

        add(btnPrev);
        add(btnNext);
        add(btnEnter);
    }

}
