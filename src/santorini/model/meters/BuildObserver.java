package santorini.model.meters;

import santorini.model.Player;

/**
 * Interface for observing build events in the Santorini game.
 *
 * Author: Ubaid Irfan (Sprint 3 implementation)
 */
public interface BuildObserver {
    void onBuild(Player player, ImpactLevel level);
    void onRemove(Player player, ImpactLevel level);
}
