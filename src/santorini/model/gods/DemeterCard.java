package santorini.model.gods;


/**
 * Represents the Demeter god card in the Santorini game.
 * <p>
 * Demeter allows a player's worker to build one additional time, but not on the same space.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 */
public class DemeterCard extends GodCard {

    /**
     * Constructs a Demeter card with its name, description, and associated power.
     */
    public DemeterCard() {
        super("Demeter",
                "Your Worker may build one additional time, but not on the same space.",
                new DemeterPower());
    }
}