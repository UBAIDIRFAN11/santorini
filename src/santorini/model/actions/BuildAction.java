package santorini.model.actions;

import santorini.model.*;

import java.util.List;


/**
 * Represents a build action in the Santorini game.
 * <p>
 * This action attempts to construct a level or dome on a valid, unoccupied neighboring cell.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 * Modified by: Ubaid Irfan (Sprint 3 implementation)
 */
public class BuildAction extends Action {

    private final Cell targetCell;

    /**
     * Constructs a build action for a specific worker and target cell.
     *
     * @param board      the game board
     * @param players    the list of players in the game
     * @param worker     the worker performing the build
     * @param targetCell the cell where the build should occur
     */
    public BuildAction(Board board, List<Player> players, Worker worker, Cell targetCell) {
        super(ActionType.BUILD, board, players, worker);
        this.targetCell = targetCell;
    }

    /**
     * Executes the build action if it is legal.
     * <p>
     * Adds a level to the target cell or a dome if the height is already at 4.
     *
     * @return {@code true} if the build was successful; {@code false} otherwise
     */
    @Override
    public boolean execute() {
        if (!isLegal()) {
            return false;
        }

        Building building = targetCell.getBuilding();
        if (building.getHeight() < 4) {
            building.addLevel();
        } else if (!building.hasDome()) {
            building.addDome(targetCell, board);
        }
        return true;
    }

    /**
     * Checks whether the build action is legal based on game rules.
     *
     * @return {@code true} if the build is legal; {@code false} otherwise
     */
    @Override
    public boolean isLegal() {
        Cell from = worker.getCurrentCell();
        if (from == null || targetCell == null) {
            fail("Worker or target cell is null.");
            return false;
        }

        boolean isAdjacent = board.getNeighbourCells(from).contains(targetCell);
        boolean isCurrentCell = targetCell.equals(from);

        if (!isAdjacent && !isCurrentCell) {
            fail("Target cell is not adjacent or current.");
            return false;
        }

        //check if building on occupied cells is allowed
        boolean canBuildHere = !targetCell.getIsOccupied() ||
                worker.getOwner().getGodCard().getSpecialPower().canBuildOnOccupiedCell(targetCell, worker);

        if (!canBuildHere) {
            fail("Cell is occupied.");
            return false;
        }

        if (targetCell.getBuilding().getHeight() >= 4) {
            fail("Cell has a dome.");
            return false;
        }

        return true;
    }

    /**
     * Returns the target cell where the build action will occur.
     *
     * @return the target {@link Cell}
     */
    @Override
    public Cell getTargetCell() {
        return targetCell;
    }
}
