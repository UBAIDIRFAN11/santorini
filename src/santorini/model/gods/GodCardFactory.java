package santorini.model.gods;

import java.util.List;
import java.util.function.Supplier;

/**
 * Factory class to provide a default set of god cards for the Santorini game.
 *
 * Author: Ubaid Irfan (Sprint 3 implementation)
 */
public class GodCardFactory {

    /**
     * Returns a list of default god card suppliers.
     * <p>
     * The default cards include Artemis, Demeter, Zeus, and Gaia.
     *
     * @return a list of suppliers that create instances of {@link GodCard}
     */
    public static List<Supplier<GodCard>> getDefaultGodCards() {
        return List.of(
                ArtemisCard::new,
                DemeterCard::new,
                ZeusCard::new,
                GaiaCard::new
        );
    }
}
