package com.bol.mancala.service;

import com.bol.mancala.exception.GameException;
import com.bol.mancala.model.Game;
import com.bol.mancala.model.Pit;
import com.bol.mancala.model.Player;

public interface PlayService {

    Game sow(Game game, int pitId) throws GameException;

    boolean isGameOver(Game game);

    Player getOppositePlayer(Game game);

    Game applyWinningRules(Game game) throws GameException;

    void determineNextTurn(Game game, Pit pit);

    Player checkWinner(Game game);
}

