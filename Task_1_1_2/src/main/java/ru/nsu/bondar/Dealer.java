package ru.nsu.bondar;

public class Dealer {
    private final Hand hand;

    public Dealer() {
        hand = new Hand();
    }

    public Hand getHand() {
        return hand;
    }
}