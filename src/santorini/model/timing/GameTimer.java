package santorini.model.timing;


/**
 * Defines a contract for a game timer.
 *
 * Author: Ubaid Irfan (Sprint 3 implementation)
 */
public interface GameTimer {
    void start();
    void pause();
    void resume();
    int getRemainingTimeSeconds();
    void setOnTimeout(Runnable action);
}
