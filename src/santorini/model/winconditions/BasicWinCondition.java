package santorini.model.winconditions;

import java.util.List;
import santorini.model.*;
import santorini.model.Player;
import santorini.model.Worker;


/**
 * Represents the basic win condition in the Santorini game.
 * <p>
 * A player wins if any of their workers moves onto the third level of a building.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 * Modified by: Ubaid Irfan (Sprint 3 implementation)
 */
public class BasicWinCondition implements WinCondition {

    /**
     * Checks if the player has won by moving a worker onto the third level of a building.
     *
     * @param player the player to check for a win condition
     * @return {@code true} if the player has won; {@code false} otherwise
     */
    @Override
    public boolean winCheck(Player player) {
        for (Worker worker : player.getWorkers()) {
            Cell from = worker.getPreviousCell();
            Cell to = worker.getCurrentCell();

            if (from == null || to == null) continue;

            int fromHeight = from.getBuilding().getHeight();
            int toHeight = to.getBuilding().getHeight();

            //only trigger win when moving from lvl 2 --> 3
            if (fromHeight == 2 && toHeight == 3) {
                return true;
            }
        }
        return false;
    }
}