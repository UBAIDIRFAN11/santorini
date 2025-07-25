package santorini.model.gods;

/**
 * Represents the Gaia god card in the Santorini game.
 *
 *  Author: Ubaid Irfan (Sprint 3 implementation)
 */
public class GaiaCard extends GodCard {

    /**
     * Constructs a Gaia card with its name, description, and associated power.
     */
    public GaiaCard() {
        super("Gaia", "Your worker may remove an existing block.", new GaiaPower());
    }
}
