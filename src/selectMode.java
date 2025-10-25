import javax.swing.*;
import java.awt.*;
import java.io.File;

public class selectMode extends JFrame {
    Image bg;
    ImageIcon[] btn_Mode = new ImageIcon[Config.btn_Mode.length]; // ใช้ ImageIcon ตรง ๆ จะง่ายกว่า

    public selectMode() {
        setFrame();
        setVisible(true); // ให้กรอบแสดงผล
    }

    private void setFrame() {
        setSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        loadImages();
        setPanel();
    }

    private void loadImages() {
        // พื้นหลัง
        String bgPath = System.getProperty("user.dir") + File.separator +
                "assets" + File.separator + "bg" + File.separator + Config.bg_Mode;
        bg = new ImageIcon(bgPath).getImage();

        // ปุ่ม (ต้องใช้ชื่อไฟล์จาก Config.btn_Mode[i] และโฟลเดอร์ให้ถูก)
        for (int i = 0; i < Config.btn_Mode.length; i++) {
            String path_mode = System.getProperty("user.dir") + File.separator +
                    "assets" + File.separator + "obj" + File.separator + Config.btn_Mode[i];
            btn_Mode[i] = new ImageIcon(path_mode);
        }
    }

    private void setPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(new GridBagLayout());

        Box box = Box.createVerticalBox();

        // 🔹 ลดขนาดปุ่มลง (เช่น 200x70)
        JLabel btn_single  = new JLabel(scale(btn_Mode[0], 300, 70));
        JLabel btn_Multi   = new JLabel(scale(btn_Mode[1], 300, 70));
        JLabel btn_HowPlay = new JLabel(scale(btn_Mode[2], 300, 70));

        btn_single.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn_Multi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn_HowPlay.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn_single.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_Multi.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_HowPlay.setAlignmentX(Component.CENTER_ALIGNMENT);

        box.add(btn_single);
        box.add(Box.createRigidArea(new Dimension(0, 10)));
        box.add(btn_Multi);
        box.add(Box.createRigidArea(new Dimension(0, 10)));
        box.add(btn_HowPlay);

        panel.add(box);
        getContentPane().add(panel, BorderLayout.CENTER);
    }

    // ตัวช่วยสเกลภาพ (ถ้าต้องการ)
    private static ImageIcon scale(ImageIcon icon, int w, int h) {
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
