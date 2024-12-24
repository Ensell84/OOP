package ru.nsu.bondar;

/**
 * Represents the dealer in the Blackjack game.
 */
public class Dealer {
    private final Hand hand;

    /**
     * Constructs a new Dealer with an empty hand.
     */
    public Dealer() {
        hand = new Hand();
    }

    /**
     * Returns the hand of the dealer.
     *
     * @return the hand of the dealer
     */
    public Hand getHand() {
        return hand;
    }
}