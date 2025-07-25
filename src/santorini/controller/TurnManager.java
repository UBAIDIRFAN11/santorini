package santorini.controller;

import santorini.model.*;
import santorini.model.Player;
import santorini.model.Worker;
import santorini.model.gods.SpecialPower;
import santorini.model.meters.BuildObserver;
import santorini.model.meters.ImpactLevel;
import santorini.model.loseconditions.LoseCondition;
import santorini.model.timing.TimerManager;
import santorini.view.BoardUI;
import santorini.model.winconditions.BasicWinCondition;
import santorini.model.actions.MoveAction;
import santorini.model.actions.BuildAction;
import santorini.model.actions.Action;
import santorini.view.SantoriniApp;
import javax.swing.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;


/**
 * Manages the turn-based game loop for the Santorini board game.
 * <p>
 * Handles movement, building, win/lose condition checking, god power interactions,
 * and user input processing across turns.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 * Modified by: Ubaid Irfan (Sprint 3 implementation)
 */

public class TurnManager {
    private Player current;
    private List<Player> players;
    private List<Worker> workers;
    private Board board;
    private BoardUI ui;
    private JLabel turnLabel;
    private CompletableFuture<Worker> pendingWorkerFuture;
    private CompletableFuture<Cell> pendingCellFuture;

    private Worker lastMovedWorker;

    private int totalFirstFloor;
    private int totalSecondFloor;
    private int totalThirdFloor;
    private int totalDome;

    private final TimerManager timerManager;

    private final List<BuildObserver> buildObservers;

    private final List<LoseCondition> loseConditions;

    private final SantoriniApp app;



    /**
     * Constructs the turn manager to control gameplay flow.
     *
     * @param players the list of players
     * @param board   the game board
     * @param ui      the board UI to update and receive input from
     * @param timerManager the timer manager to handle player timers
     * @param buildObservers observers to notify on build actions
     * @param loseConditions conditions to check for player loss
     *
     */
    public TurnManager(List<Player> players, Board board, BoardUI ui, TimerManager timerManager, List<BuildObserver> buildObservers, List<LoseCondition> loseConditions, SantoriniApp app) {
        this.players = players;
        this.board = board;
        this.ui = ui;
        this.current = players.get(0);
        this.workers = current.getWorkers();
        this.totalFirstFloor = 0;
        this.totalSecondFloor = 0;
        this.totalThirdFloor = 0;
        this.totalDome = 0;
        this.timerManager = timerManager;
        this.buildObservers = buildObservers;
        this.loseConditions = loseConditions;
        this.app = app;



        ui.attachTurnManager(this); // allow BoardUI to forward clicks


    }


    //using animations to show the current selectable workers for move, so not using this right now anymore
    private void highlightCurrentWorkers(boolean flag) {
        for (Worker w : workers) {
            Cell c = w.getCurrentCell();
            ui.highlight(c.getRow(), c.getCol(), flag);   // tells BoardUI to toggle highlight
        }
        ui.refresh();   // repaint the board to show the change
    }

    /**
     * Sets the JLabel that displays the current turn phase and player.
     *
     * @param label the label to update each phase
     */
    public void setTurnLabel(JLabel label) {
        this.turnLabel = label;
    }

    /**
     * Updates the turn label with the current player and turn phase.
     *
     * @param phase the name of the current turn phase
     */
    private void updateTurnLabel(String phase) {
        if (turnLabel != null) {
            turnLabel.setText("Turn: " + current.getName() + " — " + phase);
        }
    }

    private void notifyBuild(Player player, ImpactLevel level) {
        for (BuildObserver observer : buildObservers) {
            observer.onBuild(player, level);
        }
    }

    private void notifyRemove(Player player, ImpactLevel level) {
        for (BuildObserver observer : buildObservers) {
            observer.onRemove(player, level);
        }
    }


