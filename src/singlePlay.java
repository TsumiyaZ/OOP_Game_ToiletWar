import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

public class singlePlay extends JFrame {
    private int indexSkin;
    private int charX, charY, charWidth, charHeight;
    private int step = 20;

    private JLabel lblBackground;
    private JLabel lblCharacter;
    private JLabel lblWinner;
    private JLabel lblTimer;
    private JLabel lblCountdown;

    private boolean isPressed = false;
    private boolean finished = false;
    private boolean started = false;

    private long startNano;
    private Thread timerThread;          // [ADD CHATGPT] Thread สำหรับจับเวลา
    private Thread countdownThread;      // [ADD CHATGPT] Thread สำหรับนับถอยหลัง

    public singlePlay(int indexSkin) {
        this.indexSkin = indexSkin;

        setFrame();
        loadImages();
        setCharacter();
        setWinnerLabel();
        setTimerLabel();
        setCountdownLabel();
        setControls();

        startCountdown(); // [ADD CHATGPT]
    }

    private void setFrame() {
        setTitle(Config.NAME_GAME + " - Single Mode");
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void loadImages() {
        String bgPath = System.getProperty("user.dir") + File.separator +
                "assets" + File.separator + "bg" + File.separator + Config.backgroundGame;
        ImageIcon bgIcon = new ImageIcon(bgPath);
        Image bgScaled = bgIcon.getImage().getScaledInstance(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT, Image.SCALE_SMOOTH);
        lblBackground = new JLabel(new ImageIcon(bgScaled));
        lblBackground.setBounds(0, 0, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        add(lblBackground);

        String charPath = System.getProperty("user.dir") + File.separator +
                "assets" + File.separator + "Skin" + File.separator + Config.character_skin[indexSkin];
        ImageIcon charIcon = new ImageIcon(charPath);
        lblCharacter = new JLabel(charIcon);
        lblBackground.add(lblCharacter);
    }

    private void setCharacter() {
        Image img = ((ImageIcon) lblCharacter.getIcon()).getImage();
        charWidth = 200;
        int sw = img.getWidth(this);
        int sh = img.getHeight(this);
        charHeight = (int) ((double) sh / Math.max(1, sw) * charWidth);

        charX = 0;
        charY = 200;

        lblCharacter.setBounds(charX, charY, charWidth, charHeight);
        lblCharacter.setIcon(new ImageIcon(img.getScaledInstance(charWidth, charHeight, Image.SCALE_SMOOTH)));
    }

    private void setWinnerLabel() {
        lblWinner = new JLabel("", SwingConstants.CENTER);
        lblWinner.setFont(new Font("Arial", Font.BOLD, 60));
        lblWinner.setForeground(Color.YELLOW);
        lblWinner.setBounds(0, Config.WINDOW_HEIGHT / 2 - 100, Config.WINDOW_WIDTH, 100);
        lblWinner.setVisible(false);
        lblBackground.add(lblWinner);
    }

    private void setTimerLabel() {
        lblTimer = new JLabel("Time: 0.0 s", SwingConstants.RIGHT);
        lblTimer.setFont(new Font("Arial", Font.BOLD, 24));
        lblTimer.setForeground(Color.WHITE);
        lblTimer.setBounds(Config.WINDOW_WIDTH - 220, 20, 200, 40);
        lblBackground.add(lblTimer);
    }

    private void setCountdownLabel() {
        lblCountdown = new JLabel("", SwingConstants.CENTER);
        lblCountdown.setFont(new Font("Arial", Font.BOLD, 80));
        lblCountdown.setForeground(Color.ORANGE);
        lblCountdown.setBounds(0, Config.WINDOW_HEIGHT / 2 - 150, Config.WINDOW_WIDTH, 150);
        lblBackground.add(lblCountdown);
    }

    // =================== ใช้ Thread แทน Swing Timer ===================
    private void startCountdown() {
        countdownThread = new Thread(() -> {
            try {
                for (int i = 5; i > 0; i--) {
                    int n = i;
                    lblCountdown.setText(String.valueOf(n));
                    Thread.sleep(1000);
                }

                lblCountdown.setText("GO!");
                Thread.sleep(800);
                lblCountdown.setVisible(false);

                started = true;
                startTimerThread(); // เริ่มจับเวลา
            } catch (InterruptedException ignored) {}
        });
        countdownThread.start();
    }

    private void startTimerThread() {
        startNano = System.nanoTime();

        timerThread = new Thread(() -> {
            while (!finished && started) {
                long elapsedNano = System.nanoTime() - startNano;
                double seconds = elapsedNano / 1_000_000_000.0;
                        lblTimer.setText(String.format("Time: %.1f s", seconds));
                try {
                    Thread.sleep(50); // อัปเดตทุก 0.05 วินาที
                } catch (InterruptedException ignored) {}
            }
        });
        timerThread.start();
    }

    private void setControls() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !isPressed && !finished && started) {
                    isPressed = true;
                    moveCharacter();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    isPressed = false;
                }
            }
        });
    }

    private void moveCharacter() {
        charX += step;

        if (charX >= 1100) {
            charX = 1100;
            finished = true;

            long elapsedNano = System.nanoTime() - startNano;
            System.out.println("System.nanotime : " + System.nanoTime());
            System.out.println("ElapsedNano : " + elapsedNano);
            System.out.println("startNano " + startNano);
            double seconds = elapsedNano / 1_000_000_000.0;
            System.out.println("Second : " + seconds);

            lblWinner.setText(String.format("WINNER! (%.2f s)", seconds));
            lblWinner.setVisible(true);
        }

        lblCharacter.setLocation(charX, charY);
    }
}
