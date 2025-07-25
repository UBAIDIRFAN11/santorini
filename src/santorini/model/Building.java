package santorini.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a building structure in the Santorini game.
 * <p>
 * A building can have up to four levels and may optionally be topped with a dome.
 * Levels are added incrementally, and the dome is placed once the building is complete or based on god powers.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 * Modified by: Ubaid Irfan (Sprint 3 implementation)
 */
public class Building {
    private final List<Level> levels;
    private Dome dome;

    /**
     * Constructs a new empty building with no levels or dome.
     */
    public Building() {
        this.levels = new ArrayList<>();
        this.dome = null;
    }

    /**
     * Returns the current height of the building.
     * Each level counts as one unit of height.
     *
     * @return the number of levels in the building
     */
    public int getHeight() {
        return levels.size();
    }

    /**
     * Returns whether the building has a dome on top.
     *
     * @return {@code true} if the building has a dome, otherwise {@code false}
     */
    public boolean hasDome() {
        return dome != null;
    }

    /**
     * Adds a new level to the building if it has less than 4 levels and no dome.
     * <p>
     * Each level is assigned an increasing height index.
     */
    public void addLevel() {
        if (getHeight() < 4 && dome == null) {
            levels.add(new Level(getHeight()));
        }
    }

    /**
     * Returns the dome of the building, if present.
     *
     * @return the dome of the building, or {@code null} if no dome exists
     */
    public boolean isEmpty() {
        return levels.isEmpty() && dome == null;
    }

    /**
     * Adds a dome to the building at the specified cell.
     * Only allowed if the building does not already have a dome.
     *
     * @param cell  the cell on which the dome is placed
     * @param board the board to which the cell belongs
     */
    public void addDome(Cell cell, Board board) {
        if (!hasDome()) {
            this.dome = new Dome(cell, board);
        }
    }

    /**
     * Removes the dome from the building, if present.
     */
    public void removeDome() {
        this.dome = null;
    }

    /**
     * Removes the topmost level from the building, if any exists.
     */
    public void removeLevel() {
        if (!levels.isEmpty()) {
            levels.remove(levels.size() - 1);
        }
    }
}
