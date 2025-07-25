package santorini.model.timing;

import java.util.List;

/**
 * This class keeps track of the player timers, and can pause/resume them by index
 *
 * Author: Ubaid Irfan (Sprint 3 implementation)
 */
public class GameTimerManager implements TimerManager {
    private final List<GameTimer> timers;

    /**
     * Constructs a GameTimerManager with a list of timers.
     *
     * @param timers the list of GameTimers to manage
     */
    public GameTimerManager(List<GameTimer> timers) {
        this.timers = timers;
    }


    /**
     * Pauses timer for the player at specified index
     *
     * @param index the index of the timer to pause
     */
    @Override
    public void pause(int index) {
        timers.get(index).pause();
    }

    /**
     * Resumes timer for the player at specified index
     *
     * @param index the index of the timer to resume
     */
    @Override
    public void resume(int index) {
        timers.get(index).resume();
    }
}
