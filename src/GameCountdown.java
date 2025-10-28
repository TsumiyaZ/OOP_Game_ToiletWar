import javax.swing.*;

public class GameCountdown {
    private JLabel countdownLabel;
    private Thread countdownThread;
    private Runnable onComplete;

    public GameCountdown(JLabel countdownLabel, Runnable onComplete) {
        this.countdownLabel = countdownLabel;
        this.onComplete = onComplete;
    }

    public void start() {
        countdownThread = new Thread(() -> {
            try {
                for (int i = 5; i > 0; i--) {
                    final int count = i;
                    SwingUtilities.invokeLater(() -> countdownLabel.setText(String.valueOf(count)));
                    Thread.sleep(1000);
                }

                SwingUtilities.invokeLater(() -> countdownLabel.setText("GO!"));
                Thread.sleep(800);
                SwingUtilities.invokeLater(() -> countdownLabel.setVisible(false));

                if (onComplete != null) {
                    onComplete.run();
                }
            } catch (InterruptedException ignored) {}
        });
        countdownThread.start();
    }
}