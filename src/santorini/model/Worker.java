package santorini.model;


/**
 * Represents a worker in the Santorini game.
 * <p>
 * A worker is controlled by a player and occupies a cell on the game board.
 * It is the main agent used for moving and building.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 */
public class Worker {
    private Player owner;
    private Cell currentCell;
    private Cell previousCell;
    private Boolean canMove;

    /**
     * Constructs a worker belonging to the specified player.
     *
     * @param owner the {@link Player} who owns this worker
     */
    public Worker(Player owner) {
        this.owner = owner;
    }

    /**
     * Returns the cell this worker is currently occupying.
     *
     * @return the current {@link Cell}
     */
    public Cell getCurrentCell() {
        return currentCell;
    }

    /**
     * Sets the current cell for this worker.
     *
     * @param cell the {@link Cell} to assign as current
     */
    public void setCurrentCell(Cell cell) {
        this.previousCell = this.currentCell;

        this.currentCell = cell;
    }

    /**
     * Returns whether this worker can move.
     *
     * @return true if the worker can move, false otherwise
     */
    public Cell getPreviousCell() {
        return previousCell;
    }

    /**
     * Returns the player who owns this worker.
     *
     * @return the owning {@link Player}
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Sets the player who owns this worker.
     *
     * @param owner the owning {@link Player}
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
