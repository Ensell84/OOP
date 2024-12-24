package ru.nsu.bondar;

/**
 * Represents a card in the Blackjack game.
 */
public class Card {
    private final String suit;
    private final String rank;
    private final int value;

    /**
     * Constructs a new Card with the specified suit, rank, and value.
     *
     * @param suit  the suit of the card
     * @param rank  the rank of the card
     * @param value the value of the card
     */
    public Card(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    /**
     * Returns the suit of the card.
     *
     * @return the suit of the card
     */
    public String getSuit() {
        return suit;
    }

    /**
     * Returns the rank of the card.
     *
     * @return the rank of the card
     */
    public String getRank() {
        return rank;
    }

    /**
     * Returns the value of the card.
     *
     * @return the value of the card
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns a string representation of the card.
     *
     * @return a string representation of the card
     */
    @Override
    public String toString() {
        return rank + " " + suit + " (" + value + ")";
    }
}