import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Frame_Mode extends JFrame {
    private Frame_Menu Frame_menu;

    public Frame_Mode(Frame_Menu frame) {
        this.Frame_menu = frame;
        setFrame();
        setComponent();
    }

    private void setFrame() {
        setTitle(Config.NAME_GAME);
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        getContentPane().setBackground(new Color(20, 24, 37)); // พื้นหลังเข้มดูเท่
    }

    private void setComponent() {
        JPanel centerPanel = new JPanel(new GridLayout());
        centerPanel.setOpaque(false);
        add(centerPanel, BorderLayout.CENTER);

        Box box = Box.createVerticalBox();

        JLabel title = new JLabel("Choose Mode");
        title.setFont(new Font("Seqoe UI", Font.BOLD, 42));
        title.setForeground(new Color(255,255,255));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btn_Single = createStyledButton("Play Single");
        JButton btn_Multiplayer = createStyledButton("Play Multiplayer");
        JButton btn_back = createStyledButton("Back to Menu");


        box.add(title);
        box.add(btn_Single);
        box.add(Box.createRigidArea(new Dimension(0, 10)));
        box.add(btn_Multiplayer);
        box.add(Box.createRigidArea(new Dimension(0, 10)));
        box.add(btn_back);

        centerPanel.add(box);

        btn_Multiplayer.addActionListener(e -> {
            Frame_WaitPlayer frame_wait = new Frame_WaitPlayer(this);
            frame_wait.setVisible(true);
            setVisible(false);
        });

        btn_back.addActionListener(e -> {
            setVisible(false);
            Frame_menu.setVisible(true);
        });

    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(60, 90, 170));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.setPreferredSize(new Dimension(220, 45));
        button.setMaximumSize(new Dimension(220, 45));
        button.setMinimumSize(new Dimension(220, 45));

        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(75, 110, 200));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(60, 90, 170));
            }
        });

        return button;
    }
}
