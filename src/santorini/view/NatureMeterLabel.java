package santorini.view;

import javax.swing.*;
import java.awt.*;

/**
 * A custom JPanel that displays a nature meter label indicating the eco level of a player.
 *
 * Author: Ubaid Irfan (Sprint 3 implementation)
 */
public class NatureMeterLabel extends JPanel {
    private int ecoLevel; // 0 to 100

    /**
     * Constructs a NatureMeterLabel for a player with the specified name.
     * Initializes the eco level to 100 and sets the panel to be transparent.
     *
     * @param playerName the name of the player
     */
    public NatureMeterLabel(String playerName) {
        this.ecoLevel = 100;
        setOpaque(false); // Transparent background
    }

    /**
     * Updates the eco level of the nature meter.
     * The eco level is clamped between 0 and 100.
     *
     * @param newLevel the new eco level to set
     */
    public void updateEcoLevel(int newLevel) {
        ecoLevel = Math.max(0, Math.min(newLevel, 100));
        repaint();
    }

    /**
     * Gets the current eco level of the nature meter.
     *
     * @return the current eco level
     */
    public int getEcoLevel() {
        return ecoLevel;
    }

    /**
     * Paints the nature meter label with a background bar and a filled portion based on the eco level.
     * The color of the filled portion changes based on the eco level:
     * - Green for high levels (> 66)
     * - Yellow for medium levels (33 to 66)
     * - Red for low levels (<= 33)
     *
     * @param g the {@link Graphics} context to use for rendering
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        int panelWidth = getWidth();
        int barHeight = 10;
        int yOffset = (getHeight() - barHeight) / 2;

        // Draw background bar
        g2.setColor(Color.DARK_GRAY);
        g2.fillRoundRect(0, yOffset, panelWidth, barHeight, 8, 8);

        // Determine eco bar fill color
        Color fillColor;
        if (ecoLevel > 66) {
            fillColor = new Color(76, 175, 80); // Green
        } else if (ecoLevel > 33) {
            fillColor = new Color(255, 193, 7); // Yellow
        } else {
            fillColor = new Color(244, 67, 54); // Red
        }

        // Draw filled portion of the bar
        int filledWidth = (int) (panelWidth * ecoLevel / 100.0);
        g2.setColor(fillColor);
        g2.fillRoundRect(0, yOffset, filledWidth, barHeight, 8, 8);

        // Draw percentage text
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
        String text = String.format("%d%%", ecoLevel);
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        g2.drawString(text, (panelWidth - textWidth) / 2, yOffset + barHeight - 2);

        g2.dispose();
    }
}