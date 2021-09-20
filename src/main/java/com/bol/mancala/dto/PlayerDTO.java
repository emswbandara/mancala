package com.bol.mancala.dto;

import java.util.List;

public class PlayerDTO {

    private String name;
    private List<PitDTO> smallPits;
    private int bigPit;

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public List<PitDTO> getSmallPits() {

        return smallPits;
    }

    public void setSmallPits(List<PitDTO> smallPits) {

        this.smallPits = smallPits;
    }

    public int getBigPit() {

        return bigPit;
    }

    public void setBigPit(int bigPit) {

        this.bigPit = bigPit;
    }
}
