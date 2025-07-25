package santorini.model.actions;

import santorini.model.*;

import java.util.List;


/**
 * Represents a movement action in the Santorini game.
 * <p>
 * A {@code MoveAction} moves a worker from its current cell to an adjacent,
 * valid destination cell according to movement rules.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 */
public class MoveAction extends Action {

    private final Cell source;
    private final Cell dest;

    /**
     * Constructs a move action for the specified worker and destination cell.
     *
     * @param board   the game board
     * @param players the list of players in the game
     * @param worker  the worker that is being moved
     * @param dest    the destination {@link Cell}
     */
    public MoveAction(Board board,
                      List<Player> players,
                      Worker worker,
                      Cell dest) {
        super(ActionType.MOVE, board, players, worker);
        this.source = worker.getCurrentCell(); // capture source before the move
        this.dest = dest;
    }

    /**
     * Returns the source cell where the worker is currently located.
     *
     * @return the source {@link Cell}
     */
    public Cell getSourceCell() {
        return source;
    }

    /**
     * Returns the destination cell where the worker intends to move.
     *
     * @return the target {@link Cell}
     */
    @Override
    public Cell getTargetCell() {
        return dest;
    }

    /**
     * Executes the move action if it is legal.
     * <p>
     * Removes the worker from the source cell and places it on the destination cell.
     *
     * @return {@code true} if the move was successful; {@code false} otherwise
     */
    @Override
    public boolean execute() {
        if (!isLegal()) {
            fail("Illegal move");
            return false;
        }

        Cell from = source;
        if (from != null) from.setWorker(null);

        dest.setWorker(worker);
        worker.setCurrentCell(dest);
        return true;
    }

    /**
     * Checks if the move is legal according to game rules.
     * <p>
     * A legal move must be:
     * <ul>
     *   <li>To an adjacent cell</li>
     *   <li>To an unoccupied cell</li>
     *   <li>To a cell without a dome</li>
     *   <li>No more than one level higher than the source</li>
     * </ul>
     *
     * @return {@code true} if the move is valid; otherwise {@code false}
     */
    @Override
    public boolean isLegal() {
        Cell from = worker.getCurrentCell();
        if (from == null || dest == null) return false;

        if (!board.getNeighbourCells(from).contains(dest)) return false;
        if (dest.getIsOccupied()) return false;
        if (dest.hasDome() || dest.getBuilding().getHeight() >= 4) return false;
        if (dest.getBuilding().getHeight() - from.getBuilding().getHeight() > 1) return false;

        return true;
    }
}

