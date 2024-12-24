package ru.nsu.bondar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Dealer class.
 */
public class DealerTest {
    @Test
    public void testDealerCreation() {
        Dealer dealer = new Dealer();
        assertNotNull(dealer.getHand());
    }

    @Test
    public void testAddCardToDealerHand() {
        Dealer dealer = new Dealer();
        Card card = new Card("Spades", "King", 10);
        dealer.getHand().addCard(card);
        assertEquals(1, dealer.getHand().getCards().size());
        assertEquals(card, dealer.getHand().getCards().get(0));
    }
}