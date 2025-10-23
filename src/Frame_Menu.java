import javax.swing.*;
import java.awt.*;

class Frame_Menu extends JFrame {
    private Frame_Mode Frame_Mode = new Frame_Mode(this);

    public Frame_Menu() {
        setFrame();
        setComponent();
    }

    private void setFrame() {
        setTitle("Toilet War Game");
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        getContentPane().setBackground(new Color(20, 24, 37)); // พื้นหลังเข้มดูเท่
    }

    private void setComponent() {
        // ใช้ GridBagLayout ให้อยู่กลางจอจริง ๆ
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false); // โปร่งใส เห็นพื้นหลังข้างหลัง
        add(centerPanel, BorderLayout.CENTER);

        // กล่องแนวตั้ง
        Box box = Box.createVerticalBox();

        // ชื่อเกม
        JLabel title = new JLabel(Config.NAME_GAME);
        title.setFont(new Font("Segoe UI", Font.BOLD, 42));
        title.setForeground(new Color(255, 255, 255));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Click Start for Play Game");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(new Color(180, 180, 200));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ปุ่ม
        JButton btnStart = createStyledButton("Start Game");
        JButton btnExit = createStyledButton("Exit");

        // จัด layout
        box.add(title);
        box.add(Box.createRigidArea(new Dimension(0, 8)));
        box.add(subtitle);
        box.add(Box.createRigidArea(new Dimension(0, 25)));
        box.add(btnStart);
        box.add(Box.createRigidArea(new Dimension(0, 10)));
        box.add(btnExit);

        centerPanel.add(box); // เอากล่องไปไว้ตรงกลาง

        // Action
        btnStart.addActionListener(e -> {
            setVisible(false);
            Frame_Mode.setVisible(true);
        });
        btnExit.addActionListener(e -> System.exit(0));
    }

    // ปุ่มสไตล์เดียวกัน
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(60, 90, 170));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension size = new Dimension(220, 45);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setMinimumSize(size);

        // ขอบโค้งเล็กน้อย
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // เอฟเฟกต์ hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(75, 110, 200));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(60, 90, 170));
            }
        });

        return button;
    }
}
