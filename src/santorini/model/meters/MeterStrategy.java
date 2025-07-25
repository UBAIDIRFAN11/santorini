package santorini.model.meters;

/**
 * Interface for managing meter strategies in the Santorini game.
 *
 * Author: Ubaid Irfan (Sprint 3 implementation)
 */
public interface MeterStrategy {
    int getValue();
    boolean isDepleted();
    void adjust(int delta);
    void increase();
    void decrease();

}
