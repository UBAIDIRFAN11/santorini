package santorini.model;

import javax.swing.*;
import java.awt.*;


/**
 * Represents a single cell on the Santorini game board.
 * <p>
 * A cell may contain a building, a worker, a dome, and UI-related highlights.
 * It extends {@link JPanel} for visual representation in the game UI.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 * Modified by: Ubaid Irfan (Sprint 3 implementation)
 */
public class Cell extends JPanel {
    private int x;
    private int y;
    private Worker worker;
    private Building building;
    private boolean isOccupied;
    private boolean isHighlighted;

    /**
     * Constructs a new Cell at the specified board coordinates.
     *
     * @param x the row index
     * @param y the column index
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.building = new Building();
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    /**
     * Returns the row index of this cell.
     *
     * @return the x-coordinate (row)
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the column index of this cell.
     *
     * @return the y-coordinate (column)
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the building associated with this cell.
     *
     * @return the {@link Building} object
     */
    public Building getBuilding() {
        return building;
    }

    /**
     * Assigns a worker to this cell and updates the occupied flag.
     *
     * @param worker the {@link Worker} to assign
     */
    public void setWorker(Worker worker) {
        this.worker = worker;
        this.isOccupied = (worker != null);
        repaint();
    }

    /**
     * Returns the worker currently occupying the cell.
     *
     * @return the {@link Worker}, or {@code null} if none
     */
    public Worker getWorker() {
        return worker;
    }

    /**
     * Sets whether the cell is occupied (by a worker).
     *
     * @param occupied {@code true} if occupied, otherwise {@code false}
     */
    public void setIsOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    /**
     * Returns whether the cell is currently occupied by a worker.
     *
     * @return {@code true} if occupied, otherwise {@code false}
     */
    public boolean getIsOccupied() {
        return isOccupied;
    }

    /**
     * Highlights or un-highlights the cell in the UI.
     *
     * @param highlight {@code true} to highlight, {@code false} to remove highlight
     */
    public void setIsHighlighted(boolean highlight) {
        isHighlighted = highlight;
    }

    /**
     * Returns whether the cell is currently highlighted.
     *
     * @return {@code true} if highlighted, otherwise {@code false}
     */
    public boolean getIsHighlighted() {
        return isHighlighted;
    }

    /**
     * Assigns a new building object to the cell.
     *
     * @param building the {@link Building} to assign
     */
    public void setBuilding(Building building) {
        this.building = building;
    }

    /**
     * Returns the row index (same as getX).
     *
     * @return the row index
     */
    public int getRow() {
        return this.x;
    }

    /**
     * Returns the column index (same as getY).
     *
     * @return the column index
     */
    public int getCol() {
        return this.y;
    }

    /**
     * Checks if the building on this cell has a dome.
     *
     * @return {@code true} if the building has a dome, otherwise {@code false}
     */
    public boolean hasDome() {
        return building.hasDome();
    }

    /**
     * Custom paint logic for displaying a worker in the UI.
     *
     * @param g the {@link Graphics} context used to draw the component
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (worker != null) {
            g.setColor(Color.DARK_GRAY);
            g.fillOval(20, 20, getWidth() - 40, getHeight() - 40);
        }
    }
}