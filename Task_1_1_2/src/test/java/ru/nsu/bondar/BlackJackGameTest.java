package ru.nsu.bondar;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the BlackJackGame class.
 */
public class BlackJackGameTest {
    private Deck deck;
    private Player player;
    private Dealer dealer;
    private BlackJackGame game;

    @BeforeEach
    void setUp() {
        deck = new Deck();
        player = new Player();
        dealer = new Dealer();
        game = new BlackJackGame(deck, player, dealer);
    }

    @Test
    void testPlayerBlackjack() {
        player.getHand().addCard(new Card("Hearts", "Ace", 11));
        player.getHand().addCard(new Card("Spades", "King", 10));
        dealer.getHand().addCard(new Card("Diamonds", "Seven", 7));
        dealer.getHand().addCard(new Card("Clubs", "Eight", 8));

        String input = "0\nno\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        game.setScanner(new Scanner(System.in));

        game.playRound();

        assertEquals(0, player.getHand().getCards().size());
        assertEquals(0, dealer.getHand().getCards().size());
    }

    @Test
    void testPlayerBust() {
        player.getHand().addCard(new Card("Hearts", "King", 10));
        player.getHand().addCard(new Card("Spades", "Queen", 10));
        dealer.getHand().addCard(new Card("Diamonds", "Seven", 7));
        dealer.getHand().addCard(new Card("Clubs", "Eight", 8));

        String input = "1\nno\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        game.setScanner(new Scanner(System.in));

        game.playRound();

        assertEquals(0, player.getHand().getCards().size());
        assertEquals(0, dealer.getHand().getCards().size());
    }

    @Test
    void testPlayerStandDealerWins() {
        player.getHand().addCard(new Card("Hearts", "Ten", 10));
        player.getHand().addCard(new Card("Spades", "Seven", 7));
        dealer.getHand().addCard(new Card("Diamonds", "King", 10));
        dealer.getHand().addCard(new Card("Clubs", "Eight", 8));

        String input = "0\nno\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        game.setScanner(new Scanner(System.in));

        game.playRound();

        assertEquals(0, player.getHand().getCards().size());
        assertEquals(0, dealer.getHand().getCards().size());
    }

    @Test
    void testDealerBust() {
        player.getHand().addCard(new Card("Hearts", "Ten", 10));
        player.getHand().addCard(new Card("Spades", "Eight", 8));
        dealer.getHand().addCard(new Card("Diamonds", "Six", 6));
        dealer.getHand().addCard(new Card("Clubs", "Ten", 10));

        deck.getCards().add(new Card("Hearts", "King", 10));


        String input = "0\nno\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        game.setScanner(new Scanner(System.in));

        game.playRound();

        assertEquals(0, player.getHand().getCards().size());
        assertEquals(0, dealer.getHand().getCards().size());
    }

    @Test
    void testMultipleRounds() {
        String input = "0\nyes\n0\nno\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        game.setScanner(new Scanner(System.in));

        game.play();

        assertEquals(0, player.getHand().getCards().size());
        assertEquals(0, dealer.getHand().getCards().size());
    }
}