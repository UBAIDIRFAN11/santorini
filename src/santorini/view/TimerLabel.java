package santorini.view;

import santorini.model.timing.GameTimer;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents a JLabel that displays the remaining time for a game.
 * It updates time left every second using a Swing Timer.
 *
 * Author: Ubaid Irfan (Sprint 3 implementation)
 */
public class TimerLabel extends JLabel {
    private final GameTimer timer;
    private final String playerName;
    private final javax.swing.Timer swingTimer;
    private boolean hasTimedOut = false;

    /**
     * Constructs a TimerLabel that displays the remaining time for the given GameTimer.
     * @param timer
     */
    public TimerLabel(GameTimer timer, String playerName) {
        this.timer = timer;
        this.playerName = playerName;
        this.swingTimer = new javax.swing.Timer(1000, e -> updateText());
        this.swingTimer.start();

        // === Pixel Font ===
        try {
            Font pixelFont = Font.createFont(Font.TRUETYPE_FONT,
                            getClass().getClassLoader().getResourceAsStream("fonts/gomarice_tall_block.ttf"))
                    .deriveFont(Font.PLAIN, 22f);
            setFont(pixelFont);
        } catch (Exception e) {
            setFont(new Font("Monospaced", Font.PLAIN, 22));
        }

        // === Styling ===
        setBackground(new java.awt.Color(39, 39, 39));   // match your UI theme
        setOpaque(true);
        setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8)); // spacing inside label
        setHorizontalAlignment(SwingConstants.CENTER);

        updateText();
    }


    private void updateText() {
        if (hasTimedOut) return;

        int seconds = timer.getRemainingTimeSeconds();
        int mins = seconds / 60;
        int secs = seconds % 60;
        setText(String.format("%2d:%02d", mins, secs));

        // === Color coding ===
        if (seconds <= 30) {
            setForeground(Color.RED); // danger
        } else if (seconds <= 60) {
            setForeground(new Color(255, 165, 0)); // orange
        } else {
            setForeground(new Color(216, 178, 82)); // default color
        }

        if (seconds <= 0) {
            hasTimedOut = true;
            swingTimer.stop(); // stop ui refreshing when time is up, so it doesn't show wrong time remaining
        }
    }
}