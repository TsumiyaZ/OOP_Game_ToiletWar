import javax.swing.*;
import java.awt.*;

public class Frame_WaitPlayer extends JFrame {
    Frame_Mode FrameMode;

    public Frame_WaitPlayer(Frame_Mode FrameMode) {
        this.FrameMode = FrameMode;
        setFrame();
    }

    private void setFrame() {
        setTitle(Config.NAME_GAME);
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        getContentPane().setBackground(new Color(20, 24, 37));

        JPanel Panel_North = new JPanel(new FlowLayout());

        JButton btn_Leave = createStyledButton("Leave Room");
        JButton btn_Start = createStyledButton("Start Game");

        Panel_North.add(btn_Leave);
        Panel_North.add(btn_Start);

    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);

        button.setFocusPainted(false);
        button.setForeground(Color.white);
        button.setFont(new Font("Segoe UI", Font.BOLD, 42));
        button.setBackground(new Color(60, 90, 170));

        button.setPreferredSize(new Dimension(220, 45));
        button.setMinimumSize(new Dimension(220, 45));
        button.setMinimumSize(new Dimension(220, 45));

        return button;
    }

}
