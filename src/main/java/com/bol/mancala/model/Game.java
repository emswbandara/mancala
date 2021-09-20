package com.bol.mancala.model;

import com.bol.mancala.GameConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game implements Cloneable {

    public enum Status {
        RUNNING, PAUSED, COMPLETED, DRAW, PLAYER1_WIN, PLAYER2_WIN
    }

    private final String id;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private Status status;
    private GameStrategy gameStrategy;

    private Game(String id, Player player1, Player player2, Player currentPlayer, Status status,
             GameStrategy gameStrategy) {

        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = currentPlayer;
        this.status = status;
        this.gameStrategy = gameStrategy;
    }

    public String getId() {

        return id;
    }

    public Player getPlayer1() {

        return player1;
    }

    public Player getPlayer2() {

        return player2;
    }

    public Player getCurrentPlayer() {

        return currentPlayer;
    }

    public void setCurrentPlayer(Player player) {

        this.currentPlayer = player;
    }

    public Status getStatus() {

        return status;
    }

    public void setStatus(Status status) {

        this.status = status;
    }

    public GameStrategy getGameStrategy() {

        return gameStrategy;
    }

    public void setGameStrategy(GameStrategy gameStrategy) {

        this.gameStrategy = gameStrategy;
    }

    public Game clone() throws CloneNotSupportedException {

        return (Game) super.clone();
    }

    public static class Builder {

        private final String id;
        private final Player player1;
        private final Player player2;
        private Player currentPlayer;
        private Status status;
        private GameStrategy gameStrategy;

        public Builder(String id, String player1Name, String player2Name) {

            this(id, player1Name, player2Name, GameConstants.DEFAULT_BOARD_SIZE, GameConstants.SMALL_PIT_STONE_SIZE);
        }

        public Builder(String id, String player1Name, String player2Name, int boardSize, int pitSize) {

            this.id = id;
            this.status = Status.RUNNING;
            this.gameStrategy = new HumanPlayerGameStrategy();

            // Create big pits for players.
            Pit player1BigPit = new Pit(boardSize + 1, GameConstants.BIG_PIT_STONE_SIZE, Pit.Type.BIG);
            Pit player2BigPit = new Pit((boardSize + 1) * 2, GameConstants.BIG_PIT_STONE_SIZE, Pit.Type.BIG);

            // Create small pits.
            List<Pit> player1Pits = new ArrayList<>();
            List<Pit> player2Pits = new ArrayList<>();
            int i = 0;
            while (i < boardSize) {
                player1Pits.add(new Pit(i + 1, pitSize, Pit.Type.SMALL));
                player2Pits.add(new Pit(i + boardSize + 2, pitSize, Pit.Type.SMALL));
                i++;
            }

            // Build associations among pits.
            buildSmallPitAssociations(player1Pits, player2Pits);
            buildSmallPitAssociations(player2Pits, player1Pits);
            buildBigPitAssociations(player1Pits, player2Pits, player1BigPit);
            buildBigPitAssociations(player2Pits, player1Pits, player2BigPit);

            this.player1 = new Player(player1Name, player1Pits, player1BigPit);
            this.player2 = new Player(player2Name, player2Pits, player2BigPit);
            this.currentPlayer = generateRandomStartingPlayer(player1, player2);
        }

        private static Player generateRandomStartingPlayer(Player player1, Player player2) {

            int startingPlayer = new Random().nextInt(2);
            if (startingPlayer == 0) {
                return player1;
            } else {
                return player2;
            }
        }

        private static void buildSmallPitAssociations(List<Pit> playerList, List<Pit> opponentList) {

            Pit lastPit = null;
            for (int j = 0; j < playerList.size(); j++) {
                Pit currentPit = playerList.get(j);
                if (lastPit != null) {
                    lastPit.setNext(currentPit);
                }
                if (currentPit.getType() == Pit.Type.SMALL) {
                    int lastIndex = opponentList.size() - 1;
                    currentPit.setOpposite(opponentList.get(lastIndex - j));
                }
                lastPit = currentPit;
            }
        }

        private static void buildBigPitAssociations(List<Pit> playerList, List<Pit> opponentList, Pit playerBigPit) {

            playerList.get(playerList.size() - 1).setNext(playerBigPit);
            playerBigPit.setNext(opponentList.get(0));
        }

        public Builder currentPlayer(Player player) {

            this.currentPlayer = player;
            return this;
        }

        public Builder status(Status status) {

            this.status = status;
            return this;
        }

        public Builder gameStrategy(GameStrategy strategy) {

            this.gameStrategy = strategy;
            return this;
        }

        public Game build() {

            return new Game(id, player1, player2, currentPlayer, status, gameStrategy);
        }
    }
}
