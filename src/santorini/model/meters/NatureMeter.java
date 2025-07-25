package santorini.model.meters;

/**
 * Represents a nature meter in the Santorini game.
 *
 * Author: Ubaid Irfan (Sprint 3 implementation)
 */
public class NatureMeter implements MeterStrategy {
    private final int min;
    private final int max;
    private int value;

    /**
     * Constructs a NatureMeter with the specified minimum and maximum values.
     * @param min the minimum value of the meter
     * @param max the maximum value of the meter
     */
    public NatureMeter(int min, int max) {
        this.min = min;
        this.max = max;
        this.value = max;
    }

    /**
     * Increases the meter value
     */
    @Override
    public void increase() {
        adjust(1);
    }

    /**
     * Decreases the meter value
     */
    @Override
    public void decrease() {
        adjust(-1);
    }

    /**
     * Returns the current value of the meter.
     * @return the current value of the meter
     */
    @Override
    public int getValue() {
        return value;
    }

    /**
     * Adjusts the meter value by the specified delta, ensuring it stays within the defined min and max bounds.
     * @param delta the amount to adjust the meter value by
     */
    @Override
    public void adjust(int delta) {
        value = Math.min(max, Math.max(min, value + delta));
    }

    /**
     * Checks if the meter is depleted (i.e., its value is less than or equal to zero).
     * @return true if the meter is depleted, false otherwise
     */
    public boolean isDepleted() {
        return value <= 0;
    }
}
