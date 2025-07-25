package santorini.model.timing;

/**
 * This interface defines a contract for managing player timers in a game.
 *
 * Author: Ubaid Irfan (Sprint 3 implementation)
 */
public interface TimerManager {
    void pause(int index);
    void resume(int index);
}
