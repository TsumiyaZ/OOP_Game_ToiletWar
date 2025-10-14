import javax.swing.*;

class MainFrameGame extends JFrame {
    public MainFrameGame() {
        setFrame();
    }

    private void setFrame() {
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
    }
}