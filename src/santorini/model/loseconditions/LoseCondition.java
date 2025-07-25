package santorini.model.loseconditions;

import santorini.model.*;
import santorini.model.Player;

/**
 * Interface for defining lose conditions in the Santorini game.
 * <p>
 * Implementing classes must provide logic to determine if a given player
 * has lost based on the current state of the game board.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 */
public interface LoseCondition {

    /**
     * Checks if the specified player has lost the game based on the board state.
     *
     * @param P the player to check
     * @param B the current game board
     * @return {@code true} if the player has lost; {@code false} otherwise
     */
    boolean loseCheck(Player P, Board B);
}
