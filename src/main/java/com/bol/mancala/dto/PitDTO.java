package com.bol.mancala.dto;

public class PitDTO {

    int id;
    int stones;

    public PitDTO(int id, int stones) {

        this.id = id;
        this.stones = stones;
    }
    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public int getStones() {

        return stones;
    }

    public void setStones(int stones) {

        this.stones = stones;
    }
}
