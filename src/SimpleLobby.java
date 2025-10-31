import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SimpleLobby extends JFrame {
    private GameClient client;
    private JList<String> playerList;
    private DefaultListModel<String> listModel;
    private JButton readyButton;
    private JButton startButton;
    private JLabel statusLabel;
    private int mySkinIndex = 0;
    private String myPlayerName;

    public SimpleLobby(int skinIndex) {
        this.mySkinIndex = skinIndex;
        setupFrame();
        createComponents();
    }

    private void setupFrame() {
        setTitle(Config.NAME_GAME + " - Multiplayer Lobby");
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void createComponents() {
        JPanel topPanel = new JPanel(new FlowLayout());
        statusLabel = new JLabel("Connecting to server...");
        topPanel.add(statusLabel);
        add(topPanel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        playerList = new JList<>(listModel);
        playerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(playerList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Players in Lobby"));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        readyButton = new JButton("Ready");
        readyButton.setEnabled(false);
        readyButton.addActionListener(e -> setReady());
        buttonPanel.add(readyButton);

        startButton = new JButton("Start Game");
        startButton.setEnabled(false);
        startButton.addActionListener(e -> startGame());
        buttonPanel.add(startButton);

        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> goBack());
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void connectToServer(String serverIP) {
        client = new GameClient(this);
        if (client.connect(serverIP)) {
            myPlayerName = JOptionPane.showInputDialog(this, "Enter your name:", "Player Name", JOptionPane.QUESTION_MESSAGE);
            if (myPlayerName == null || myPlayerName.trim().isEmpty()) {
                myPlayerName = "Player" + System.currentTimeMillis() % 1000;
            }
            myPlayerName = myPlayerName.trim();
            client.joinGame(myPlayerName, mySkinIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to connect to server!\nMake sure the server is running.", "Connection Error", JOptionPane.ERROR_MESSAGE);
            goBack();
        }
    }

    public void onJoinSuccess() {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText("Connected to lobby - Wait for other players");
            readyButton.setEnabled(true);
        });
    }

    public void updatePlayerList(List<NetworkPlayer> players) {
        SwingUtilities.invokeLater(() -> {
            listModel.clear();
            for (NetworkPlayer player : players) {
                String status = player.isReady() ? " ✓ Ready" : " ⏳ Not Ready";
                listModel.addElement(player.getPlayerName() + status);
            }
            
            long readyCount = players.stream().mapToLong(p -> p.isReady() ? 1 : 0).sum();
            boolean canStart = players.size() >= Config.PLAYER_READY_TO_START && readyCount == players.size();
            startButton.setEnabled(canStart);
            
            if (canStart) {
                statusLabel.setText("Players: " + players.size() + " | Ready: " + readyCount + " | Ready to start!");
                startButton.setBackground(java.awt.Color.GREEN);
            } else {
                statusLabel.setText("Players: " + players.size() + " | Ready: " + readyCount + " | Need "+ Config.PLAYER_READY_TO_START +"+ ready players to start");
                startButton.setBackground(null);
            }
        });
    }

    public void enableStartButton() {
    }

    private void setReady() {
        if (client != null) {
            client.setReady();
            readyButton.setEnabled(false);
            readyButton.setText("Ready! ✓");
            readyButton.setBackground(Color.GREEN);
        }
    }

    private void startGame() {
        if (client != null) {
            client.startGame();
        }
    }

    public void startMultiplayerGame() {
        SwingUtilities.invokeLater(() -> {
            setVisible(false);
            NetworkMultiplayerGame game = new NetworkMultiplayerGame(client, myPlayerName);
            if (client != null) {
                game.addPlayers(client.getPlayers(), myPlayerName);
                client.setGame(game);
            }
        });
    }

    private void goBack() {
        if (client != null) {
            client.disconnect();
        }
        setVisible(false);
        new ModeSelector(mySkinIndex).setVisible(true);
    }

    @Override
    public void dispose() {
        if (client != null) {
            client.disconnect();
        }
        super.dispose();
    }
}