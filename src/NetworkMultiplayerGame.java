import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkMultiplayerGame extends JFrame {
    private static final int FINISH_LINE = 1100;
    private static final int CHARACTER_WIDTH = 120;
    private static final int CHARACTER_HEIGHT = 120;
    private static final int STEP_SIZE = 5;
    private static final int ROAD_START_Y = 280;
    private static final int LANE_HEIGHT = 80;

    private JLabel lblBackground;
    private JLabel lblWinner;
    private JLabel lblTimer;
    private JLabel lblCountdown;

    private Map<Integer, NetworkGameCharacter> characters;
    private GameTimer gameTimer;
    private GameCountdown gameCountdown;
    private Thread animationThread;
    private GameClient gameClient;

    private boolean finished = false;
    private boolean started = false;
    private int myPlayerId = -1;
    private String myPlayerName;

    public NetworkMultiplayerGame(GameClient client, String playerName) {
        this.gameClient = client;
        this.myPlayerName = playerName;
        characters = new HashMap<>();
        setupFrame();
        initializeComponents();
        setupControls();
        startGame();
    }

    private void setupFrame() {
        setTitle(Config.NAME_GAME + " - Network Multiplayer");
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void initializeComponents() {
        lblBackground = GameUI.createBackground();
        add(lblBackground);

        lblWinner = GameUI.createWinnerLabel();
        lblBackground.add(lblWinner);

        lblTimer = GameUI.createTimerLabel();
        lblBackground.add(lblTimer);

        lblCountdown = GameUI.createCountdownLabel();
        lblBackground.add(lblCountdown);

        gameTimer = new GameTimer(lblTimer);
        gameCountdown = new GameCountdown(lblCountdown, this::onCountdownComplete);
    }

    public void addPlayer(int playerId, String playerName, int skinIndex) {
        int laneIndex = characters.size();
        int yPosition = ROAD_START_Y + (laneIndex * LANE_HEIGHT);
        
        NetworkGameCharacter character = new NetworkGameCharacter(
            skinIndex, playerName, playerId, 0, yPosition, 
            CHARACTER_WIDTH, CHARACTER_HEIGHT, STEP_SIZE
        );
        
        characters.put(playerId, character);
        lblBackground.add(character.getLabel());
        lblBackground.add(character.getNameLabel());
        

    }

    public void addPlayers(List<NetworkPlayer> players, String myPlayerName) {
        for (int i = 0; i < players.size(); i++) {
            NetworkPlayer player = players.get(i);
            addPlayer(i, player.getPlayerName(), player.getSkinIndex());
            if (player.getPlayerName().equals(myPlayerName)) {
                myPlayerId = i;
                System.out.println("My player ID: " + myPlayerId + ", Name: " + myPlayerName);
            }
        }
    }

    private void setupControls() {
        addKeyListener(new KeyAdapter() {
            private boolean spacePressed = false;
            
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !spacePressed && !finished && started) {
                    spacePressed = true;
                    moveMyCharacter();
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
                    for (NetworkGameCharacter character : characters.values()) {
                        character.updateAnimation();
                        character.updateNamePosition();
                    }
                });

                try {
                    Thread.sleep(16);
                } catch (InterruptedException ignored) {}
            }
        });
        animationThread.start();
    }

    private void moveMyCharacter() {
        NetworkGameCharacter myCharacter = characters.get(myPlayerId);
        if (myCharacter != null && myCharacter.getX() < FINISH_LINE) {
            myCharacter.setMoving(true);
            myCharacter.move();
            
            if (gameClient != null) {
                gameClient.sendStep(myPlayerId);
                gameClient.sendMove(myPlayerId, myCharacter.getX());
            }
            
            Timer walkTimer = new Timer(200, e -> {
                myCharacter.setMoving(false);
                ((Timer) e.getSource()).stop();
            });
            walkTimer.setRepeats(false);
            walkTimer.start();
            
            checkWinCondition(myCharacter);
        }
    }

    public void syncPlayerPosition(int playerId, int newX) {
        NetworkGameCharacter character = characters.get(playerId);
        if (character != null && playerId != myPlayerId) {
            character.setMoving(true);
            character.setPosition(newX);
            character.updateNamePosition();
            
            Timer walkTimer = new Timer(200, e -> {
                character.setMoving(false);
                ((Timer) e.getSource()).stop();
            });
            walkTimer.setRepeats(false);
            walkTimer.start();
            
            checkWinCondition(character);
        }
    }

    public void movePlayer(int playerId) {
        NetworkGameCharacter character = characters.get(playerId);
        if (character != null && playerId != myPlayerId) {
            character.setMoving(true);
            character.move();
            character.updateNamePosition();
            
            Timer walkTimer = new Timer(200, e -> {
                character.setMoving(false);
                ((Timer) e.getSource()).stop();
            });
            walkTimer.setRepeats(false);
            walkTimer.start();
            
            checkWinCondition(character);
        }
    }

    private void checkWinCondition(NetworkGameCharacter character) {
        if (character.hasReachedFinish(FINISH_LINE) && !finished) {
            character.setPosition(FINISH_LINE);
            finished = true;
            gameTimer.stop();

            double seconds = gameTimer.getElapsedSeconds();
            lblWinner.setText(String.format("%s WINS! (%.2f s)", character.getPlayerName(), seconds));
            lblWinner.setVisible(true);
        }
    }
}