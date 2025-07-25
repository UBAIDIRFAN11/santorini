package santorini.model;


/**
 * Represents a single level of a building in the Santorini game.
 * <p>
 * Levels are stacked to increase the height of a building, and their order
 * is determined by the {@code levelNumber} (0 to 3).
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 */
public class Level {
    private final int levelNumber;

    /**
     * Constructs a level with the specified level number.
     *
     * @param levelNumber the numeric height of the level (e.g., 0 for ground level, 1 for first floor, etc.)
     */
    public Level(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    /**
     * Returns the numeric level of this building segment.
     *
     * @return the level number (0 to 3)
     */
    public int getLevelNumber() {
        return levelNumber;
    }
}