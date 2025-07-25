package santorini.model.actions;

import santorini.model.*;
import santorini.model.Player;
import santorini.model.Worker;

import java.util.List;


/**
 * Abstract base class representing an action in the Santorini game.
 * <p>
 * Concrete subclasses such as {@code MoveAction} or {@code BuildAction}
 * define the specific behavior of a game action. Each action involves
 * a worker, the board, and a list of players.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 * Modified by: Ubaid Irfan (Sprint 3 implementation)
 */
public abstract class Action {

    protected final ActionType type;
    protected final Board board;
    protected final List<Player> players;
    protected final Worker worker;

    private String failureReason = "";

    /**
     * Constructs an Action with the specified type, board, players, and worker.
     *
     * @param type    the type of action (e.g., MOVE, BUILD)
     * @param board   the game board on which the action occurs
     * @param players the list of players participating in the game
     * @param worker  the worker performing the action
     */
    public Action(ActionType type, Board board, List<Player> players, Worker worker) {
        this.type = type;
        this.board = board;
        this.players = players;
        this.worker = worker;
    }

    /**
     * Executes the action.
     * <p>
     * Concrete subclasses must implement this method to define
     * how the action affects the game state.
     *
     * @return {@code true} if the action was successful, otherwise {@code false}
     */
    public abstract boolean execute();

    /**
     * Returns the worker performing the action.
     *
     * @return the {@link Worker} object
     */
    public Worker getWorker() {
        return worker;
    }

    /**
     * Returns the type of the action.
     *
     * @return the {@link ActionType}
     */
    public ActionType getType() {
        return type;
    }

    /**
     * Returns the game board where the action takes place.
     *
     * @return the {@link Board}
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Flags the action as failed and records the failure reason.
     *
     * @param why a message explaining why the action failed
     */
    protected void fail(String why) {
        failureReason = why;
    }

    /**
     * Checks whether the action was successful.
     *
     * @return {@code true} if the action succeeded, otherwise {@code false}
     */
    public boolean isSuccess() {
        return failureReason.isEmpty();
    }

    /**
     * Returns the reason the action failed, if any.
     *
     * @return an error message string, or an empty string if the action succeeded
     */
    public String getErrorMessage() {
        return failureReason;
    }

    /**
     * Returns the target cell of the action.
     * <p>
     * This method is overridden in subclasses such as {@code BuildAction}
     * that require a target cell. Defaults to {@code null}.
     *
     * @return the target {@link Cell}, or {@code null} by default
     */
    public Cell getTargetCell() {
        return null;
    }

    /**
     * Returns the list of players in the game.
     *
     * @return a list of {@link Player} objects
     */
    public List<Player> getPlayers() {
        return players;
    }


    /**
        * Checks whether the action is legal according to game rules.
     */
    public boolean isLegal() {
        return true;
    }
}
