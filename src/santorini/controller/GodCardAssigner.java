package santorini.controller;

import santorini.model.gods.GodCard;
import santorini.model.Player;
import santorini.model.gods.GodCardProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Handles assigning god cards randomly to players from a provided god card pool.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 * Modified by: Ubaid Irfan (Sprint 3 implementation)
 */
public class GodCardAssigner {
    private final GodCardProvider provider;

    /**
     * Constructs a GodCardAssigner with the specified GodCardProvider.
     *
     * @param provider the provider to get available god cards from
     */
    public GodCardAssigner(GodCardProvider provider) {
        this.provider = provider;
    }

    /**
     * Assigns god cards to a list of players.
     * <p>
     * The method shuffles the available god cards and assigns one to each player.
     *
     * @param players the list of players to assign god cards to
     */
    public void assignTo(List<Player> players) {
        List<GodCard> cards = new ArrayList<>(provider.getAvailableCards());
        Collections.shuffle(cards);
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setGodCard(cards.get(i));
        }
    }
}
