package santorini.model;

/**
 * Represents a dome in the Santorini game.
 * <p>
 * A dome can be placed on a cell to indicate that no further building or movement
 * can occur there. Domes are typically placed on buildings of height 3, but may
 * also be used according to specific god powers.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 */
public class Dome {

    private boolean isPlaced;
    private Cell locatedCell;
    private int locatedX;
    private int locatedY;
    private Board board;

    /**
     * Constructs a dome at the specified cell on the board.
     * The dome is considered placed upon creation.
     *
     * @param cell  the {@link Cell} where the dome is located
     * @param board the game {@link Board} the dome belongs to
     */
    public Dome(Cell cell, Board board) {
        this.isPlaced = true;
        this.locatedCell = cell;
        this.locatedX = cell.getX();
        this.locatedY = cell.getY();
        this.board = board;
    }
}
