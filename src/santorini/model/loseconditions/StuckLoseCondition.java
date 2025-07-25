package santorini.model.loseconditions;

import java.util.List;
import santorini.model.*;
import santorini.model.Player;
import santorini.model.Worker;


/**
 * Represents a standard lose condition where a player has no legal moves.
 * <p>
 * This condition checks if all of a player's workers are stuck (i.e., unable to move
 * to any adjacent valid cell).
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 */
public class StuckLoseCondition implements LoseCondition {

    /**
     * Determines whether the player has lost because none of their workers can move.
     *
     * @param P the player to check
     * @param B the current game board
     * @return {@code true} if all the player's workers are stuck; {@code false} otherwise
     */
    @Override
    public boolean loseCheck(Player P, Board B) {
        List<Worker> workers = P.getWorkers();

        for (int i = 0; i < workers.size(); i++) {
            Worker w = workers.get(i);
            Cell currentCell = w.getCurrentCell();
            List<Cell> adjacentCells = B.getNeighbourCells(currentCell);

            for (int j = 0; j < adjacentCells.size(); j++) {
                Cell neighbor = adjacentCells.get(j);
                int fromHeight = currentCell.getBuilding().getHeight();
                int toHeight = neighbor.getBuilding().getHeight();

                if (neighbor.getWorker() == null && toHeight <= fromHeight + 1 && !neighbor.hasDome()) {
                    return false; // At least one move is possible
                }
            }
        }

        return true; // All workers are stuck
    }
}