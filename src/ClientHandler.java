import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private GameServer server;
    private PrintWriter out;
    private BufferedReader in;
    private NetworkPlayer player;

    public ClientHandler(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                handleMessage(inputLine);
            }
        } catch (IOException e) {
            System.err.println("Client handler error: " + e.getMessage());
        } finally {
            cleanup();
        }
    }

    private void handleMessage(String message) {
        String[] parts = message.split(":");
        String command = parts[0];

        switch (command) {
            case "JOIN":
                if (parts.length >= 3) {
                    String playerName = parts[1];
                    int skinIndex = Integer.parseInt(parts[2]);
                    player = new NetworkPlayer(playerName, skinIndex, socket.getInetAddress().getHostAddress());
                    server.addPlayer(player);
                    sendMessage("JOIN_SUCCESS");
                }
                break;
            case "READY":
                if (player != null) {
                    player.setReady(true);
                    server.broadcastPlayerList();
                    if (server.canStartGame()) {
                        sendMessage("CAN_START");
                    }
                }
                break;
            case "START_GAME":
                if (player != null && server.canStartGame()) {
                    server.startGame();
                }
                break;
            case "CHANGE_SKIN":
                if (player != null && parts.length >= 2) {
                    int newSkin = Integer.parseInt(parts[1]);
                    player.setSkinIndex(newSkin);
                    server.broadcastPlayerList();
                }
                break;
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    private void cleanup() {
        try {
            if (player != null) {
                server.removePlayer(player);
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Error cleaning up client: " + e.getMessage());
        }
    }
}