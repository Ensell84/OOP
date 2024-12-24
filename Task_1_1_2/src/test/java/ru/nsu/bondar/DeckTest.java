package ru.nsu.bondar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Deck class.
 */
public class DeckTest {
    @Test
    public void testDeckCreation() {
        Deck deck = new Deck();
        assertEquals(52, deck.getCards().size());
    }

    @Test
    public void testDrawCard() {
        Deck deck = new Deck();
        Card card = deck.drawCard();
        assertNotNull(card);
        assertEquals(51, deck.getCards().size());
    }
}