import javax.swing.*;

public class GameTimer {
    private JLabel timerLabel;
    private long startTime;
    private Thread timerThread;
    private boolean running = false;

    public GameTimer(JLabel timerLabel) {
        this.timerLabel = timerLabel;
    }

    public void start() {
        startTime = System.nanoTime();
        running = true;
        
        timerThread = new Thread(() -> {
            while (running) {
                long elapsed = System.nanoTime() - startTime;
                double seconds = elapsed / 1_000_000_000.0;
                SwingUtilities.invokeLater(() -> 
                    timerLabel.setText(String.format("Time: %.1f s", seconds)));
                try {
                    Thread.sleep(16);
                } catch (InterruptedException ignored) {}
            }
        });
        timerThread.start();
    }

    public void stop() {
        running = false;
    }

    public double getElapsedSeconds() {
        long elapsed = System.nanoTime() - startTime;
        return elapsed / 1_000_000_000.0;
    }
}