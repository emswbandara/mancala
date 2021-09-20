package com.bol.mancala.model;

/**
 * A Game can have different strategies for playing. E.g. Human vs Human, Human vs Computer.
 */
public interface GameStrategy {

    int getPlayerNextMove();
}
