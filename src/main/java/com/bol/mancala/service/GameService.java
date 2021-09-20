package com.bol.mancala.service;

import com.bol.mancala.exception.GameException;
import com.bol.mancala.model.Game;

import java.util.List;

public interface GameService {

    Game createGame(String player1, String player2) throws GameException;

    void updateGame(String gameId, Game game) throws GameException;

    List<Game> listGames() throws GameException;

    Game getGame(String gameId) throws GameException;

    void deleteGame(String gameId) throws GameException;

    Game restartGame(String gameId) throws GameException;

    void pauseGame(String gameId) throws GameException;

    void resumeGame(String gameId) throws GameException;
}
