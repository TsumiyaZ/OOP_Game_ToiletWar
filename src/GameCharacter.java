import javax.swing.*;

public class GameCharacter {
    private JLabel characterLabel;
    private DinoAnimator animator;
    private int x, y;
    private int width, height;
    private int step;
    private boolean isMoving = false;

    public GameCharacter(int skinIndex, int startX, int startY, int width, int height, int step) {
        this.animator = new DinoAnimator(skinIndex);
        this.x = startX;
        this.y = startY;
        this.width = width;
        this.height = height;
        this.step = step;
        this.characterLabel = new JLabel();
        this.characterLabel.setBounds(x, y, width, height);
    }

    public void setMoving(boolean moving) {
        this.isMoving = moving;
        animator.setWalking(moving);
    }

    public void move() {
        if (isMoving) {
            x += step;
            characterLabel.setLocation(x, y);
        }
    }

    public void updateAnimation() {
        try {
            ImageIcon frame = animator.getCurrentFrameScaled(width, height);
            if (frame != null) {
                characterLabel.setIcon(frame);
            }
        } catch (Exception e) {
            System.err.println("Error updating animation: " + e.getMessage());
        }
    }

    public boolean hasReachedFinish(int finishLine) {
        return x >= finishLine;
    }

    public void setPosition(int finishX) {
        this.x = finishX;
        characterLabel.setLocation(x, y);
    }

    public JLabel getLabel() {
        return characterLabel;
    }

    public int getX() {
        return x;
    }
}