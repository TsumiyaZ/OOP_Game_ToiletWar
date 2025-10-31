import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SimpleLobby extends JFrame {
    private GameClient client;
    private JList<String> playerList;
    private DefaultListModel<String> listModel;
    private JButton readyButton;
    private JLabel statusLabel;
    private int mySkinIndex = 0;

    public SimpleLobby() {
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

        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener(e -> goBack());
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void connectToServer(String serverIP) {
        client = new GameClient(this);
        if (client.connect(serverIP)) {
            String playerName = JOptionPane.showInputDialog(this, "Enter your name:", "Player Name", JOptionPane.QUESTION_MESSAGE);
            if (playerName == null || playerName.trim().isEmpty()) {
                playerName = "Player" + System.currentTimeMillis() % 1000;
            }
            client.joinGame(playerName.trim(), mySkinIndex);
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
            statusLabel.setText("Players: " + players.size() + " | Ready: " + readyCount + " | Need 3+ ready players to start");
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

    public void startMultiplayerGame() {
        SwingUtilities.invokeLater(() -> {
            setVisible(false);
            new NetworkMultiplayerGame();
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