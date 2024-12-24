package ru.nsu.bondar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a deck of cards in the Blackjack game.
 */
public class Deck {
    private final List<Card> cards;

    /**
     * Constructs a new Deck with 52 shuffled cards.
     */
    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Spades", "Hearts", "Clubs", "Diamonds"};
        String[] ranks = {"2", "3", "4", "5", "6",
                "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                cards.add(new Card(suit, ranks[i], values[i]));
            }
        }
        Collections.shuffle(cards);
    }

    /**
     * Draws a card from the deck.
     *
     * @return the drawn card
     */
    public Card drawCard() {
        return cards.remove(cards.size() - 1);
    }
}