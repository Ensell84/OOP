package ru.nsu.bondar;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the Card class.
 */
public class CardTest {
    @Test
    public void testCardCreation() {
        Card card = new Card("Hearts", "Ace", 11);
        assertEquals("Hearts", card.getSuit());
        assertEquals("Ace", card.getRank());
        assertEquals(11, card.getValue());
    }

    @Test
    public void testCardToString() {
        Card card = new Card("Hearts", "Ace", 11);
        assertEquals("Ace Hearts (11)", card.toString());
    }
}