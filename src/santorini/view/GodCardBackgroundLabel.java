package santorini.view;

import javax.swing.*;
import java.awt.*;


/**
 * A custom {@link JLabel} that supports displaying a scaled background image behind its text.
 * <p>
 * Used in the UI to show player god card panels with thematic images.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 */
public class GodCardBackgroundLabel extends JLabel {
    private Image backgroundImage;

    /**
     * Constructs a label with a default background image.
     */
    public GodCardBackgroundLabel() {
        setBackgroundImage("screens/default_bg.jpg");
    }

    /**
     * Sets the background image using a resource path.
     * <p>
     * If the image fails to load, logs an error message to the console.
     *
     * @param path the resource path to the image file
     */
    public void setBackgroundImage(String path) {
        java.net.URL imgUrl = getClass().getClassLoader().getResource(path);
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            backgroundImage = icon.getImage();
        } else {
            System.err.println("Failed to load image: " + path);
        }
        repaint();
    }

    /**
     * Paints the background image and label content.
     *
     * @param g the {@link Graphics} context to use for rendering
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(g);
    }
}