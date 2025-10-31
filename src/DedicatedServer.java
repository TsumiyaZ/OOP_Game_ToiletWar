public class DedicatedServer {
    public static void main(String[] args) {
        System.out.println("=== Toilet War Game Server ===");
        System.out.println("Starting server on port 12345...");
        
        GameServer server = new GameServer();
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down server...");
            server.stop();
        }));
        
        server.start();
    }
}