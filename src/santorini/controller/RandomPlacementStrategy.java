package santorini.controller;

import santorini.model.*;
import santorini.model.Player;
import santorini.model.Worker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * Implements a strategy for randomly placing workers on the game board.
 * <p>
 * Each player is assigned a fixed number of workers, and workers are placed
 * randomly in unoccupied cells at the start of the game.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 */
public class RandomPlacementStrategy implements WorkerPlacementStrategy {

    private static final int WORKERS_PER_PLAYER = 2;

    /**
     * Places workers for all players randomly on the board.
     * <p>
     * This method shuffles all available cells and assigns the first N cells to workers,
     * where N = number of players × {@code WORKERS_PER_PLAYER}.
     *
     * @param board   the game board
     * @param players the list of players to assign workers to
     */
    @Override
    public void placeWorkers(Board board, List<Player> players) {
        List<Cell> allCells = new ArrayList<>();

        // Gather all cells on the board
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                allCells.add(board.getCell(row, col));
            }
        }

        // Shuffle cells to randomize placement
        Collections.shuffle(allCells, new Random());

        int index = 0;
        for (Player player : players) {
            for (int i = 0; i < WORKERS_PER_PLAYER; i++) {
                Cell cell = allCells.get(index++);
                Worker worker = new Worker(player);
                player.addWorker(worker);
                worker.setCurrentCell(cell);
                cell.setWorker(worker);
            }
        }
    }
}
