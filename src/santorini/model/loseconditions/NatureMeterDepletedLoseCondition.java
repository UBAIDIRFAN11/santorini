package santorini.model.loseconditions;

import santorini.model.Board;
import santorini.model.Player;
import santorini.model.meters.MeterType;

/**
 * Represents a lose condition where a player loses if their nature meter is depleted.
 *
 * Author: Ubaid Irfan (Sprint 3 implementation)
 */
public class NatureMeterDepletedLoseCondition implements LoseCondition {

    /**
     * Checks if the specified player has lost the game due to a depleted nature meter.
     *
     * @param p the player to check
     * @param b the current game board (not used in this condition)
     * @return {@code true} if the player's nature meter is depleted; {@code false} otherwise
     */
    @Override
    public boolean loseCheck(Player p, Board b) {
        return p.getMeter(MeterType.NATURE).isDepleted();
    }
}
