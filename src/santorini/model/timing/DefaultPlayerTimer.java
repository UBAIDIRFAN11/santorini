package santorini.model.timing;

import javax.swing.*;

/**
 * This class implements GameTimer interface to provide a default countdown timer with start/pause/resume functionality.
 * It tracks the remaining time in seconds and allows setting a timeout action to be executed when the timer runs out.
 *
 * Author: Ubaid Irfan (Sprint 3 Implementation)
 */
public class DefaultPlayerTimer implements GameTimer {
    private final int initialTimeSeconds;
    private int remainingSeconds;
    private boolean isRunning = false;
    private long lastStartTime;
    private Runnable onTimeout;

    /**
     * Constructs a DefaultPlayerTimer with a specified initial time in seconds.
     * @param minutes
     */
    public DefaultPlayerTimer(int minutes) {
        this.initialTimeSeconds = minutes * 60;
        this.remainingSeconds = initialTimeSeconds;
    }

    /**
     * Starts the timer if not already running.
     * <p>A background thread periodically checks if the timer has expired and invokes the timeout action if set.</p>
     */
    @Override
    public void start() {
        if (!isRunning) {
            lastStartTime = System.currentTimeMillis();
            isRunning = true;

            new Thread(() -> {
                while (isRunning && getRemainingTimeSeconds() > 0) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ignored) {}
                }
                if (isRunning && getRemainingTimeSeconds() <= 0) {
                    isRunning = false;
                    if (onTimeout != null) {
                        SwingUtilities.invokeLater(onTimeout);
                    }

                }
            }).start();
        }
    }

    /**
     * Pauses the timer and updates remaining time.
     */
    @Override
    public void pause() {
        if (isRunning) {
            long now = System.currentTimeMillis();
            remainingSeconds = getRemainingTimeSeconds();
            isRunning = false;
        }
    }

    /**
     * Resumes the timer from the last paused state.
     */
    @Override
    public void resume() {
        start();
    }

    /**
     * Returns the remaining time in seconds.
     * If the timer is running, it calculates the elapsed time since the last start.
     *
     * @return remaining time in seconds
     */
    @Override
    public int getRemainingTimeSeconds() {
        if (isRunning) {
            long now = System.currentTimeMillis();
            int elapsed = (int) ((now - lastStartTime) / 1000);
            return Math.max(remainingSeconds - elapsed, 0);
        }
        return Math.max(remainingSeconds, 0);
    }

    /**
     * Sets the action to be executed when the timer runs out.
     *
     * @param action the action to execute on timeout
     */
    @Override
    public void setOnTimeout(Runnable action) {
        this.onTimeout = action;
    }

}

