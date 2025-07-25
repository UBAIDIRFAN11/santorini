package santorini.model.meters;

/**
 * Represents the impact levels of different blocks on the nature meter in the Santorini game.
 * block=building of any level, or dome
 *
 * Author: Ubaid Irfan (Sprint 3 implementation)
 */
public enum ImpactLevel {
    LEVEL_1(5),
    LEVEL_2(10),
    LEVEL_3(15),
    DOME(20);

    private final int ecoImpact;

    /**
     * Constructs an ImpactLevel with the specified ecological impact value.
     *
     * @param ecoImpact the ecological impact value of the block level
     */
    ImpactLevel(int ecoImpact) {
        this.ecoImpact = ecoImpact;
    }

    /**
     * Returns the impact value of this ImpactLevel.
     *
     * @return the ecological impact value
     */
    public int getImpact() {
        return ecoImpact;
    }

    /**
     * Returns the ImpactLevel corresponding to the specified height.
     *
     * @param height the height of the block (1 for LEVEL_1, 2 for LEVEL_2, etc.)
     * @return the ImpactLevel corresponding to the height
     * @throws IllegalArgumentException if the height is not valid (not between 1 and 4)
     */
    public static ImpactLevel fromHeight(int height) {
        return switch (height) {
            case 1 -> LEVEL_1;
            case 2 -> LEVEL_2;
            case 3 -> LEVEL_3;
            case 4 -> DOME;
            default -> throw new IllegalArgumentException("Invalid height: " + height);
        };
    }
}
