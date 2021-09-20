package com.bol.mancala.repository;

import com.bol.mancala.model.Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class InMemoryGameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    void testSave() throws Exception {

        Game game = new Game.Builder("testId", "testPlayer1Name", "testPlayer2Name").build();
        gameRepository.save(game);
        Game savedGame = gameRepository.findById("testId");
        Assert.notNull(savedGame, "Game should not be null.");

        // Assert that modification to the retrieved object does not change the saved object.
        savedGame.setStatus(Game.Status.PAUSED);
        Game originalGame = gameRepository.findById("testId");
        Assert.isTrue(originalGame.getStatus() != savedGame.getStatus(), "Original object should not get modified.");
    }

    @Test
    void testFindAll() throws Exception {

        Game game = new Game.Builder("testId2", "testPlayer1Name", "testPlayer2Name").build();
        gameRepository.save(game);
        List<Game> games = gameRepository.findAll();
        Assert.notEmpty(games, "Game list should not be empty.");
    }

    @Test
    void testExistsById() throws Exception {

        Game game = new Game.Builder("testId2", "testPlayer1Name", "testPlayer2Name").build();
        gameRepository.save(game);
        boolean status = gameRepository.existsById("testId2");
        Assert.isTrue(status, "Saved game should be exist.");
    }

    @Test
    void testUpdateById() throws Exception {

        Game game = new Game.Builder("testId2", "testPlayer1Name", "testPlayer2Name").build();
        gameRepository.save(game);
        Game savedGame = gameRepository.findById("testId2");
        savedGame.setStatus(Game.Status.PAUSED);
        gameRepository.updateById("testId2", savedGame);
        Assert.isTrue(gameRepository.findById("testId2").getStatus() == Game.Status.PAUSED, "Updated game should have" +
                " PAUSED status.");
    }

    @Test
    void testDeleteById() throws Exception {

        Game game = new Game.Builder("testId2", "testPlayer1Name", "testPlayer2Name").build();
        gameRepository.save(game);
        gameRepository.deleteById("testId2");
        Assert.state(!gameRepository.existsById("testId2"), "Game should be deleted.");
    }
}
