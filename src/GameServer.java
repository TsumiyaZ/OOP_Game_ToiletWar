import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameServer {
    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private List<ClientHandler> clients;
    private List<NetworkPlayer> players;
    private boolean gameStarted = false;

    public GameServer() {
        clients = new CopyOnWriteArrayList<>();
        players = new CopyOnWriteArrayList<>();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);
            
            while (!gameStarted) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    public void addPlayer(NetworkPlayer player) {
        players.add(player);
        broadcastPlayerList();
    }

    public void removePlayer(NetworkPlayer player) {
        players.remove(player);
        broadcastPlayerList();
    }

    public List<NetworkPlayer> getPlayers() {
        return new ArrayList<>(players);
    }

    public boolean canStartGame() {
        return players.size() >= Config.PLAYER_READY_TO_START && players.stream().allMatch(NetworkPlayer::isReady);
    }

    public void startGame() {
        if (canStartGame()) {
            gameStarted = true;
            broadcastMessage("GAME_START");
        }
    }

    public void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public void broadcastPlayerList() {
        StringBuilder playerList = new StringBuilder("PLAYER_LIST:");
        for (NetworkPlayer player : players) {
            playerList.append(player.getPlayerName()).append(",")
                     .append(player.getSkinIndex()).append(",")
                     .append(player.isReady()).append(";");
        }
        broadcastMessage(playerList.toString());
    }

    public void broadcastMove(int playerId, int newX) {
        broadcastMessage("PLAYER_MOVE:" + playerId + ":" + newX);
    }

    public void broadcastStep(int playerId) {
        broadcastMessage("PLAYER_STEP:" + playerId);
    }

    public void stop() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error stopping server: " + e.getMessage());
        }
    }
}