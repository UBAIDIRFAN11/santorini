package santorini.controller;

import santorini.model.Board;
import santorini.model.Player;

import java.util.List;


/**
 * Strategy interface for placing workers at the start of a Santorini game.
 * <p>
 * Implementations define how and where workers are positioned on the board.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 */
public interface WorkerPlacementStrategy {

    /**
     * Places all players' workers on the provided game board.
     *
     * @param board   the game board
     * @param players the list of players whose workers need to be placed
     */
    void placeWorkers(Board board, List<Player> players);
}
