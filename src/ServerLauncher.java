import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerLauncher extends JFrame {
    private GameServer server;
    private JButton startButton;
    private JButton stopButton;
    private JTextArea logArea;
    private JLabel statusLabel;
    private boolean serverRunning = false;

    public ServerLauncher() {
        setupFrame();
        createComponents();
    }

    private void setupFrame() {
        setTitle("Toilet War - Server Launcher");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void createComponents() {
        JPanel topPanel = new JPanel(new FlowLayout());
        statusLabel = new JLabel("Server Status: Stopped");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        topPanel.add(statusLabel);
        add(topPanel, BorderLayout.NORTH);

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setBackground(Color.BLACK);
        logArea.setForeground(Color.GREEN);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Server Log"));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        startButton = new JButton("Start Server");
        startButton.addActionListener(e -> startServer());
        buttonPanel.add(startButton);

        stopButton = new JButton("Stop Server");
        stopButton.setEnabled(false);
        stopButton.addActionListener(e -> stopServer());
        buttonPanel.add(stopButton);

        JButton clearLogButton = new JButton("Clear Log");
        clearLogButton.addActionListener(e -> logArea.setText(""));
        buttonPanel.add(clearLogButton);

        JButton copyIPButton = new JButton("Copy IP");
        copyIPButton.addActionListener(e -> copyIPToClipboard());
        buttonPanel.add(copyIPButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addLog("Server launcher ready. Click 'Start Server' to begin.");
    }

    private void startServer() {
        if (!serverRunning) {
            server = new GameServer();
            new Thread(() -> {
                addLog("Starting server on port 12345...");
                server.start();
            }).start();
            
            String serverIP = getLocalIPAddress();
            serverRunning = true;
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            statusLabel.setText("Server Status: Running on " + serverIP + ":12345");
            statusLabel.setForeground(Color.GREEN);
            addLog("Server started successfully!");
            addLog("Server IP: " + serverIP);
            addLog("Players can connect using: " + serverIP);
            addLog("Local players can use: localhost or 127.0.0.1");
        }
    }

    private void stopServer() {
        if (serverRunning && server != null) {
            server.stop();
            serverRunning = false;
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            statusLabel.setText("Server Status: Stopped");
            statusLabel.setForeground(Color.RED);
            addLog("Server stopped.");
        }
    }

    private void addLog(String message) {
        SwingUtilities.invokeLater(() -> {
            String timestamp = java.time.LocalTime.now().toString().substring(0, 8);
            logArea.append("[" + timestamp + "] " + message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    private String getLocalIPAddress() {
        try {
            java.net.InetAddress localHost = java.net.InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (Exception e) {
            return "localhost";
        }
    }

    private void copyIPToClipboard() {
        String serverIP = getLocalIPAddress();
        java.awt.datatransfer.StringSelection stringSelection = new java.awt.datatransfer.StringSelection(serverIP);
        java.awt.datatransfer.Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        
        addLog("IP address copied to clipboard: " + serverIP);
        JOptionPane.showMessageDialog(this, 
            "Server IP copied to clipboard: " + serverIP + "\n\nShare this IP with other players!", 
            "IP Copied", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void dispose() {
        if (serverRunning && server != null) {
            server.stop();
        }
        super.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ServerLauncher().setVisible(true);
        });
    }
}