    /**
     * Checks if any player has lost based on the defined lose conditions.
     * If a player loses, it pauses their timer and displays the win screen for the other player.
     *
     * @param player the player to check for loss conditions
     * @return {@code true} if a player has lost, {@code false} otherwise
     */
    public boolean checkLoseConditions(Player player) {

        for (LoseCondition condition : loseConditions) {
            if (condition.loseCheck(current, board)) {
                int idx = players.indexOf(current);
                Player winner = players.get((idx + 1) % players.size());

                // stop timer
                timerManager.pause(current.getIndex());

                //new WinScreen(winner.getName()).setVisible(true);
                SwingUtilities.invokeLater(() -> app.showWin(winner.getName()));
                return true;
            }
        }
        return false;
    }


    /**
     * Starts the turn for the current player.
     * lose condition checks, and begins the movement phase, timer.
     */
    public void startTurn() {

        checkLoseConditions(players.get(0));
        board.clearHighlights();

        //resume player timer
        timerManager.resume(current.getIndex());

        ui.updateWorkerAnimationState(current, null);

        if (turnLabel != null) {
            turnLabel.setText("Turn: " + current.getName());
        }
        updateTurnLabel("Move Phase");
        //highlightCurrentWorkers(true);

        waitForWorker().thenAccept(this::handleWorkerSelectionForMove);
    }

    /**
     * Handles logic when the player selects a worker to move.
     *
     * @param selectedWorker the worker chosen for movement
     */
    private void handleWorkerSelectionForMove(Worker selectedWorker) {
        highlightCurrentWorkers(false);

        ui.updateWorkerAnimationState(current, selectedWorker);


        // trapped‐worker check
        if (!workerHasMoves(selectedWorker)) {
            JOptionPane.showMessageDialog(ui,
                    "This worker has no valid moves. Please select a different worker.");
            startTurn();
            return;
        }
        current.highlightMovableCells(board, selectedWorker);
        ui.refresh();

        waitForCell().thenAccept(firstDest -> {
            // Create and store the base move action
            Action baseMove = new MoveAction(board, players, selectedWorker, firstDest);
            Cell initialCell = selectedWorker.getCurrentCell(); // store original position before move

            if (!baseMove.execute()) {
                JOptionPane.showMessageDialog(null, "Invalid move: Cell is already occupied, or too far away. Please choose another cell.");

                startTurn();
                return;
            }
            //remembering the worker that just moved
            lastMovedWorker = selectedWorker;
            ui.refresh();

            if (new BasicWinCondition().winCheck(current)) {
                timerManager.pause(current.getIndex());
                //new WinScreen(current.getName()).setVisible(true);
                SwingUtilities.invokeLater(() -> app.showWin(current.getName()));
                return;
            }

            // Ask god power if it offers more moves (e.g., Artemis)
            List<Action> godMoves = current.getGodCard()
                    .getSpecialPower()
                    .modify(baseMove);   // pass the just‐executed action

            if (!godMoves.isEmpty()) {
                ui.showGodPowerPrompt("Use god power to move again?",
                        () -> { // YES
                            updateTurnLabel("God Power: Select second move");

                            board.clearHighlights();
                            for (Action a : godMoves) {
                                Cell c = a.getTargetCell();
                                if (c != null && !c.hasDome()) {
                                    board.highlightCell(c.getX(), c.getY());
                                }
                            }

                            ui.updateWorkerAnimationState(current, selectedWorker);
                            ui.refresh();

                            waitForCell().thenAccept(secondDest -> {
                                for (Action godMove : godMoves) {
                                    if (secondDest.equals(godMove.getTargetCell())) {
                                        if (godMove.execute()) {
                                            ui.refresh();
                                            if (new BasicWinCondition().winCheck(current)) {
                                                timerManager.pause(current.getIndex());
                                                //new WinScreen(current.getName()).setVisible(true);
                                                SwingUtilities.invokeLater(() -> app.showWin(current.getName()));
                                                return;
                                            }
                                        }
                                        break;
                                    }
                                }
                                ui.updateWorkerAnimationState(current, selectedWorker);
                                handleBuildPhase();
                            });
                        },
                        () -> { // NO
                            ui.updateWorkerAnimationState(current, selectedWorker);
                            handleBuildPhase();
                        }
                );
            }
            ui.updateWorkerAnimationState(current, selectedWorker);
            handleBuildPhase();
        });
    }

