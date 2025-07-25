package santorini.model.timing;

import santorini.model.Player;
import santorini.view.SantoriniApp;

import javax.swing.*;
import java.util.List;

/**
 * This class is responsible for handling a timeout, i.e. what happens when a player's time runs out.
 * In the event of a timeout, the player at that position loses, and the next player wins, displaying
 * the relevant message in a WinScreen.
 *
 * Author: Ubaid Irfan (Sprint 3 implementation)
 */
public class TimeOutHandler {
    /**
     * Constructs a TimeOutHandler that sets up the timeout behaviour for each player.
     *
     * @param players the list of players in the game
     * @param timers  the list of GameTimers corresponding to each player
     */
    public TimeOutHandler(List<Player> players, List<GameTimer> timers, SantoriniApp app) {
        for (int i = 0; i < players.size(); i++) {
            Player loser = players.get(i);
            Player winner = players.get((i + 1) % players.size());
            GameTimer timer = timers.get(i);

            timer.setOnTimeout(() -> SwingUtilities.invokeLater(() -> {
                app.showWin(winner.getName()); // show win screen via app
            }));
        }
    }
}
