package com.bol.mancala.service;

import com.bol.mancala.GameConstants;
import com.bol.mancala.exception.GameException;
import com.bol.mancala.model.Game;
import com.bol.mancala.model.Pit;
import com.bol.mancala.model.Player;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayServiceImpl implements PlayService {

    @Override
    public Game sow(Game game, int pitId) throws GameException {

        if (game.getStatus() != Game.Status.RUNNING) {
            throw new GameException(GameConstants.ERROR_CODE_INVALID_MOVE, "Game is not in running state.");
        }

        if (pitId <= 0 || pitId > 13 || pitId == 7) {
            throw new GameException(GameConstants.ERROR_CODE_INVALID_PIT_ID, "Provided Pit ID is invalid.");
        }
        Player currentPlayer = game.getCurrentPlayer();

        Optional<Pit> pitOptional = currentPlayer.getSmallPitById(pitId);
        if (!pitOptional.isPresent()) {
            if (getOppositePlayer(game).containsPit(pitId)) {
                throw new GameException(GameConstants.ERROR_CODE_INVALID_PIT_ID, "Cannot select other player pits.");
            }
            throw new GameException(GameConstants.ERROR_CODE_INVALID_PIT_ID, "Provided Pit ID is invalid.");
        }
        Pit pit = pitOptional.get();
        if (pit.getStones() == 0) {
            throw new GameException(GameConstants.ERROR_CODE_INVALID_MOVE, "Invalid move. Stones are empty in the pit.");
        }

        int stones = pit.getStones();
        pit.clearStones();

        // Sow stones in anti-clockwise direction.
        while (stones > 0) {
            pit = pit.getNext();
            // Stones should be added only to small pits and current player's big pit.
            if (pit.getType() == Pit.Type.SMALL || pit.getId() == currentPlayer.getBigPit().getId()) {
                pit.addStone();
                stones--;
            }
        }

        // If the landing pit was empty before sowing a stone it, and if it is owned by the current player, he will
        // collect his own stones and opposite pit stones.
        if (pit.getType() == Pit.Type.SMALL && currentPlayer.containsPit(pit.getId()) && pit.getStones() == 1) {
            currentPlayer.getBigPit().addStones(pit.getStones() + pit.getOpposite().getStones());
            pit.clearStones();
            pit.getOpposite().clearStones();
        }

        if (!isGameOver(game)) {
            determineNextTurn(game, pit);
        }
        return game;
    }

    // Clear pits only when game is over and update game status.
    @Override
    public Game applyWinningRules(Game game) throws GameException {

        if (game.getStatus() != Game.Status.COMPLETED) {
            throw new GameException(GameConstants.ERROR_CODE_INVALID_MOVE, "Game is not completed yet.");
        }

        int player1StoneCount = game.getPlayer1().getSmallPits().stream().mapToInt(Pit::getStones).sum();
        game.getPlayer1().getBigPit().addStones(player1StoneCount);
        game.getPlayer1().getSmallPits().forEach(Pit::clearStones);

        int player2StoneCount = game.getPlayer2().getSmallPits().stream().mapToInt(Pit::getStones).sum();
        game.getPlayer2().getBigPit().addStones(player2StoneCount);
        game.getPlayer2().getSmallPits().forEach(Pit::clearStones);

        int player1Mancala = game.getPlayer1().getBigPit().getStones();
        int player2Mancala = game.getPlayer2().getBigPit().getStones();
        if (player1Mancala == player2Mancala) {
            game.setStatus(Game.Status.DRAW);
        } else if (player1Mancala > player2Mancala) {
            game.setStatus(Game.Status.PLAYER1_WIN);
        } else {
            game.setStatus(Game.Status.PLAYER2_WIN);
        }
        return game;
    }

    @Override
    public boolean isGameOver(Game game) {

        if (game.getPlayer1().getSmallPits().stream().allMatch(pit -> pit.getStones() == 0) ||
                game.getPlayer2().getSmallPits().stream().allMatch(pit -> pit.getStones() == 0)) {
            // Game over.
            game.setStatus(Game.Status.COMPLETED);
            return true;
        }
        return false;
    }

    @Override
    public void determineNextTurn(Game game, Pit pit) {

        if (pit.getType() == Pit.Type.SMALL || !(game.getCurrentPlayer().getBigPit().getId() == pit.getId())) {
            game.setCurrentPlayer(getOppositePlayer(game));
        }
        // Else current player remains the same.
    }

    @Override
    public Player getOppositePlayer(Game game) {

        if (game.getPlayer1().getName().equals(game.getCurrentPlayer().getName())) {
            return game.getPlayer2();
        } else {
            return game.getPlayer1();
        }
    }

    @Override
    public Player checkWinner(Game game) {

        if (game.getStatus() == Game.Status.PLAYER1_WIN) {
            return game.getPlayer1();
        } else if (game.getStatus() == Game.Status.PLAYER2_WIN) {
            return game.getPlayer2();
        } else {
            return null;
        }
    }
}
