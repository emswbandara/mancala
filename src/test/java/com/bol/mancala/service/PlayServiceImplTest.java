package com.bol.mancala.service;

import com.bol.mancala.exception.GameException;
import com.bol.mancala.model.Game;
import com.bol.mancala.model.Pit;
import com.bol.mancala.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PlayServiceImplTest {

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayService playService;

    @Test
    public void testSow() throws Exception {

        Game game = gameService.createGame("testPlayer1", "testPlayer2");
        Assert.notNull(game, "Game should not be null.");
        Player opponent = playService.getOppositePlayer(game);

        Assertions.assertThrows(GameException.class, () -> {
            playService.sow(game, -1);
        });

        // Should throw an exception if sow is triggered for an opponent's pit.
        Assertions.assertThrows(GameException.class, () -> {
            playService.sow(game, opponent.getSmallPits().get(0).getId());
        });

        int pitId = game.getCurrentPlayer().getSmallPits().get(0).getId();
        String currentPlayerName = game.getCurrentPlayer().getName();
        int stonesInNextPit = game.getCurrentPlayer().getSmallPits().get(1).getStones();
        Game updatedGame = playService.sow(game, pitId);
        Assert.isTrue(updatedGame.getCurrentPlayer().getSmallPits().get(1).getStones() == stonesInNextPit + 1, "Stone" +
                " count in immediate next pit should be incremented by 1.");
        Assert.isTrue(updatedGame.getCurrentPlayer().getSmallPits().get(0).getStones() == 0, "Stone count should" +
                " be 0 in the sow pit.");
        Assert.isTrue(updatedGame.getCurrentPlayer().getName().equals(currentPlayerName), "Player should not be " +
                "changed.");
    }

    @Test
    public void testIsGameOver() throws Exception {

        Game game = new Game.Builder("1234", "test1", "test2").build();
        Assert.isTrue(!playService.isGameOver(game), "Game should not be completed.");

        game.getPlayer1().getSmallPits().forEach(Pit::clearStones);

        Assert.isTrue(playService.isGameOver(game), "Game should be completed.");
    }
}
