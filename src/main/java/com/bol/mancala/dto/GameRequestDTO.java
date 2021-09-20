package com.bol.mancala.dto;

public class GameRequestDTO {

    private String player1Name;
    private String player2Name;
    private int boardSize;
    private int pitSize;

    public String getPlayer1Name() {

        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {

        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {

        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {

        this.player2Name = player2Name;
    }

    public int getBoardSize() {

        return boardSize;
    }

    public void setBoardSize(int boardSize) {

        this.boardSize = boardSize;
    }

    public int getPitSize() {

        return pitSize;
    }

    public void setPitSize(int pitSize) {

        this.pitSize = pitSize;
    }
}
