package santorini.model.gods;

import santorini.model.Cell;
import santorini.model.actions.Action;
import santorini.model.actions.ActionType;
import santorini.model.actions.MoveAction;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents the special power of Artemis in the Santorini game.
 * <p>
 * After moving, the worker may move one additional time, but not back to the initial space.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 */
public class ArtemisPower extends SpecialPower {

    private Cell initialCell;

    /**
     * Constructs the Artemis power with its name and description.
     */
    public ArtemisPower() {
        super("Artemis", "Your Worker may move one additional time, but not back to its initial space.");
    }

    /**
     * Modifies the original move action to allow a second movement,
     * excluding the cell the worker initially moved from.
     *
     * @param action the initial {@link MoveAction} executed by the worker
     * @return a list of additional {@link MoveAction}s that represent valid second moves
     */
    @Override
    public List<Action> modify(Action action) {
        List<Action> poweredMoves = new ArrayList<>();

        if (action.getType() == ActionType.MOVE) {
            MoveAction m = (MoveAction) action;

            Cell current = m.getTargetCell();
            Cell original = m.getSourceCell();

            for (Cell neighbour : action.getBoard().getNeighbourCells(current)) {
                if (!neighbour.equals(original)
                        && !neighbour.getIsOccupied()
                        && neighbour.getBuilding().getHeight() <= current.getBuilding().getHeight() + 1) {

                    poweredMoves.add(new MoveAction(
                            action.getBoard(),
                            action.getPlayers(),
                            action.getWorker(),
                            neighbour
                    ));
                }
            }
        }

        return poweredMoves;
    }
}