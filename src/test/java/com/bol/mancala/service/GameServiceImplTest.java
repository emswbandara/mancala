package com.bol.mancala.service;

import com.bol.mancala.exception.GameException;
import com.bol.mancala.model.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GameServiceImplTest {

    @Autowired
    private GameService gameService;

    @Test
    public void testCreateGame() throws Exception {

        Game game = gameService.createGame("testPlayer1", "testPlayer2");
        Assert.notNull(game, "Game should not be null.");
    }

    @Test
    void testCreateGameException() {

        Assertions.assertThrows(GameException.class, () -> {
            gameService.createGame(null, "testPlayer2");
        });

        Assertions.assertThrows(GameException.class, () -> {
            gameService.createGame("testPlayer1", null);
        });

        Assertions.assertThrows(GameException.class, () -> {
            gameService.createGame("testPlayer1", "testPlayer1");
        });
    }

    @Test
    public void testGetGame() throws Exception {

        Game game = gameService.createGame("testPlayer1", "testPlayer2");
        Game savedGame = gameService.getGame(game.getId());
        Assert.notNull(savedGame, "Game should not be null.");
    }

    @Test
    public void testGetGameException() throws Exception {

        Assertions.assertThrows(GameException.class, () -> {
            gameService.getGame("invalidId");
        });
    }

    @Test
    public void testListGames() throws Exception {

        Game game = gameService.createGame("testPlayer1", "testPlayer2");
        List<Game> savedGames = gameService.listGames();
        Assert.notEmpty(savedGames, "Game list should not be empty.");
    }

    @Test
    public void testGameOperations() throws Exception {

        Game game = gameService.createGame("testPlayer1", "testPlayer2");
        String gameId = game.getId();
        gameService.pauseGame(gameId);
        Assert.isTrue(gameService.getGame(gameId).getStatus() == Game.Status.PAUSED, "Game status should be updated " +
                "to pause.");

        gameService.resumeGame(gameId);
        Assert.isTrue(gameService.getGame(gameId).getStatus() == Game.Status.RUNNING, "Game status should be updated " +
                "to running.");
    }

    @Test
    public void testDeleteGame() throws Exception {

        Game game = gameService.createGame("testPlayer1", "testPlayer2");
        gameService.deleteGame(game.getId());
        Assertions.assertThrows(GameException.class, () -> {
            gameService.getGame(game.getId());
        });
    }
}
