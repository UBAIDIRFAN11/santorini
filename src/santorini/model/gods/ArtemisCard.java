package santorini.model.gods;


/**
 * Represents the Artemis god card in the Santorini game.
 * <p>
 * Artemis allows a player's worker to move an additional time, provided
 * it does not move back to its original space.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 */
public class ArtemisCard extends GodCard {

    /**
     * Constructs an Artemis god card with its name, description, and associated power.
     */
    public ArtemisCard() {
        super("Artemis",
                "Your Worker may move one additional time, but not back to its initial space.",
                new ArtemisPower());
    }
}