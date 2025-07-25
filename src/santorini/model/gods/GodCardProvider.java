package santorini.model.gods;

import java.util.List;

/**
 * Interface for providing a list of available god cards in the Santorini game.
 *
 * Author: Ubaid Irfan (Sprint 3 implementation)
 */
public interface GodCardProvider {
    List<GodCard> getAvailableCards();
}
