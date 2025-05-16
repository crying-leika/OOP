package ru.nsu.shabalina.model;

public class Food {
    private final Coordinate position;

    public Food(Coordinate position) {
        this.position = position;
    }

    public Coordinate getPosition() {
        return position;
    }
}

