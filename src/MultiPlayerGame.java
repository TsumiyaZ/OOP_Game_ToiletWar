import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MultiPlayerGame extends JFrame {
    private static final int FINISH_LINE = 1100;
    private static final int CHARACTER_WIDTH = 150;
    private static final int CHARACTER_HEIGHT = 150;
    private static final int PLAYER1_Y = 180;
    private static final int PLAYER2_Y = 350;
    private static final int STEP_SIZE = 5;

    private JLabel lblBackground;
    private JLabel lblWinner;
    private JLabel lblTimer;
    private JLabel lblCountdown;

    private GameCharacter player1;
    private GameCharacter player2;
    private GameTimer gameTimer;
    private GameCountdown gameCountdown;
    private Thread animationThread;

    private boolean isPressed1 = false;
    private boolean isPressed2 = false;
    private boolean finished = false;
    private boolean started = false;

    public MultiPlayerGame(int skinIndex1, int skinIndex2) {
        setupFrame();
        initializeComponents(skinIndex1, skinIndex2);
        setupControls();
        startGame();
    }

    private void setupFrame() {
        setTitle(Config.NAME_GAME + " - Multiplayer Mode");
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void initializeComponents(int skinIndex1, int skinIndex2) {
        lblBackground = GameUI.createBackground();
        add(lblBackground);

        player1 = new GameCharacter(skinIndex1, 0, PLAYER1_Y, CHARACTER_WIDTH, CHARACTER_HEIGHT, STEP_SIZE);
        player2 = new GameCharacter(skinIndex2, 0, PLAYER2_Y, CHARACTER_WIDTH, CHARACTER_HEIGHT, STEP_SIZE);
        
        lblBackground.add(player1.getLabel());
        lblBackground.add(player2.getLabel());

        lblWinner = GameUI.createWinnerLabel();
        lblBackground.add(lblWinner);

        lblTimer = GameUI.createTimerLabel();
        lblBackground.add(lblTimer);

        lblCountdown = GameUI.createCountdownLabel();
        lblBackground.add(lblCountdown);

        gameTimer = new GameTimer(lblTimer);
        gameCountdown = new GameCountdown(lblCountdown, this::onCountdownComplete);
    }

    private void setupControls() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!finished && started) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE && !isPressed1) {
                        isPressed1 = true;
                        player1.setMoving(true);
                    }
                    if (e.getKeyCode() == KeyEvent.VK_ENTER && !isPressed2) {
                        isPressed2 = true;
                        player2.setMoving(true);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    isPressed1 = false;
                    player1.setMoving(false);
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    isPressed2 = false;
                    player2.setMoving(false);
                }
            }
        });
    }

    private void startGame() {
        startAnimation();
        gameCountdown.start();
    }

    private void onCountdownComplete() {
        started = true;
        gameTimer.start();
    }

    private void startAnimation() {
        animationThread = new Thread(() -> {
            while (!finished) {
                SwingUtilities.invokeLater(() -> {
                    player1.updateAnimation();
                    player2.updateAnimation();
                    
                    if (started) {
                        if (player1.getX() < FINISH_LINE) {
                            player1.move();
                        }
                        if (player2.getX() < FINISH_LINE) {
                            player2.move();
                        }
                        checkWinCondition();
                    }
                });

                try {
                    Thread.sleep(16);
                } catch (InterruptedException ignored) {}
            }
        });
        animationThread.start();
    }

    private void checkWinCondition() {
        if (player1.hasReachedFinish(FINISH_LINE) && !finished) {
            player1.setPosition(FINISH_LINE);
            finished = true;
            gameTimer.stop();
            double seconds = gameTimer.getElapsedSeconds();
            lblWinner.setText(String.format("PLAYER 1 WINS! (%.2f s)", seconds));
            lblWinner.setVisible(true);
        } else if (player2.hasReachedFinish(FINISH_LINE) && !finished) {
            player2.setPosition(FINISH_LINE);
            finished = true;
            gameTimer.stop();
            double seconds = gameTimer.getElapsedSeconds();
            lblWinner.setText(String.format("PLAYER 2 WINS! (%.2f s)", seconds));
            lblWinner.setVisible(true);
        }
    }
}