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
    private Scanner scanner;

    /**
     * Constructs a new BlackJackGame with a new deck, player, and dealer.
     */
    public BlackJackGame() {
        deck = new Deck();
        player = new Player();
        dealer = new Dealer();
        playerScore = 0;
        dealerScore = 0;
        scanner = new Scanner(System.in);
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
        this.scanner = new Scanner(System.in);
    }

    /**
     * Sets the scanner for user input.
     *
     * @param scanner the scanner to set
     */
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Returns the deck.
     *
     * @return the deck
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Returns the player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the dealer.
     *
     * @return the dealer
     */
    public Dealer getDealer() {
        return dealer;
    }

    /**
     * Plays a single round of the game.
     */
    public void playRound() {
        System.out.println("Round started");
        player.getHand().addCard(deck.drawCard());
        player.getHand().addCard(deck.drawCard());
        dealer.getHand().addCard(deck.drawCard());
        dealer.getHand().addCard(deck.drawCard());

        System.out.println("Dealer dealt the cards");
        System.out.println("Your cards: " +
                player.getHand() + " => " + player.getHand().getTotalValue());
        System.out.println("Dealer's cards: [" +
                dealer.getHand().getCards().get(0) + ", <hidden card>]");

        if (player.getHand().getTotalValue() == 21) {
            playerScore++;
            System.out.println("You won the round! Score " +
                    playerScore + ":" + dealerScore + " in your favor.");
            resetHands();
            return;
        }

        while (true) {
            System.out.println("Enter '1' to draw a card, and '0' to stop ...");
            int choice = scanner.nextInt();
            if (choice == 1) {
                Card drawnCard = deck.drawCard();
                player.getHand().addCard(drawnCard);
                System.out.println("You drew " + drawnCard);
                System.out.println("Your cards: " +
                        player.getHand() + " => " + player.getHand().getTotalValue());
                if (player.getHand().getTotalValue() > 21) {
                    dealerScore++;
                    System.out.println("You lost the round! Score " +
                            playerScore + ":" + dealerScore + " in favor of the dealer.");
                    resetHands();
                    return;
                }
            } else {
                break;
            }
        }

        System.out.println("Dealer's turn");
        System.out.println("Dealer reveals the hidden card " + dealer.getHand().getCards().get(1));
        while (dealer.getHand().getTotalValue() < 17) {
            Card drawnCard = deck.drawCard();
            dealer.getHand().addCard(drawnCard);
            System.out.println("Dealer drew " + drawnCard);
        }

        System.out.println("Your cards: " +
                player.getHand() + " => " + player.getHand().getTotalValue());
        System.out.println("Dealer's cards: " +
                dealer.getHand() + " => " + dealer.getHand().getTotalValue());

        if (dealer.getHand().getTotalValue() > 21 ||
                player.getHand().getTotalValue() > dealer.getHand().getTotalValue()) {
            playerScore++;
            System.out.println("You won the round! Score " +
                    playerScore + ":" + dealerScore + " in your favor.");
        } else if (player.getHand().getTotalValue() < dealer.getHand().getTotalValue()) {
            dealerScore++;
            System.out.println("You lost the round! Score " +
                    playerScore + ":" + dealerScore + " in favor of the dealer.");
        } else {
            System.out.println("It's a tie! Score " + playerScore + ":" + dealerScore + ".");
        }

        resetHands();
    }

    /**
     * Starts the Blackjack game and continues to play rounds until the user decides to stop.
     * It welcomes the player, starts new rounds, and asks the player
     * if they want to play another round.
     * The game ends when the player responds with anything other than "yes".
     */
    public void play() {
        System.out.println("Welcome to Blackjack!");
        while (true) {
            System.out.println("Starting a new round...");
            playRound();
            System.out.println("Do you want to play another round? (yes/no)");
            String response = scanner.next();
            if (!response.equalsIgnoreCase("yes")) {
                break;
            }
        }
        System.out.println("Thanks for playing!");
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