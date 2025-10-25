import javax.swing.*;
import java.awt.*;

public class ChooseSkin extends JFrame{

    public ChooseSkin() {
        setTitle("Choose Skin");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null); // ใช้ absolute layout เพื่อวางตำแหน่งง่าย

        // ชื่อหัวข้อ
        JLabel lblTitle = new JLabel("Choose Skin", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblTitle.setBounds(0, 50, 1200, 50);
        add(lblTitle);

        // กล่องแสดงสกิน
        JPanel skinBox = new JPanel();
        skinBox.setBackground(Color.WHITE);
        skinBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        skinBox.setBounds(450, 200, 300, 250);
        add(skinBox);

        JLabel skinLabel = new JLabel("Skin Preview", SwingConstants.CENTER);
        skinLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        skinBox.setLayout(new BorderLayout());
        skinBox.add(skinLabel, BorderLayout.CENTER);

        // ปุ่มลูกศรซ้าย
        JButton btnLeft = new JButton("◀");
        btnLeft.setFont(new Font("SansSerif", Font.BOLD, 30));
        btnLeft.setBounds(350, 290, 80, 80);
        add(btnLeft);

        // ปุ่มลูกศรขวา
        JButton btnRight = new JButton("▶");
        btnRight.setFont(new Font("SansSerif", Font.BOLD, 30));
        btnRight.setBounds(770, 290, 80, 80);
        add(btnRight);

        // ปุ่ม Continue
        JButton btnContinue = new JButton("Continue");
        btnContinue.setFont(new Font("SansSerif", Font.BOLD, 20));
        btnContinue.setBounds(500, 520, 200, 60);
        add(btnContinue);

        // สีพื้นหลังอ่อน ๆ
        getContentPane().setBackground(new Color(20, 24, 37)); // พื้นหลังเข้มดูเท่

        btnContinue.addActionListener(e -> {
            Frame_Menu frameMenu = new Frame_Menu(this);
            setVisible(false);
            frameMenu.setVisible(true);
        });
    }

    public static void main(String[] args) {
        ChooseSkin frame = new ChooseSkin();
        frame.setVisible(true);
    }
}
