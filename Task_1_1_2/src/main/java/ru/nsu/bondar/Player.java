package ru.nsu.bondar;

public class Player {
    private final Hand hand;

    public Player() {
        hand = new Hand();
    }

    public Hand getHand() {
        return hand;
    }
}