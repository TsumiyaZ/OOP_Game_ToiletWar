import javax.swing.*;
import java.awt.*;

public class Frame_WaitPlayer extends JFrame {
    private Frame_Mode Frame_mode;

    Frame_WaitPlayer(Frame_Mode frame_mode) {
        this.Frame_mode = frame_mode;
        setFrame();
    }

    private void setFrame() {
        setTitle("Toilet War Game");
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        getContentPane().setBackground(new Color(20, 24, 37));
    }
}
