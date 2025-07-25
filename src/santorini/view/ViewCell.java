package santorini.view;

import santorini.model.*;
import santorini.model.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * A UI component that visually represents a single cell on the game board.
 * <p>
 * This class handles drawing buildings, domes, workers, hover effects,
 * and highlighting for selectable/interactable states.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 */
public class ViewCell extends JPanel {

    public static final BufferedImage ground, grassFlower,  b1, b2, b3, dome;
    private final BufferedImage groundTile;

    //animated workers
    private static final BufferedImage[] wBlueAnim = new BufferedImage[2];
    private static final BufferedImage[] wRedAnim = new BufferedImage[2];

    private int frameIndex = 0;
    private Timer animationTimer;

    static {
        try {
            ground = img("blocks/grass1.png");
            grassFlower = img("blocks/grass-with-flower.png");

            b1     = img("blocks/block1.png");
            b2     = img("blocks/block2.png");
            b3     = img("blocks/block3.png");
            dome   = img("blocks/dome.png");




            wBlueAnim[0] = img("workers/worker_girl_blue2.png");
            wBlueAnim[1] = img("workers/worker_girl_blue1.png");

            wRedAnim[0] = img("workers/worker_girl_orange1.png");
            wRedAnim[1] = img("workers/worker_girl_orange2.png");



        } catch (IOException e) {
            throw new RuntimeException("Missing sprite files", e);
        }
    }

    private static BufferedImage img(String path) throws IOException {
        return ImageIO.read(ViewCell.class.getClassLoader().getResourceAsStream(path));
    }

    private static final Map<Player, BufferedImage> colourMap = new HashMap<>();
    private final Cell model;
    private boolean hovered = false;

    private static final Color HIGHLIGHT_OVERLAY = new Color(39,39,39, 80);
    private static final Color HOVER_OVERLAY     = new Color(255, 255, 255, 40);
    private static final Color ACTIVE_OVERLAY    = new Color(39,39,39, 80);

    /**
     * Constructs a ViewCell tied to a specific {@link Cell} model.
     * Adds mouse listeners to track hover interactions.
     *
     * @param model the Cell model this view will represent
     */
    public ViewCell(Cell model, BufferedImage groundTile) {
        this.model = model;
        this.groundTile = groundTile;
        setOpaque(false);

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                hovered = true;
                repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                hovered = false;
                repaint();
            }
        });

        animationTimer = new Timer(500, e -> {
            frameIndex = (frameIndex + 1) % 2; // Only 2 frames
            repaint(); // triggers paintComponent again
        });

        //animationTimer.start();
    }

    public void setAnimationActive(boolean active) {
        if (active && !animationTimer.isRunning()) {
            animationTimer.start();
        } else if (!active && animationTimer.isRunning()) {
            animationTimer.stop();
        }
    }

    /**
     * Sets the highlight state of this cell.
     *
     * @param f {@code true} to highlight, {@code false} to remove highlight
     */
    public void setHighlighted(boolean f) {
        model.setIsHighlighted(f);
        repaint();
    }

    /**
     * Paints the cell, including ground, building height, dome, worker, and overlays.
     *
     * @param g the {@link Graphics} context for rendering
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int w = getWidth(), h = getHeight();

        // Draw ground layer
        g2.drawImage(groundTile, 0, 0, w, h, null);

        // Draw tower or dome
        if (model.hasDome()) {
            g2.drawImage(dome, 0, 0, w, h, null);
        } else {
            int height = model.getBuilding().getHeight();
            switch (height) {
                case 4 -> g2.drawImage(dome, 0, 0, w, h, null);
                case 3 -> g2.drawImage(b3, 0, 0, w, h, null);
                case 2 -> g2.drawImage(b2, 0, 0, w, h, null);
                case 1 -> g2.drawImage(b1, 0, 0, w, h, null);
            }
        }

        // Handle dome drawn from either Cell or Building
        if (model.getBuilding().hasDome() || model.hasDome()) {
            g2.drawImage(dome, 0, 0, w, h, null);
        }

        // Draw worker if present
        if (model.getWorker() != null) {
            Player owner = model.getWorker().getOwner();
            String color = owner.getWorkerColor();

            BufferedImage sprite = switch (color.toLowerCase()) {
                case "blue" -> wBlueAnim[frameIndex];
                case "red" -> wRedAnim[frameIndex];
                default -> throw new IllegalStateException("Unknown color: " + color);
            };
            g2.drawImage(sprite, 0, 0, w, h, null);
        }

        int circleSize = Math.min(w, h) / 3;  // size of circular overlay
        int circleX = (w - circleSize) / 2;
        int circleY = (h - circleSize) / 2;

        // Highlight overlay (valid move/build/etc targets)
        if (model.getIsHighlighted() && model.getWorker() == null) {
            g2.setColor(HIGHLIGHT_OVERLAY);
            g2.fillOval(circleX, circleY, circleSize, circleSize);
        }

        // Hover overlay — keep as rounded square
        if (hovered) {
            int arcSize = 20, margin = 4;
            g2.setColor(HOVER_OVERLAY);
            g2.fillRoundRect(margin, margin, w - 2 * margin, h - 2 * margin, arcSize, arcSize);
        }

        // Active overlay (selected worker or target cell with worker)
        if (model.getWorker() != null && model.getIsHighlighted()) {
            g2.setColor(ACTIVE_OVERLAY);
            g2.fillOval(circleX, circleY, circleSize, circleSize);
        }
    }

    public Cell getModel() {
        return model;
    }
}