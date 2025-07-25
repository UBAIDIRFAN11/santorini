package santorini.model.meters;

import santorini.model.Player;
import santorini.view.NatureMeterLabel;

import java.util.Map;

/**
 * Observer for the nature meter UI in the Santorini game.
 *
 * Author: Ubaid Irfan (Sprint 3 implementation)
 */
public class NatureMeterUIObserver implements BuildObserver {
    private final Map<Player, NatureMeterLabel> ecoLabels;

    /**
     * Constructs a NatureMeterUIObserver with the specified map of player labels.
     * @param ecoLabels a map where keys are players and values are their corresponding NatureMeterLabels
     */
    public NatureMeterUIObserver(Map<Player, NatureMeterLabel> ecoLabels) {
        this.ecoLabels = ecoLabels;
    }

    /**
     * Updates the UI for the nature meter when a player builds or removes a block.
     *
     * @param player the player whose meter is being updated
     * @param level the impact level of the build or removal event
     */
    @Override
    public void onBuild(Player player, ImpactLevel level) {
        updateUI(player);
    }

    /**
     * Updates the UI for the nature meter when a player removes a block.
     *
     * @param player the player whose meter is being updated
     * @param level the impact level of the removal event
     */
    @Override
    public void onRemove(Player player, ImpactLevel level) {
        updateUI(player);
    }

    /**
     * Updates the UI for the nature meter of a specific player.
     *
     * @param player the player whose nature meter UI is being updated
     */
    private void updateUI(Player player) {
        int value = player.getMeter(MeterType.NATURE).getValue();
        NatureMeterLabel label = ecoLabels.get(player);
        if (label != null) label.updateEcoLevel(value);
    }
}

