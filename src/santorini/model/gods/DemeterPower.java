package santorini.model.gods;

import santorini.model.*;
import santorini.model.actions.Action;
import santorini.model.actions.ActionType;
import santorini.model.actions.BuildAction;
import santorini.model.Worker;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents the special power of Demeter in the Santorini game.
 * <p>
 * Demeter allows a worker to build an additional time on a different space.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 * Modified by: Ubaid Irfan (Sprint 3 implementation)
 */
public class DemeterPower extends SpecialPower {

    private Cell firstBuildCell;

    /**
     * Constructs the Demeter power with its name and effect description.
     */
    public DemeterPower() {
        super("Demeter", "Your Worker may build one additional time, but not on the same space.");
    }

    /**
     * Modifies the initial build action to include all valid secondary builds,
     * excluding the space used in the first build.
     *
     * @param action the original {@link BuildAction} performed
     * @return a list of additional valid {@link BuildAction}s
     */
    @Override
    public List<Action> modify(Action action) {
        List<Action> modifiedActions = new ArrayList<>();

        if (action.getType() == ActionType.BUILD && action.getTargetCell() != null) {
            firstBuildCell = action.getTargetCell();
            Worker worker = action.getWorker();
            Board board = action.getBoard();

            List<Cell> neighbours = board.getNeighbourCells(worker.getCurrentCell());

            for (Cell cell : neighbours) {
                if (!cell.equals(firstBuildCell)
                        && !cell.getIsOccupied()
                        && cell.getBuilding().getHeight() < 4) {
                    modifiedActions.add(new BuildAction(board, action.getPlayers(), worker, cell));
                }
            }
        }

        return modifiedActions;
    }


    /**
     * Returns the first build cell used by Demeter's power.
     *
     * @return the cell where the first build occurred
     */
    @Override
    public boolean allowsSecondBuild() {
        return true;
    }
}
