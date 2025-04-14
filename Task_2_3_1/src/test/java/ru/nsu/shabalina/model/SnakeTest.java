package ru.nsu.shabalina.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

public class SnakeTest {

    @Test
    public void testSnakeInitialization() {
        Snake snake = new Snake(5, 5);
        LinkedList<Coordinate> segments = snake.getSegments();
        assertEquals(1, segments.size(), "Snake should have one segment initially");
        assertEquals(5, segments.getFirst().getX(), "Snake head X position should be 5");
        assertEquals(5, segments.getFirst().getY(), "Snake head Y position should be 5");
        assertEquals(Direction.RIGHT, snake.getDirection(), "Initial direction should be RIGHT");
    }

    @Test
    public void testSnakeMoveRight() {
        Snake snake = new Snake(5, 5);
        snake.move();
        Coordinate head = snake.getHeadPosition();
        assertEquals(6, head.getX(), "Snake should move right to X=6");
        assertEquals(5, head.getY(), "Y position should remain 5");
        assertEquals(1, snake.getSegments().size(), "Snake size should remain 1");
    }

    @Test
    public void testSnakeMoveUp() {
        Snake snake = new Snake(5, 5);
        snake.setDirection(Direction.UP);
        snake.move();
        Coordinate head = snake.getHeadPosition();
        assertEquals(5, head.getX(), "X position should remain 5");
        assertEquals(4, head.getY(), "Snake should move up to Y=4");
        assertEquals(1, snake.getSegments().size(), "Snake size should remain 1");
    }

    @Test
    public void testSnakeGrow() {
        Snake snake = new Snake(5, 5);
        snake.grow();
        LinkedList<Coordinate> segments = snake.getSegments();
        assertEquals(2, segments.size(), "Snake should have two segments after growing");
        assertEquals(5, segments.getLast().getX(), "Tail X position should be 5");
        assertEquals(5, segments.getLast().getY(), "Tail Y position should be 5");
    }

}
