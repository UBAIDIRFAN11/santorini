package santorini.model.winconditions;

import santorini.model.Player;


/**
 * Interface for defining win conditions in the Santorini game.
 * <p>
 * Implementing classes must provide logic to determine if a player
 * has fulfilled the criteria to win the game.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 */
public interface WinCondition {

    /**
     * Checks whether the specified player has met the win condition.
     *
     * @param P the player to check
     * @return {@code true} if the player has won; {@code false} otherwise
     */
    boolean winCheck(Player P);
}