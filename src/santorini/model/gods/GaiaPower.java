package santorini.model.gods;

import santorini.model.*;
import santorini.model.actions.Action;
import santorini.model.actions.ActionType;
import santorini.model.actions.RemoveBuildAction;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents the special power of Gaia in the Santorini game, allowing a worker to remove an existing block.
 *
 * Author: Ubaid Irfan (Sprint 3 implementation)
 */
public class GaiaPower extends SpecialPower {

    /**
     * Constructs the Gaia power with its name and effect description.
     */
    public GaiaPower() {
        super("Gaia", "Your worker may remove an existing block.");
    }

    /**
     * Modifies the action to allow the worker to remove blocks from adjacent cells.
     *
     * @param action the original {@link Action} performed
     * @return a list of additional valid {@link Action}s for removing blocks
     */
    @Override
    public List<Action> modify(Action action) {
        if (action.getType() != ActionType.REMOVE_BUILD) return List.of();

        Worker worker = action.getWorker();
        Player player = worker.getOwner();
        Board board = action.getBoard();

        List<Action> removes = new ArrayList<>();
        for (Cell cell : board.getNeighbourCells(worker.getCurrentCell())) {
            Building b = cell.getBuilding();
            if (b.getHeight() > 0 || cell.hasDome()) {
                removes.add(new RemoveBuildAction(board, List.of(player), worker, cell));
            }
        }
        return removes;
    }


    /**
     * Indicates whether this power allows the removal of blocks.
     * @return {@code true} if removal is allowed, {@code false} otherwise
     */
    @Override
    public boolean allowsRemove() { return true; };

    /**
     * Returns a list of legal remove options for the given worker.
     * <p>
     * This includes adjacent cells and the worker's own cell.
     *
     * @param board   the game board
     * @param players the list of players in the game
     * @param worker  the worker performing the removal
     * @return a list of legal {@link Action}s for removing blocks
     */
    @Override
    public List<Action> getRemoveOptions(Board board, List<Player> players, Worker worker) {
        List<Action> options = new ArrayList<>();

        // Include adjacent cells
        for (Cell nbr : board.getNeighbourCells(worker.getCurrentCell())) {
            Action a = new RemoveBuildAction(board, players, worker, nbr);
            if (a.isLegal()) options.add(a);
        }

        // Also allow removing from the worker's own cell
        Cell ownCell = worker.getCurrentCell();
        if (ownCell != null) {
            Action selfRemove = new RemoveBuildAction(board, players, worker, ownCell);
            if (selfRemove.isLegal()) options.add(selfRemove);
        }

        return options;
    }


}