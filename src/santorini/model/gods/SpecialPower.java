package santorini.model.gods;

import santorini.model.Board;
import santorini.model.Cell;
import santorini.model.Player;
import santorini.model.Worker;
import santorini.model.actions.Action;

import java.util.ArrayList;
import java.util.List;


/**
 * Abstract base class for defining special god powers in the Santorini game.
 * <p>
 * Each power has a name, description, and a method that modifies a base action.
 * Subclasses implement specific effects such as extra moves or builds.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 * Modified by: Ubaid Irfan (Sprint 3 implementation)
 */
public abstract class SpecialPower {
    private final String name;
    private final String description;

    /**
     * Constructs a special power with a given name and description.
     *
     * @param name        the name of the power (usually the god's name)
     * @param description a short description of what the power does
     */
    public SpecialPower(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Returns the name of the special power.
     *
     * @return the name of the power
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the special power.
     *
     * @return the power's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Modifies a base action and returns a list of additional actions
     * allowed by the god's power.
     *
     * @param action the original action to be modified
     * @return a list of additional or alternative {@link Action}s
     */
    public abstract List<Action> modify(Action action);


    /**
     * Returns a list of additional cells where the worker can build, based on the god's special power.
     *
     * @param board  the game board
     * @param worker the worker performing the build
     * @return a list of additional {@link Cell}s where building is allowed
     */
    public List<Cell> getAdditionalBuildCells(Board board, Worker worker) {
        return new ArrayList<>(); // Default: no additional build cells
    }

    /**
     * Checks if the worker can build on an occupied cell.
     *
     * @param cell   the cell to check
     * @param worker the worker attempting to build
     * @return {@code true} if building is allowed on the occupied cell; {@code false} otherwise
     */
    public boolean canBuildOnOccupiedCell(Cell cell, Worker worker) {
        return false; // Default behavior for all gods
    }

    /**
     * Checks if the god's power allows a second build action in the same turn.
     *
     * @return {@code true} if a second build is allowed; {@code false} otherwise
     */
    public boolean allowsSecondBuild() {
        return false; // default: no second build
    }

    /**
     * Checks if the god's power allows the worker to remove a building.
     * This is used for gods that allow removing buildings.
     *
     * @return {@code true} if removal is allowed; {@code false} otherwise
     */
    public boolean allowsRemove() {
        return false; // default: no remove
    }

    /**
     * Returns a list of actions that allow the worker to remove a building.
     * This is used for gods that allow removing buildings.
     *
     * @param board   the game board
     * @param players the list of players in the game
     * @param worker  the worker performing the removal
     * @return a list of {@link Action}s that represent removal options
     */
    public List<Action> getRemoveOptions(Board board, List<Player> players, Worker worker) {
        return new ArrayList<>(); // Default: no remove options
    }
}

