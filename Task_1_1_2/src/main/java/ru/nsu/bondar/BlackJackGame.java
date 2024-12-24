package ru.nsu.bondar;

import java.util.Scanner;

/**
 * Manages the Blackjack game logic.
 */
public class BlackJackGame {
    private final Deck deck;
    private final Player player;
    private final Dealer dealer;
    private int playerScore;
    private int dealerScore;

    /**
     * Constructs a new BlackJackGame with a new deck, player, and dealer.
     */
    public BlackJackGame() {
        deck = new Deck();
        player = new Player();
        dealer = new Dealer();
        playerScore = 0;
        dealerScore = 0;
    }

    /**
     * Constructs a new BlackJackGame with the specified deck, player, and dealer.
     *
     * @param deck   the deck of cards
     * @param player the player
     * @param dealer the dealer
     */
    public BlackJackGame(Deck deck, Player player, Dealer dealer) {
        this.deck = deck;
        this.player = player;
        this.dealer = dealer;
    }

    /**
     * Starts and manages the game loop.
     */
    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Blackjack!");

        while (true) {
            System.out.println("\nRound " + (playerScore + dealerScore + 1));
            player.getHand().addCard(deck.drawCard());
            player.getHand().addCard(deck.drawCard());
            dealer.getHand().addCard(deck.drawCard());
            dealer.getHand().addCard(deck.drawCard());

            System.out.println("\nDealer dealt the cards");
            System.out.println("Your cards: " + player.getHand());
            System.out.println("Dealer's cards: [" + dealer.getHand().getCards().get(0) + ", <hidden card>]");

            if (player.getHand().getTotalValue() == 21) {
                System.out.println("\nBlackjack! You won the round!");
                playerScore++;
                resetHands();
                continue;
            }

            while (true) {
                System.out.println("\nYour turn");
                System.out.print("Enter “1” to draw a card, and “0” to stop: ");
                int choice = scanner.nextInt();
                if (choice == 1) {
                    player.getHand().addCard(deck.drawCard());
                    System.out.println("Your cards: " + player.getHand());
                    if (player.getHand().getTotalValue() > 21) {
                        System.out.println("\nYou lost! Total value exceeds 21.");
                        dealerScore++;
                        resetHands();
                        break;
                    }
                } else {
                    break;
                }
            }

            if (player.getHand().getTotalValue() <= 21) {
                System.out.println("\nDealer's turn");
                while (dealer.getHand().getTotalValue() < 17) {
                    dealer.getHand().addCard(deck.drawCard());
                    System.out.println("Dealer draws " + dealer.getHand().getCards().get(dealer.getHand().getCards().size() - 1));
                }

                System.out.println("\nYour cards: " + player.getHand());
                System.out.println("Dealer's cards: " + dealer.getHand());

                if (dealer.getHand().getTotalValue() > 21 || player.getHand().getTotalValue() > dealer.getHand().getTotalValue()) {
                    System.out.println("\nYou won the round!");
                    playerScore++;
                } else if (player.getHand().getTotalValue() < dealer.getHand().getTotalValue()) {
                    System.out.println("\nDealer won the round!");
                    dealerScore++;
                } else {
                    System.out.println("\nIt's a tie!");
                }
            }

            System.out.println("\nScore " + playerScore + ":" + dealerScore + " in your favor.");
            resetHands();
        }
    }

    /**
     * Resets the hands of the player and dealer.
     */
    private void resetHands() {
        player.getHand().getCards().clear();
        dealer.getHand().getCards().clear();
    }

    /**
     * The main method to start the Blackjack game.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        BlackJackGame game = new BlackJackGame();
        game.play();
    }
}