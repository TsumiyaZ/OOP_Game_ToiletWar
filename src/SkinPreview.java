import javax.swing.*;

public class SkinPreview {
    private DinoAnimator animator;
    private JLabel previewLabel;
    private Thread animationThread;
    private boolean running = false;
    private int width, height;

    public SkinPreview(int skinIndex, int width, int height) {
        this.animator = new DinoAnimator(skinIndex);
        this.width = width;
        this.height = height;
        this.previewLabel = new JLabel();
        this.previewLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void startAnimation() {
        if (running) return;
        
        running = true;
        animationThread = new Thread(() -> {
            while (running) {
                SwingUtilities.invokeLater(() -> {
                    ImageIcon frame = animator.getCurrentFrameScaled(width, height);
                    previewLabel.setIcon(frame);
                });
                
                try {
                    Thread.sleep(30);
                } catch (InterruptedException ignored) {
                    break;
                }
            }
        });
        animationThread.start();
    }

    public void stopAnimation() {
        running = false;
        if (animationThread != null) {
            animationThread.interrupt();
        }
    }

    public JLabel getLabel() {
        return previewLabel;
    }

    public void setBounds(int x, int y, int width, int height) {
        previewLabel.setBounds(x, y, width, height);
    }
}