import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class Multiplayer extends JFrame {
    Image bg;
    ImageIcon[] btn_Mode = new ImageIcon[Config.btn_Mode.length]; // ใช้ ImageIcon ตรง ๆ จะง่ายกว่า

    public Multiplayer() {
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

        // ใช้ GridBagLayout ให้อยู่กลางทั้งแนวตั้งและแนวนอน
        panel.setLayout(new GridBagLayout());
        getContentPane().add(panel);

        // สร้างกล่องเรียงปุ่มในแนวตั้ง
        Box box = Box.createVerticalBox();

        int width = 350;
        int height = 100;

        JLabel btn_single  = new JLabel(scale(btn_Mode[0], width, height));
        JLabel btn_multi   = new JLabel(scale(btn_Mode[1], width, height));
        JLabel btn_how     = new JLabel(scale(btn_Mode[2], width, height));

        btn_single.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn_multi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn_how.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn_single.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_multi.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_how.setAlignmentX(Component.CENTER_ALIGNMENT);

        box.add(btn_single);
        box.add(Box.createRigidArea(new Dimension(0, 30))); // เว้นระยะ
        box.add(btn_multi);
        box.add(Box.createRigidArea(new Dimension(0, 30)));
        box.add(btn_how);

        panel.add(box);

        btn_single.addMouseListener(new MouseAdapter() {
            int w = width;
            int h = height;

            @Override
            public void mouseEntered(MouseEvent e) {
                double scale = 1.1;  // ขยาย 10%
                int newW = (int) (w * scale);
                int newH = (int) (h * scale);

                Image bigger = btn_Mode[0].getImage().getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
                btn_single.setIcon(new ImageIcon(bigger));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // กลับสู่ขนาดเดิม
                btn_single.setIcon(scale(btn_Mode[0], w, h));
            }
        });

        btn_multi.addMouseListener(new MouseAdapter() {
            int w = width;
            int h = height;

            @Override
            public void mouseEntered(MouseEvent e) {
                double scale = 1.1;  // ขยาย 10%
                int newW = (int) (w * scale);
                int newH = (int) (h * scale);

                Image bigger = btn_Mode[0].getImage().getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
                btn_multi.setIcon(new ImageIcon(bigger));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // กลับสู่ขนาดเดิม
                btn_multi.setIcon(scale(btn_Mode[0], w, h));
            }
        });

        btn_how.addMouseListener(new MouseAdapter() {
            int w = width;
            int h = height;

            @Override
            public void mouseEntered(MouseEvent e) {
                double scale = 1.1;  // ขยาย 10%
                int newW = (int) (w * scale);
                int newH = (int) (h * scale);

                Image bigger = btn_Mode[0].getImage().getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
                btn_how.setIcon(new ImageIcon(bigger));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // กลับสู่ขนาดเดิม
                btn_how.setIcon(scale(btn_Mode[0], w, h));
            }
        });
    }



    // ตัวช่วยสเกลภาพ (ถ้าต้องการ)
    private static ImageIcon scale(ImageIcon icon, int w, int h) {
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
