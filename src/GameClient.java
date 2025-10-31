import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class GameClient {
    private static final int PORT = 12345;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private List<NetworkPlayer> players;
    private SimpleLobby lobby;
    private NetworkMultiplayerGame game;

    public GameClient(SimpleLobby lobby) {
        this.lobby = lobby;
        this.players = new ArrayList<>();
    }

    public boolean connect(String serverIP) {
        try {
            socket = new Socket(serverIP, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            new Thread(this::listenForMessages).start();
            return true;
        } catch (IOException e) {
            System.err.println("Connection failed: " + e.getMessage());
            return false;
        }
    }

    public void joinGame(String playerName, int skinIndex) {
        sendMessage("JOIN:" + playerName + ":" + skinIndex);
    }

    public void setReady() {
        sendMessage("READY");
    }

    public void startGame() {
        sendMessage("START_GAME");
    }

    public void changeSkin(int skinIndex) {
        sendMessage("CHANGE_SKIN:" + skinIndex);
    }

    public void sendMove(int playerId, int newX) {
        sendMessage("MOVE:" + playerId + ":" + newX);
    }

    private void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    private void listenForMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                handleMessage(message);
            }
        } catch (IOException e) {
            System.err.println("Error listening for messages: " + e.getMessage());
        }
    }

    private void handleMessage(String message) {
        String[] parts = message.split(":");
        String command = parts[0];

        switch (command) {
            case "JOIN_SUCCESS":
                lobby.onJoinSuccess();
                break;
            case "PLAYER_LIST":
                if (parts.length > 1) {
                    parsePlayerList(parts[1]);
                    lobby.updatePlayerList(players);
                }
                break;
            case "CAN_START":
                lobby.enableStartButton();
                break;
            case "GAME_START":
                lobby.startMultiplayerGame();
                break;
            case "PLAYER_MOVE":
                if (game != null && parts.length > 2) {
                    int playerId = Integer.parseInt(parts[1]);
                    int newX = Integer.parseInt(parts[2]);
                    game.syncPlayerPosition(playerId, newX);
                }
                break;
        }
    }

    private void parsePlayerList(String playerData) {
        players.clear();
        if (!playerData.isEmpty()) {
            String[] playerEntries = playerData.split(";");
            for (String entry : playerEntries) {
                if (!entry.isEmpty()) {
                    String[] playerInfo = entry.split(",");
                    if (playerInfo.length >= 3) {
                        String name = playerInfo[0];
                        int skin = Integer.parseInt(playerInfo[1]);
                        boolean ready = Boolean.parseBoolean(playerInfo[2]);
                        NetworkPlayer player = new NetworkPlayer(name, skin, "");
                        player.setReady(ready);
                        players.add(player);
                    }
                }
            }
        }
    }

    public List<NetworkPlayer> getPlayers() {
        return new ArrayList<>(players);
    }

    public void setGame(NetworkMultiplayerGame game) {
        this.game = game;
    }

    public void disconnect() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Error disconnecting: " + e.getMessage());
        }
    }
}