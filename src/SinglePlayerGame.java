import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SinglePlayerGame extends JFrame {
    private static final int FINISH_LINE = 1100;
    private static final int CHARACTER_WIDTH = 150;
    private static final int CHARACTER_HEIGHT = 150;
    private static final int CHARACTER_Y = 230;
    private static final int STEP_SIZE = 5;

    private JLabel lblBackground;
    private JLabel lblWinner;
    private JLabel lblTimer;
    private JLabel lblCountdown;

    private GameCharacter character;
    private GameTimer gameTimer;
    private GameCountdown gameCountdown;
    private Thread animationThread;

    private boolean finished = false;
    private boolean started = false;

    public SinglePlayerGame(int skinIndex) {
        setupFrame();
        initializeComponents(skinIndex);
        setupControls();
        startGame();
    }

    private void setupFrame() {
        setTitle(Config.NAME_GAME + " - Single Mode");
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void initializeComponents(int skinIndex) {
        lblBackground = GameUI.createBackground();
        add(lblBackground);

        character = new GameCharacter(skinIndex, 0, CHARACTER_Y, CHARACTER_WIDTH, CHARACTER_HEIGHT, STEP_SIZE);
        lblBackground.add(character.getLabel());

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
            private boolean spacePressed = false;
            
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !spacePressed && !finished && started) {
                    spacePressed = true;
                    moveCharacterOneStep();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    spacePressed = false;
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
                    character.updateAnimation();
                });

                try {
                    Thread.sleep(16);
                } catch (InterruptedException ignored) {}
            }
        });
        animationThread.start();
    }

    private void moveCharacterOneStep() {
        if (character.getX() < FINISH_LINE) {
            character.setMoving(true);
            character.move();
            
            Timer walkTimer = new Timer(200, e -> {
                character.setMoving(false);
                ((Timer) e.getSource()).stop();
            });
            walkTimer.setRepeats(false);
            walkTimer.start();
            
            checkWinCondition();
        }
    }

    private void checkWinCondition() {
        if (character.hasReachedFinish(FINISH_LINE)) {
            character.setPosition(FINISH_LINE);
            finished = true;
            gameTimer.stop();

            double seconds = gameTimer.getElapsedSeconds();
            lblWinner.setText(String.format("WINNER! (%.2f s)", seconds));
            lblWinner.setVisible(true);
        }
    }
}