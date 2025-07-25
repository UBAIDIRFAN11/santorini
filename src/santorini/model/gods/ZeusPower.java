package santorini.model.gods;

import santorini.model.Board;
import santorini.model.Cell;
import santorini.model.Worker;
import santorini.model.actions.Action;
import santorini.model.actions.ActionType;
import santorini.model.actions.BuildAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the special power of Zeus in the Santorini game, allowing a worker to build a block under itself.
 *
 * Author: Ubaid Irfan (Sprint 3 implementation)
 */
public class ZeusPower extends SpecialPower {

    /**
     * Constructs a Zeus power with its name and description.
     */
    public ZeusPower() {
        super("Zeus", "Your Worker may build a block under itself.");
    }

    /**
     * Modifies the build action to allow building a block under the worker's current cell.
     *
     * @param action the original {@link Action} performed
     * @return a list of modified {@link Action}s that include the additional build action
     */
    @Override
    public List<Action> modify(Action action) {
        List<Action> modified = new ArrayList<>();

        if (action.getType() == ActionType.BUILD) {
            Worker worker = action.getWorker();
            Cell currentCell = worker.getCurrentCell();
            Board board = action.getBoard();

            //only allow if height<3, not dome, and not winning move
            if (!currentCell.hasDome() && currentCell.getBuilding().getHeight() < 3) {
                modified.add(new BuildAction(board, action.getPlayers(), worker, currentCell));
            }
        }

        return modified;
    }

    /**
     * Returns a list of additional cells where the worker can build, based on Zeus's special power.
     *
     * @param board  the game board
     * @param worker the worker performing the build
     * @return a list of additional {@link Cell}s where building is allowed
     */
    @Override
    public List<Cell> getAdditionalBuildCells(Board board, Worker worker) {
        Cell current = worker.getCurrentCell();
        if (!current.hasDome() && current.getBuilding().getHeight() < 3) {
            return Collections.singletonList(current);
        }
        return Collections.emptyList();
    }

    /**
     * Checks if the worker can build on an occupied cell.
     *
     * @param cell   the cell to check
     * @param worker the worker performing the build
     * @return true if the worker can build on the occupied cell, false otherwise
     */
    @Override
    public boolean canBuildOnOccupiedCell(Cell cell, Worker worker) {
        //zeus can build underneath worker
        return cell.equals(worker.getCurrentCell());
    }

}