    /**
     * Checks if a worker has any legal move options from its current cell.
     *
     * @param w the worker to test
     * @return {@code true} if at least one move is valid
     */
    private boolean workerHasMoves(Worker w) {
        Cell from = w.getCurrentCell();
        for (Cell nbr : board.getNeighbourCells(from)) {
            Action move = new MoveAction(board, players, w, nbr);
            if (move.isLegal()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Begins the build phase after movement (or after using a god move).
     */
    private void handleBuildPhase() {
        board.clearHighlights();
        updateTurnLabel("Build Phase");
        highlightCurrentWorkers(true);
        // build with the Worker that just moved
        handleWorkerSelectionForBuild(lastMovedWorker);
    }

    /**
     * Handles logic when the player selects a worker to build with.
     *
     * @param selectedWorker the worker chosen to build
     */
    private void handleWorkerSelectionForBuild(Worker selectedWorker) {
        highlightCurrentWorkers(false);
        current.highlightBuildableCells(board, selectedWorker);
        ui.refresh();

        waitForCell().thenAccept(firstCell -> {
            int preHeight = firstCell.getBuilding().getHeight();

            Action baseBuild = new BuildAction(board, players, selectedWorker, firstCell);

            if (!baseBuild.execute()) {
                JOptionPane.showMessageDialog(null, "Invalid build: " + baseBuild.getErrorMessage());
                handleBuildPhase();
                return;
            }

            int postHeight = firstCell.getBuilding().getHeight();
            ImpactLevel level = ImpactLevel.fromHeight(postHeight);
            notifyBuild(current, level);

            //check if player lost after first build
            if (checkLoseConditions(current)) {
                return; // Stop the rest of the turn if player has lost
            }

            if (preHeight == 0 && postHeight == 1) {
                totalFirstFloor++;
                if (totalFirstFloor > 22) {
                    // Undo build
                    firstCell.getBuilding().removeLevel();
                    ui.showToast("Limit of 22 first-floor blocks reached.");
                    //JOptionPane.showMessageDialog(null, "Limit of 22 first-floor blocks reached.");
                    handleBuildPhase();
                    return;
                }
            } else if (preHeight == 1 && postHeight == 2) {
                totalSecondFloor++;
                if (totalSecondFloor > 18) {
                    firstCell.getBuilding().removeLevel();
                    ui.showToast("Limit of 18 second-floor blocks reached.");
                    //JOptionPane.showMessageDialog(null, "Limit of 18 second-floor blocks reached.");
                    handleBuildPhase();
                    return;
                }
            } else if (preHeight == 2 && postHeight == 3) {
                totalThirdFloor++;
                if (totalThirdFloor > 14) {
                    firstCell.getBuilding().removeLevel();
                    ui.showToast("Limit of 14 third-floor blocks reached.");
                    //JOptionPane.showMessageDialog(null, "Limit of 14 third-floor blocks reached.");
                    handleBuildPhase();
                    return;
                }
            } else if (postHeight == 4 ) {
                totalDome++;
                if (totalDome > 18) {
                    firstCell.getBuilding().removeDome();
                    ui.showToast("Limit of 18 domes reached.");
                    //JOptionPane.showMessageDialog(null, "Limit of 18 domes reached.");
                    handleBuildPhase();
                    return;
                }
            }

            ui.refresh();

            // Check if the god power offers a second build


            SpecialPower power = current.getGodCard().getSpecialPower();
            List<Action> additionalBuilds = power.modify(baseBuild);

            if (power.allowsSecondBuild() && !additionalBuilds.isEmpty()) {
                ui.showGodPowerPrompt("Use god power to build again?",
                        () -> {
                            updateTurnLabel("God Power: Select second build");

                            board.clearHighlights();
                            for (Action a : additionalBuilds) {
                                Cell c = a.getTargetCell();
                                if (c != null) board.highlightCell(c.getX(), c.getY());
                            }
                            ui.refresh();

                            waitForCell().thenAccept(secondCell -> {
                                for (Action godBuild : additionalBuilds) {
                                    if (secondCell.equals(godBuild.getTargetCell())) {
                                        int preHeight2 = secondCell.getBuilding().getHeight();

                                        if (godBuild.execute()) {
                                            int postHeight2 = secondCell.getBuilding().getHeight();
                                            ImpactLevel level2 = ImpactLevel.fromHeight(postHeight2);
                                            notifyBuild(current, level2);

                                            if (checkLoseConditions(current)) return;

                                            if (preHeight2 == 0 && postHeight2 == 1) {
                                                totalFirstFloor++;
                                                if (totalFirstFloor > 22) {
                                                    secondCell.getBuilding().removeLevel();
                                                    ui.showToast("Limit of 22 first-floor blocks reached.");
                                                    handleBuildPhase();
                                                    return;
                                                }
                                            } else if (preHeight2 == 1 && postHeight2 == 2) {
                                                totalSecondFloor++;
                                                if (totalSecondFloor > 18) {
                                                    secondCell.getBuilding().removeLevel();
                                                    ui.showToast("Limit of 18 second-floor blocks reached.");
                                                    handleBuildPhase();
                                                    return;
                                                }
                                            } else if (preHeight2 == 2 && postHeight2 == 3) {
                                                totalThirdFloor++;
                                                if (totalThirdFloor > 14) {
                                                    secondCell.getBuilding().removeLevel();
                                                    ui.showToast("Limit of 14 third-floor blocks reached.");
                                                    handleBuildPhase();
                                                    return;
                                                }
                                            } else if (postHeight2 == 4) {
                                                totalDome++;
                                                if (totalDome > 18) {
                                                    secondCell.getBuilding().removeDome();
                                                    ui.showToast("Limit of 18 domes reached.");
                                                    handleBuildPhase();
                                                    return;
                                                }
                                            }

                                            ui.refresh();
                                        } else {
                                            ui.showToast("Invalid god power build.");
                                        }
                                        break;
                                    }
                                }
                                checkLoseConditions(players.get(0));
                                handleRemoveBuildPhase();
                            });
                        },
                        () -> {
                            board.clearHighlights();
                            checkLoseConditions(players.get(0));
                            ui.updateWorkerAnimationState(current, selectedWorker);
                            handleRemoveBuildPhase();
                        }
                );
                return;
            }


            board.clearHighlights();
            checkLoseConditions(players.get(0));
            ui.updateWorkerAnimationState(current, selectedWorker);
            handleRemoveBuildPhase();
        });
    }

    private void handleRemoveBuildPhase() {
        board.clearHighlights();
        updateTurnLabel("Remove Build Phase");
        highlightCurrentWorkers(false);
        // remove build with the Worker that just moved
        handleWorkerSelectionForRemoveBuild(lastMovedWorker);
    }


    /**
     * Handles the logic for removing a building using a god power.
     * If the god power allows removal, it highlights valid cells and waits for player input.
     */
    private void handleWorkerSelectionForRemoveBuild(Worker selectedWorker) {
        SpecialPower power = current.getGodCard().getSpecialPower();
        if (!power.allowsRemove()) {
            endTurn();
            return;
        }

        List<Action> removeOptions = power.getRemoveOptions(board, players, lastMovedWorker);
        if (removeOptions.isEmpty()) {
            endTurn();
            return;
        }

        ui.showGodPowerPrompt("Use god power to remove a block?",
                () -> { // YES
                    updateTurnLabel("God Power: Select a block to remove");
                    board.clearHighlights();
                    for (Action action : removeOptions) {
                        Cell cell = action.getTargetCell();
                        if (cell != null) board.highlightCell(cell.getX(), cell.getY());
                    }
                    ui.refresh();

                    waitForCell().thenAccept(chosenCell -> {
                        for (Action action : removeOptions) {
                            if (chosenCell.equals(action.getTargetCell()) && action.execute()) {
                                Cell cell = action.getTargetCell();
                                int height = cell.getBuilding().getHeight();
                                ImpactLevel level = cell.hasDome() ? ImpactLevel.DOME : ImpactLevel.fromHeight(height + 1);
                                notifyRemove(current, level);
                                ui.refresh();
                                break;
                            }
                        }
                        ui.updateWorkerAnimationState(current, selectedWorker);
                        endTurn();
                    });
                },
                () -> { // NO
                    endTurn();
                }
        );

        board.clearHighlights();
        for (Action action : removeOptions) {
            Cell cell = action.getTargetCell();
            if (cell != null) board.highlightCell(cell.getX(), cell.getY());
        }
        ui.refresh();

        waitForCell().thenAccept(chosenCell -> {
            for (Action action : removeOptions) {
                if (chosenCell.equals(action.getTargetCell()) && action.execute()) {

                    // Determine impact level BEFORE updating UI
                    Cell cell = action.getTargetCell();
                    int height = cell.getBuilding().getHeight();
                    ImpactLevel level = cell.hasDome() ? ImpactLevel.DOME : ImpactLevel.fromHeight(height + 1); // +1 to get the level that was just removed

                    notifyRemove(current, level); // This is what triggers NatureMeterManager.onRemove



                    ui.refresh();
                    break;
                }
            }
            ui.updateWorkerAnimationState(current, selectedWorker);
            endTurn();
        });
    }

    /**
     * Ends the current player's turn, rotates to the next player, and restarts turn logic, pauses that player's timer.
     */
    public void endTurn() {
        System.out.println("Ending turn for " + current.getName());

        // stop timer for the current player once their turn is over
        timerManager.pause(current.getIndex());
        checkLoseConditions(players.get(0));

        // Rotate players
        players.remove(0);
        players.add(current);
        current = players.get(0);
        workers = current.getWorkers();

        board.clearHighlights();
        startTurn();
    }

    /**
     * Handles the board click and routes it to worker or cell selection logic.
     *
     * @param row clicked row index
     * @param col clicked column index
     */
    public void handleClick(int row, int col) {
        Cell cell = board.getCell(row, col);

        Worker w = cell.getWorker();

        // Check for worker selection
        if (pendingWorkerFuture != null && !pendingWorkerFuture.isDone()) {
            if (w != null && workers.contains(w)) {
                pendingWorkerFuture.complete(w);
            }
            return;
        }

        // Check for cell selection (movement/building)
        if (pendingCellFuture != null && !pendingCellFuture.isDone()) {
            if (cell.getIsHighlighted()) {
                pendingCellFuture.complete(cell);
            } else {
                //JOptionPane.showMessageDialog(ui, "Invalid selection. Please click a highlighted cell.");
                ui.showToast("Invalid selection. Please click a highlighted cell.");

            }
        }
    }

    /**
     * Initiates asynchronous waiting for the player to click a worker.
     *
     * @return a future that resolves with the selected worker
     */
    private CompletableFuture<Worker> waitForWorker() {
        pendingWorkerFuture = new CompletableFuture<>();
        return pendingWorkerFuture;
    }

    /**
     * Initiates asynchronous waiting for the player to click a cell.
     *
     * @return a future that resolves with the selected cell
     */
    private CompletableFuture<Cell> waitForCell() {
        pendingCellFuture = new CompletableFuture<>();
        return pendingCellFuture;
    }
}