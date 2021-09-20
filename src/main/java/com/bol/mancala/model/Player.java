package com.bol.mancala.model;

import java.util.List;
import java.util.Optional;

public class Player implements Cloneable {

    private final String name;
    private final List<Pit> smallPits;
    private final Pit bigPit;

    public Player(String name, List<Pit> smallPits, Pit bigPit) {

        this.name = name;
        this.smallPits = smallPits;
        this.bigPit = bigPit;
    }

    public String getName() {

        return name;
    }

    public List<Pit> getSmallPits() {

        return smallPits;
    }

    public boolean containsPit(int pitId) {

        return smallPits.stream().anyMatch(pit -> pit.getId() == pitId);
    }

    public Optional<Pit> getSmallPitById(int pitId) {

        return smallPits.stream().filter(pit -> pit.getId() == pitId).findAny();
    }

    public Pit getBigPit() {

        return bigPit;
    }

    public Player clone() throws CloneNotSupportedException {

        return (Player) super.clone();
    }
}
