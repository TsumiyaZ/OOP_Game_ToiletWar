import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Frame_WaitPlayer extends JFrame {
    Frame_Mode FrameMode;
    private JTextArea playerListArea;      // [ADDED BY GPT] พื้นที่แสดงรายชื่อผู้เล่น
    private JButton btn_Leave, btn_Start;  // [ADDED BY GPT] ปุ่มควบคุม
    private ServerSocket serverSocket;     // [ADDED BY GPT] ใช้เมื่อเป็น Host
    private ArrayList<Socket> playerSockets = new ArrayList<>(); // [ADDED BY GPT] เก็บ socket ของผู้เล่น
    private boolean isHost;                // [ADDED BY GPT] บอกสถานะว่าเป็น Host หรือ Client

    // [ADDED BY GPT] Constructor ใหม่ที่รับค่า isHost
    public Frame_WaitPlayer(Frame_Mode FrameMode, boolean isHost) {
        this.FrameMode = FrameMode;
        this.isHost = isHost;
        setFrame();

        // [ADDED BY GPT] เลือกโหมดการทำงาน
        if (isHost) {
            startServer();
        } else {
            connectToServer();
        }
    }

    private void setFrame() {
        setTitle(Config.NAME_GAME + " - Waiting Room");  // [ADDED BY GPT]
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        getContentPane().setBackground(new Color(20, 24, 37));

        // [ADDED BY GPT] ส่วนบน - ปุ่ม Leave / Start
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setOpaque(false);

        btn_Leave = createStyledButton("Leave Room");
        btn_Start = createStyledButton("Start Game");

        topPanel.add(btn_Leave);
        topPanel.add(btn_Start);
        add(topPanel, BorderLayout.NORTH);

        // [ADDED BY GPT] ส่วนกลาง - รายชื่อผู้เล่น
        playerListArea = new JTextArea();
        playerListArea.setEditable(false);
        playerListArea.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        playerListArea.setForeground(Color.WHITE);
        playerListArea.setBackground(new Color(30, 35, 50));
        playerListArea.setText(isHost ? "Waiting for players to join...\n" : "Connecting to host...\n");
        add(new JScrollPane(playerListArea), BorderLayout.CENTER);

        // [ADDED BY GPT] ปุ่ม Leave
        btn_Leave.addActionListener(e -> {
            try {
                // [ADDED BY GPT] ถ้าเป็น client ให้ส่งข้อความ LEAVE ไปแจ้ง host ก่อนปิด
                if (!isHost && socket != null && !socket.isClosed()) {
                    OutputStream out = socket.getOutputStream();
                    out.write("LEAVE\n".getBytes());
                    out.flush();
                    socket.close();
                }
                if (isHost && serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            dispose();
            FrameMode.setVisible(true);
        });


        // [ADDED BY GPT] ปุ่ม Start
        btn_Start.addActionListener(e -> {
            if (isHost) {
                JOptionPane.showMessageDialog(this, "Game starting for " + playerSockets.size() + " players!");
                dispose();
                // TODO: เปิด Frame เกมจริง
            } else {
                JOptionPane.showMessageDialog(this, "Waiting for host to start...");
            }
        });

        setVisible(true);
    }

    // [ADDED BY GPT] ปุ่มสไตล์เดียวกัน
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setBackground(new Color(60, 90, 170));
        button.setPreferredSize(new Dimension(180, 40));
        return button;
    }

    // ======================
    // [ADDED BY GPT] 🧑‍💻 Host Mode
    // ======================
    private void startServer() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(7777);
                updatePlayerList("Server started on port 7777\n");

                while (playerSockets.size() < 3) {
                    Socket socket = serverSocket.accept();
                    playerSockets.add(socket);
                    updatePlayerList("Player " + playerSockets.size() + " joined: " + socket.getInetAddress() + "\n");

                    // [ADDED BY GPT] สร้าง thread สำหรับตรวจจับข้อความจาก player
                    new Thread(() -> handleClient(socket)).start();
                }

                updatePlayerList("Room full! Ready to start!\n");
            } catch (IOException e) {
                updatePlayerList("Server closed.\n");
            }
        }).start();
    }

    // [ADDED BY GPT] ตัวแปรไว้เก็บ socket ของ client
    private Socket socket;

    // ======================
    // [ADDED BY GPT] 👥 Client Mode
    // ======================
    private void connectToServer() {
        new Thread(() -> {
            String ip = JOptionPane.showInputDialog(this, "Enter host IP address:", "192.168.1.100");
            if (ip == null || ip.isEmpty()) {
                updatePlayerList("No IP entered. Returning...");
                return;
            }

            try {
                socket = new Socket(ip, 7777);
                updatePlayerList("Connected to host at " + ip + "\n");
            } catch (IOException e) {
                updatePlayerList("Failed to connect to host.\n");
            }
        }).start();
    }

    // [ADDED BY GPT] เพิ่มเมธอด handleClient() ใน Frame_WaitPlayer
    private void handleClient(Socket socket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if ("LEAVE".equals(line.trim())) {
                    updatePlayerList("Player left the room: " + socket.getInetAddress() + "\n");
                    playerSockets.remove(socket);
                    break;
                }
            }
        } catch (IOException e) {
            updatePlayerList("Player disconnected: " + socket.getInetAddress() + "\n");
            playerSockets.remove(socket);
        } finally {
            try { socket.close(); } catch (IOException ex) {}
        }
    }


    // [ADDED BY GPT] เมธอดอัปเดตข้อความใน TextArea จาก thread อื่น
    private void updatePlayerList(String text) {
        SwingUtilities.invokeLater(() -> playerListArea.append(text));
    }
}
