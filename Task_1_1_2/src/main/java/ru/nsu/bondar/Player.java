package ru.nsu.bondar;

/**
 * Represents a player in the Blackjack game.
 */
public class Player {
    private final Hand hand;

    /**
     * Constructs a new Player with an empty hand.
     */
    public Player() {
        hand = new Hand();
    }

    /**
     * Returns the hand of the player.
     *
     * @return the hand of the player
     */
    public Hand getHand() {
        return hand;
    }
}