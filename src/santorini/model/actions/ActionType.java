package santorini.model.actions;


/**
 * Enum representing the types of actions a worker can perform in the game.
 * <p>
 * These are used to distinguish between movement and building phases.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 * Modified by: Ubaid Irfan (Sprint 3 implementation)
 */
public enum ActionType {
    /** Represents a movement action performed by a worker. */
    MOVE,

    /** Represents a building action performed by a worker. */
    BUILD,

    /** Represents a building removal action performed by a worker. */
    REMOVE_BUILD
}

