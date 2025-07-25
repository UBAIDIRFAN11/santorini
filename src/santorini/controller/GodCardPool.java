package santorini.controller;

import santorini.model.gods.*;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Provides a predefined pool of available god cards for use in the game.
 * <p>
 * This class can be expanded to support custom sets, random selections, or player choices.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 * Modified by: Ubaid Irfan (Sprint 3 implementation)
 */
public class GodCardPool implements GodCardProvider {
    private final List<Supplier<GodCard>> godCardSuppliers;

    /**
     * Constructs a GodCardPool with a list of suppliers for god cards.
     * <p>
     * Each supplier is responsible for creating a specific god card instance.
     *
     * @param godCardSuppliers a list of suppliers that provide god card instances
     */
    public GodCardPool(List<Supplier<GodCard>> godCardSuppliers) {
        this.godCardSuppliers = godCardSuppliers;
    }

    /**
     * Returns a list of all available god cards in the game.
     * <p>
     * Currently includes: Artemis and Demeter.
     *
     * @return a list of {@link GodCard} objects
     */
    @Override
    public List<GodCard> getAvailableCards() {
        return godCardSuppliers.stream()
                .map(Supplier::get)
                .collect(Collectors.toList());
    }
}
