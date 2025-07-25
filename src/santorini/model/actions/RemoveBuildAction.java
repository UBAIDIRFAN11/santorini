package santorini.model.actions;

import santorini.model.*;
import santorini.model.meters.ImpactLevel;

import java.util.List;

/**
 * Represents an action to remove a block from a cell in the Santorini game.
 *
 *  Author: Ubaid Irfan (Sprint 3 implementation)
 */
public class RemoveBuildAction extends Action {
    private final Cell targetCell;
    private ImpactLevel impactLevel; //track what was removed

    /**
     * Constructs a RemoveBuildAction for a specific worker and target cell.
     *
     * @param board      the game board
     * @param players    the list of players in the game
     * @param worker     the worker performing the removal
     * @param targetCell the cell from which to remove a build
     */
    public RemoveBuildAction(Board board, List<Player> players, Worker worker, Cell targetCell) {
        super(ActionType.REMOVE_BUILD, board, players, worker);
        this.targetCell = targetCell;
    }

    /**
     * Executes the removal action if it is legal.
     * <p>
     * Removes a level or dome from the target cell, updating the impact level accordingly.
     *
     * @return {@code true} if the removal was successful; {@code false} otherwise
     */
    @Override
    public boolean execute() {
        if (!isLegal()) return false;

        Building building = targetCell.getBuilding();
        int preHeight = building.getHeight();

        if (targetCell.hasDome()) {
            impactLevel = ImpactLevel.DOME;
            building.removeDome();
        } else {
            impactLevel = ImpactLevel.fromHeight(preHeight);
            building.removeLevel();
        }

        return true;
    }

    /**
     * Returns the impact level of the removal action.
     *
     * @return the {@link ImpactLevel} indicating what was removed
     */
    @Override
    public boolean isLegal() {
        if (targetCell == null || worker == null) return false;

        Cell from = worker.getCurrentCell();
        boolean isAdjacentOrSame = board.getNeighbourCells(from).contains(targetCell) || targetCell.equals(from);

        if (!isAdjacentOrSame) return false;

        Building b = targetCell.getBuilding();
        return b.getHeight() > 0 || targetCell.hasDome();
    }

    /**
     * Returns the impact level of the removal action.
     *
     * @return the {@link ImpactLevel} indicating what was removed
     */
    @Override
    public Cell getTargetCell() {
        return targetCell;
    }
}
