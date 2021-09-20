package com.bol.mancala.model;

public class Pit implements Cloneable {

    public enum Type {
        SMALL, BIG
    }

    private final int id;
    private int stones;
    private final Pit.Type type;
    private Pit next;
    private Pit opposite;

    public Pit(int id, int stones, Type type) {

        this.id = id;
        this.stones = stones;
        this.type = type;
    }

    public int getId() {

        return id;
    }

    public int getStones() {

        return stones;
    }

    public Type getType() {

        return type;
    }

    public Pit getNext() {

        return next;
    }

    public void setNext(Pit next) {

        this.next = next;
    }

    public Pit getOpposite() {

        return opposite;
    }

    public void setOpposite(Pit opposite) {

        this.opposite = opposite;
    }

    public void addStone() {

        this.stones++;
    }

    public void addStones(int stones) {

        this.stones += stones;
    }

    public void clearStones() {

        this.stones = 0;
    }

    public Pit clone() throws CloneNotSupportedException {

        return (Pit) super.clone();
    }
}
