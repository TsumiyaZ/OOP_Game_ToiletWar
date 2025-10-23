import javax.swing.*;
import java.awt.*;

public class Frame_CreateJoin extends JFrame {
    Frame_Mode FrameMode;

    public Frame_CreateJoin(Frame_Mode frame_mode) {
        this.FrameMode = frame_mode;
        setFrame();
        setComponent();
    }

    private void setFrame() {
        setTitle(Config.NAME_GAME);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setResizable(false);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(20, 24, 37));
    }

    private void setComponent() {
        JPanel panel_center = new JPanel(new GridBagLayout());
        panel_center.setOpaque(false);
        add(panel_center, BorderLayout.CENTER);

        Box box = Box.createVerticalBox();

        JButton btn_createRoom = createStyledButton("Create Room");
        JButton btn_JoinRoom = createStyledButton("Join Room");
        JButton btn_Back = createStyledButton("Back to Mode");

        box.add(btn_createRoom);
        box.add(Box.createRigidArea(new Dimension(0, 10)));
        box.add(btn_JoinRoom);
        box.add(Box.createRigidArea(new Dimension(0, 10)));
        box.add(btn_Back);

        box.setAlignmentY(Component.CENTER_ALIGNMENT);
        box.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel_center.add(box);

        btn_Back.addActionListener(e -> {
            setVisible(false);
            FrameMode.setVisible(true);
        });
    }

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
