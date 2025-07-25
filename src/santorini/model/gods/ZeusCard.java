package santorini.model.gods;

/**
 * Represents the Zeus god card in the Santorini game.
 *
 * Author: Ubaid Irfan (Sprint 3 implementation)
 */
public class ZeusCard extends GodCard {

    /**
     * Constructs a Zeus card with its name, description, and associated power.
     */
    public ZeusCard() {
        super("Zeus", "Your Worker may build a block under itself.", new ZeusPower());
    }
}
