package ru.nsu.bondar;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a hand of cards in the Blackjack game.
 */
public class Hand {
    private final List<Card> cards;

    /**
     * Constructs a new Hand with an empty list of cards.
     */
    public Hand() {
        cards = new ArrayList<>();
    }

    /**
     * Adds a card to the hand.
     *
     * @param card the card to add
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Returns the total value of the hand, taking into account the special rules for Aces.
     *
     * @return the total value of the hand
     */
    public int getTotalValue() {
        int totalValue = 0;
        int aceCount = 0;

        for (Card card : cards) {
            totalValue += card.getValue();
            if (card.getRank().equals("Ace")) {
                aceCount++;
            }
        }

        while (totalValue > 21 && aceCount > 0) {
            totalValue -= 10;
            aceCount--;
        }

        return totalValue;
    }

    /**
     * Returns the list of cards in the hand.
     *
     * @return the list of cards in the hand
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * Returns a string representation of the hand, including the total value.
     *
     * @return a string representation of the hand
     */
    @Override
    public String toString() {
        return cards.toString() + " => " + getTotalValue();
    }
}