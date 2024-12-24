package ru.nsu.bondar;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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