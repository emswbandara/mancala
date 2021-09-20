package com.bol.mancala.dto;

public class GameResponseDTO {

    private String gameId;
    private String currentPlayer;
    private String status;
    private String winner;
    private PlayerDTO player1;
    private PlayerDTO player2;

    public String getGameId() {

        return gameId;
    }

    public void setGameId(String gameId) {

        this.gameId = gameId;
    }

    public String getCurrentPlayer() {

        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {

        this.currentPlayer = currentPlayer;
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    public PlayerDTO getPlayer1() {

        return player1;
    }

    public void setPlayer1(PlayerDTO player1) {

        this.player1 = player1;
    }

    public PlayerDTO getPlayer2() {

        return player2;
    }

    public void setPlayer2(PlayerDTO player2) {

        this.player2 = player2;
    }

    public String getWinner() {

        return winner;
    }

    public void setWinner(String winner) {

        this.winner = winner;
    }
}
