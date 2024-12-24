package ru.nsu.bondar;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the Hand class.
 */
public class HandTest {
    @Test
    public void testAddCard() {
        Hand hand = new Hand();
        Card card = new Card("Hearts", "Ace", 11);
        hand.addCard(card);
        assertEquals(1, hand.getCards().size());
        assertEquals(card, hand.getCards().get(0));
    }

    @Test
    public void testGetTotalValue() {
        Hand hand = new Hand();
        hand.addCard(new Card("Hearts", "Ace", 11));
        hand.addCard(new Card("Spades", "King", 10));
        assertEquals(21, hand.getTotalValue());
    }

    @Test
    public void testGetTotalValueWithAces() {
        Hand hand = new Hand();
        hand.addCard(new Card("Hearts", "Ace", 11));
        hand.addCard(new Card("Spades", "Ace", 11));
        hand.addCard(new Card("Clubs", "Nine", 9));
        assertEquals(21, hand.getTotalValue());
    }

    @Test
    public void testGetCards() {
        Hand hand = new Hand();
        Card card1 = new Card("Hearts", "Ace", 11);
        Card card2 = new Card("Spades", "King", 10);
        hand.addCard(card1);
        hand.addCard(card2);
        assertEquals(2, hand.getCards().size());
        assertTrue(hand.getCards().contains(card1));
        assertTrue(hand.getCards().contains(card2));
    }

    @Test
    public void testToString() {
        Hand hand = new Hand();
        hand.addCard(new Card("Hearts", "Ace", 11));
        hand.addCard(new Card("Spades", "King", 10));
        assertEquals("[Ace Hearts (11), King Spades (10)] => 21", hand.toString());
    }
}