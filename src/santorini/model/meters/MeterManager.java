package santorini.model.meters;

import santorini.model.Player;

/**
 * Manages the meter adjustments for a specific type of meter in the Santorini game.
 *
 * Author: Ubaid Irfan (Sprint 3 implementation)
 */
public class MeterManager<T extends MeterType> implements BuildObserver {
    private final T meterType;

    /**
     * Constructs a MeterManager for the specified meter type.
     * @param meterType the type of meter to manage - currently only NATURE is supported
     */
    public MeterManager(T meterType) {
        this.meterType = meterType;
    }

    /**
     * Handles the build event by adjusting the player's meter based on the impact level.
     * @param player the player whose meter is being adjusted
     * @param level the impact level of the build event
     */
    @Override
    public void onBuild(Player player, ImpactLevel level) {
        player.getMeter(meterType).adjust(-level.getImpact());
    }

    /**
     * Handles the removal event by adjusting the player's meter based on the impact level.
     * @param player the player whose meter is being adjusted
     * @param level the impact level of the removal event
     */
    @Override
    public void onRemove(Player player, ImpactLevel level) {
        player.getMeter(meterType).adjust(level.getImpact());
    }
}


