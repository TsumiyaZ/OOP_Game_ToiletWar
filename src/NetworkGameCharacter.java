import javax.swing.*;

public class NetworkGameCharacter {
    private JLabel characterLabel;
    private DinoAnimator animator;
    private int x, y;
    private int width, height;
    private int step;
    private String playerName;
    private boolean isMoving = false;
    private int playerId;

    public NetworkGameCharacter(int skinIndex, String playerName, int playerId, int startX, int startY, int width, int height, int step) {
        this.animator = new DinoAnimator(skinIndex);
        this.playerName = playerName;
        this.playerId = playerId;
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
        x += step;
        characterLabel.setLocation(x, y);
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

    public void setPosition(int newX) {
        this.x = newX;
        characterLabel.setLocation(x, y);
    }

    public JLabel getLabel() {
        return characterLabel;
    }

    public int getX() {
        return x;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerId() {
        return playerId;
    }
}