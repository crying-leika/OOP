package ru.nsu.shabalina.model;

import java.util.LinkedList;

public class Snake {
    private final LinkedList<Coordinate> segments;
    private Direction direction;

    public Snake(int x, int y) {
        segments = new LinkedList<>();
        segments.add(new Coordinate(x, y));
        direction = Direction.RIGHT;
    }

    public void move() {
        Coordinate head = segments.getFirst();
        Coordinate newHead = switch (direction) {
            case UP -> new Coordinate(head.getX(), head.getY() - 1);
            case DOWN -> new Coordinate(head.getX(), head.getY() + 1);
            case LEFT -> new Coordinate(head.getX() - 1, head.getY());
            case RIGHT -> new Coordinate(head.getX() + 1, head.getY());
        };
        segments.addFirst(newHead);
        segments.removeLast();
    }

    public void grow() {
        Coordinate tail = segments.getLast();
        segments.addLast(tail); // Добавляем копию хвоста для роста
    }

    public boolean collidesWithItself() {
        Coordinate head = segments.getFirst();
        for (int i = 1; i < segments.size(); i++) {
            if (head.equals(segments.get(i))) {
                return true;
            }
        }
        return false;
    }

    public void setDirection(Direction dir) {
        if (direction == Direction.UP && dir == Direction.DOWN ||
                direction == Direction.DOWN && dir == Direction.UP ||
                direction == Direction.LEFT && dir == Direction.RIGHT ||
                direction == Direction.RIGHT && dir == Direction.LEFT) {
            return;
        }
        this.direction = dir;
    }

    public Coordinate getHeadPosition() {
        return segments.getFirst();
    }

    public LinkedList<Coordinate> getSegments() {
        return segments;
    }

    public Direction getDirection() {
        return direction;
    }
}

