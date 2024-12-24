package ru.nsu.bondar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Player class.
 */
public class PlayerTest {
    @Test
    public void testPlayerCreation() {
        Player player = new Player();
        assertNotNull(player.getHand());
    }

    @Test
    public void testAddCardToPlayerHand() {
        Player player = new Player();
        Card card = new Card("Hearts", "Ace", 11);
        player.getHand().addCard(card);
        assertEquals(1, player.getHand().getCards().size());
        assertEquals(card, player.getHand().getCards().get(0));
    }
}