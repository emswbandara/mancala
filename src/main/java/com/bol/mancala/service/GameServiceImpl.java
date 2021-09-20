package com.bol.mancala.service;

import com.bol.mancala.GameConstants;
import com.bol.mancala.exception.GameException;
import com.bol.mancala.model.Game;
import com.bol.mancala.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public Game createGame(String player1, String player2) throws GameException {

        if (player1 == null || player1.isEmpty()) {
            throw new GameException(GameConstants.ERROR_CODE_INVALID_PLAYER_NAME, "Player 1 Name cannot be empty.");
        }

        if (player2 == null || player2.isEmpty()) {
            throw new GameException(GameConstants.ERROR_CODE_INVALID_PLAYER_NAME, "Player 2 Name cannot be empty.");
        }

        if (player1.equals(player2)) {
            throw new GameException(GameConstants.ERROR_CODE_PLAYER_NAME_CONFLICT, "Player 1 and Player 2 Names " +
                    "cannot be the same.");
        }
        String gameId = UUID.randomUUID().toString();
        Game game = new Game.Builder(gameId, player1, player2).build();
        gameRepository.save(game);

        return game;
    }

    @Override
    public void updateGame(String gameId, Game game) throws GameException {

        validateGameId(gameId);
        gameRepository.updateById(gameId, game);
    }

    @Override
    public List<Game> listGames() throws GameException {

        return gameRepository.findAll();
    }

    @Override
    public Game getGame(String gameId) throws GameException {

        validateGameId(gameId);
        return gameRepository.findById(gameId);
    }

    @Override
    public Game restartGame(String gameId) throws GameException {

        validateGameId(gameId);
        Game game = gameRepository.findById(gameId);
        Game newGame = new Game.Builder(gameId, game.getPlayer1().getName(), game.getPlayer2().getName()).build();
        gameRepository.updateById(gameId, newGame);
        return newGame;
    }

    @Override
    public void pauseGame(String gameId) throws GameException {

        validateGameId(gameId);
        Game game = gameRepository.findById(gameId);
        game.setStatus(Game.Status.PAUSED);
        gameRepository.updateById(gameId, game);
    }

    @Override
    public void resumeGame(String gameId) throws GameException {

        validateGameId(gameId);
        Game game = gameRepository.findById(gameId);
        game.setStatus(Game.Status.RUNNING);
        gameRepository.updateById(gameId, game);
    }

    @Override
    public void deleteGame(String gameId) throws GameException {

        validateGameId(gameId);
        gameRepository.deleteById(gameId);
    }

    private void validateGameId(String gameId) throws GameException {

        if (!gameRepository.existsById(gameId)) {
            throw new GameException(GameConstants.ERROR_CODE_GAME_NOT_FOUND,
                    "Could not find a game with ID: " + gameId);
        }
    }
}
