package santorini.model.gods;

/**
 * Abstract base class representing a god card in the Santorini game.
 * <p>
 * A god card defines a unique name, description, and associated {@link SpecialPower}
 * that can modify the behavior of a player's actions during gameplay.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 */
public abstract class GodCard {
    private final String name;
    private final String description;
    private final SpecialPower specialPower;

    /**
     * Constructs a GodCard with the given name, description, and power.
     *
     * @param name         the name of the god (e.g., "Artemis", "Demeter")
     * @param description  the textual description of the god's effect
     * @param specialPower the {@link SpecialPower} granted by this card
     */
    public GodCard(String name, String description, SpecialPower specialPower) {
        this.name = name;
        this.description = description;
        this.specialPower = specialPower;
    }

    /**
     * Returns the name of the god.
     *
     * @return the god card's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the god's effect.
     *
     * @return the god card's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the special power associated with the god card.
     *
     * @return the {@link SpecialPower} for this god
     */
    public SpecialPower getSpecialPower() {
        return specialPower;
    }
}
