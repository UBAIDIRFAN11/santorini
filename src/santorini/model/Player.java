package santorini.model;

import santorini.model.gods.GodCard;
import santorini.model.meters.MeterStrategy;
import santorini.model.meters.MeterType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Represents a player in the Santorini game.
 * <p>
 * Each player has a name, a set of workers, a god card ability,
 * and a designated worker color. This class includes methods to manage
 * worker actions such as movement and building highlights on the game board.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 * Modified by: Ubaid Irfan (Sprint 3 implementation)
 */
public class Player {
    private String name;
    private List<Worker> workers = new ArrayList<>();
    private GodCard godCard;
    private String workerColour; // b = blue, r = red
    private final int index;


    private final Map<MeterType, MeterStrategy> meters = new HashMap<>();


    /**
     * Constructs a player with the specified name.
     *
     * @param name the player's name
     */
    public Player(String name, int index) {
        this.name = name;
        this.index = index;
    }

    /**
     * Returns the name of the player.
     *
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the list of workers assigned to the player.
     *
     * @return a list of {@link Worker} objects
     */
    public List<Worker> getWorkers() {
        return workers;
    }

    /**
     * Adds a worker to the player's list of workers.
     *
     * @param worker the {@link Worker} to add
     */
    public void addWorker(Worker worker) {
        workers.add(worker);
    }

    /**
     * Highlights valid cells the specified worker can move to.
     * <p>
     * Valid cells must be adjacent, unoccupied, without a dome,
     * and no more than one level higher than the worker's current cell.
     *
     * @param B the game board
     * @param w the active worker
     */
    public void highlightMovableCells(Board B, Worker w) {
        Cell currentCell = w.getCurrentCell();
        List<Cell> adjacentCells = B.getNeighbourCells(currentCell);

        for (int j = 0; j < adjacentCells.size(); j++) {
            Cell neighbor = adjacentCells.get(j);
            int fromHeight = w.getCurrentCell().getBuilding().getHeight();
            int toHeight = neighbor.getBuilding().getHeight();

            if (!neighbor.getIsOccupied()
                    && !neighbor.hasDome()
                    && (toHeight - fromHeight) <= 1) {
                B.highlightCell(neighbor.getX(), neighbor.getY());
            }
        }
    }


    /**
     * Highlights valid cells where the specified worker can build.
     * <p>
     * Valid cells must be adjacent, unoccupied, without a dome,
     * and with a building height less than 4.
     * Additionally, it checks for any extra buildable cells provided by the god card's special power.
     *
     * @param board  the game board
     * @param worker the active worker
     */
    public void highlightBuildableCells(Board board, Worker worker) {
        Cell currentCell = worker.getCurrentCell();
        List<Cell> adjacentCells = board.getNeighbourCells(currentCell);

        // by default, highlight normal buildable adjacent cells
        for (Cell neighbor : adjacentCells) {
            if (!neighbor.getIsOccupied()
                    && !neighbor.hasDome()
                    && neighbor.getBuilding().getHeight() < 4) {
                board.highlightCell(neighbor.getX(), neighbor.getY());
            }
        }

        //ask god power for extra valid build cells
        GodCard godCard = this.getGodCard();
        if (godCard != null && godCard.getSpecialPower() != null) {
            List<Cell> extraCells = godCard.getSpecialPower().getAdditionalBuildCells(board, worker);
            for (Cell cell : extraCells) {
                board.highlightCell(cell.getX(), cell.getY());
            }
        }
    }

    /**
     * Assigns a god card to this player, granting special abilities.
     *
     * @param godCard the {@link GodCard} to assign
     */
    public void setGodCard(GodCard godCard) {
        this.godCard = godCard;
    }

    /**
     * Retrieves the god card assigned to the player.
     *
     * @return the player's {@link GodCard}
     */
    public GodCard getGodCard() {
        return godCard;
    }

    /**
     * Sets the player's worker color.
     *
     * @param color the color to assign (e.g., "blue", "red")
     */
    public void setWorkerColor(String color) {
        this.workerColour = color;
    }

    /**
     * Returns the player's worker color.
     *
     * @return the worker color
     */
    public String getWorkerColor() {
        return workerColour;
    }

    /**
     * Returns the index of the player.
     * @return the player's index
     */
    public int getIndex() {return index;}

    /**
     * Sets a meter strategy for the player.
     *
     * @param type  the type of meter (e.g., MOVE, BUILD)
     * @param meter the {@link MeterStrategy} to set
     */
    public void setMeter(MeterType type, MeterStrategy meter) {
        meters.put(type, meter);
    }

    /**
     * Retrieves the meter strategy for a specific type.
     *
     * @param type the type of meter to retrieve
     * @return the {@link MeterStrategy} for the specified type, or null if not set
     */
    public MeterStrategy getMeter(MeterType type) {
        return meters.get(type);
    }
}
