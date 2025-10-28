import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DinoAnimator {
    private ImageIcon[] stopFrames;
    private ImageIcon[] walkFrames;
    private int currentFrame = 0;
    private int frameDelay = 0;
    private final int FRAME_SPEED = 3;
    private boolean isWalking = false;
    
    public DinoAnimator(int dinoIndex) {
        loadFrames(dinoIndex);
    }
    
    private void loadFrames(int dinoIndex) {
        String basePath = System.getProperty("user.dir") + File.separator +
                "assets" + File.separator + "DinoSprites" + File.separator + "d" + (dinoIndex + 1);
        
        System.out.println("Loading frames for dino index: " + dinoIndex);
        System.out.println("Base path: " + basePath);
        
        stopFrames = loadFramesFromDirectory(basePath + File.separator + "stop", "stop");
        walkFrames = loadFramesFromDirectory(basePath + File.separator + "walk", "walk");
    }
    
    private ImageIcon[] loadFramesFromDirectory(String dirPath, String type) {
        List<ImageIcon> frames = new ArrayList<>();
        File dir = new File(dirPath);
        
        if (!dir.exists() || !dir.isDirectory()) {
            System.err.println("Directory not found: " + dirPath);
            return new ImageIcon[]{createDefaultIcon()};
        }
        
        int frameIndex = 1;
        while (true) {
            String framePath = dirPath + File.separator + frameIndex + ".png";
            File file = new File(framePath);
            
            if (file.exists()) {
                frames.add(new ImageIcon(framePath));
                System.out.println("Loaded " + type + " frame: " + framePath);
                frameIndex++;
            } else {
                break;
            }
        }
        
        if (frames.isEmpty()) {
            System.err.println("No frames found in: " + dirPath);
            frames.add(createDefaultIcon());
        }
        
        return frames.toArray(new ImageIcon[0]);
    }
    
    private ImageIcon createDefaultIcon() {
        Image defaultImage = new java.awt.image.BufferedImage(100, 100, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) defaultImage.getGraphics();
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, 100, 100);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Missing", 20, 50);
        g2d.dispose();
        return new ImageIcon(defaultImage);
    }
    
    public void setWalking(boolean walking) {
        if (this.isWalking != walking) {
            this.isWalking = walking;
            currentFrame = 0;
        }
    }
    
    public ImageIcon getCurrentFrame() {
        frameDelay++;
        
        if (frameDelay >= FRAME_SPEED) {
            frameDelay = 0;
            currentFrame++;
            
            if (isWalking) {
                if (currentFrame >= walkFrames.length) {
                    currentFrame = 0;
                }
            } else {
                if (currentFrame >= stopFrames.length) {
                    currentFrame = 0;
                }
            }
        }
        
        ImageIcon frame = isWalking ? walkFrames[currentFrame] : stopFrames[currentFrame];
        return frame != null ? frame : createDefaultIcon();
    }
    
    public ImageIcon getCurrentFrameScaled(int width, int height) {
        try {
            ImageIcon frame = getCurrentFrame();
            if (frame != null && frame.getImage() != null) {
                Image scaledImage = frame.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            } else {
                System.err.println("Frame is null, using default icon");
                return createDefaultIcon();
            }
        } catch (Exception e) {
            System.err.println("Error in getCurrentFrameScaled: " + e.getMessage());
            return createDefaultIcon();
        }
    }
}