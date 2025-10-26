import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class Multiplayer extends JFrame {
    Image bg;
    ImageIcon[] btn_Mode = new ImageIcon[Config.btn_Mode.length]; // ใช้ ImageIcon ตรง ๆ จะง่ายกว่า
    selectMode frame_selectMode;

    public Multiplayer(selectMode frame) {
        this.frame_selectMode = frame;
        setFrame();
    }

    private void setFrame() {
        setTitle(Config.NAME_GAME + " - Select Mode");
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
                    "assets" + File.separator + "obj" + File.separator + Config.btn_Multiplay[i];
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

        // ใช้ GridBagLayout ให้อยู่กลางทั้งแนวตั้งและแนวนอน
        panel.setLayout(new GridBagLayout());
        getContentPane().add(panel);

        // สร้างกล่องเรียงปุ่มในแนวตั้ง
        Box box = Box.createVerticalBox();

        int width = 350;
        int height = 100;

        JLabel btn_Host  = new JLabel(scale(btn_Mode[0], width, height));
        JLabel btn_Join   = new JLabel(scale(btn_Mode[1], width, height));
        JLabel btn_Back     = new JLabel(scale(btn_Mode[2], width, height));

        btn_Host.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn_Join.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn_Back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn_Host.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_Join.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_Back.setAlignmentX(Component.CENTER_ALIGNMENT);

        box.add(btn_Host);
        box.add(Box.createRigidArea(new Dimension(0, 30))); // เว้นระยะ
        box.add(btn_Join);
        box.add(Box.createRigidArea(new Dimension(0, 30)));
        box.add(btn_Back);

        panel.add(box);

        btn_Host.addMouseListener(new MouseAdapter() {
            int w = width;
            int h = height;

            @Override
            public void mouseEntered(MouseEvent e) {
                double scale = 1.1;  // ขยาย 10%
                int newW = (int) (w * scale);
                int newH = (int) (h * scale);

                Image bigger = btn_Mode[0].getImage().getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
                btn_Host.setIcon(new ImageIcon(bigger));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // กลับสู่ขนาดเดิม
                btn_Host.setIcon(scale(btn_Mode[0], w, h));
            }
        });

        btn_Join.addMouseListener(new MouseAdapter() {
            int w = width;
            int h = height;

            @Override
            public void mouseEntered(MouseEvent e) {
                double scale = 1.1;  // ขยาย 10%
                int newW = (int) (w * scale);
                int newH = (int) (h * scale);

                Image bigger = btn_Mode[1].getImage().getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
                btn_Join.setIcon(new ImageIcon(bigger));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // กลับสู่ขนาดเดิม
                btn_Join.setIcon(scale(btn_Mode[1], w, h));
            }
        });

        btn_Back.addMouseListener(new MouseAdapter() {
            int w = width;
            int h = height;

            @Override
            public void mouseEntered(MouseEvent e) {
                double scale = 1.1;  // ขยาย 10%
                int newW = (int) (w * scale);
                int newH = (int) (h * scale);

                Image bigger = btn_Mode[2].getImage().getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
                btn_Back.setIcon(new ImageIcon(bigger));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // กลับสู่ขนาดเดิม
                btn_Back.setIcon(scale(btn_Mode[2], w, h));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setVisible(false);
                frame_selectMode.setVisible(true);
            }
        });
    }



    // ตัวช่วยสเกลภาพ (ถ้าต้องการ)
    private static ImageIcon scale(ImageIcon icon, int w, int h) {
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
