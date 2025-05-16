package ru.nsu.shabalina.model;

import org.junit.jupiter.api.Test; // JUnit 5
import static org.junit.jupiter.api.Assertions.*; // JUnit 5 assertions

public class GameFieldTest {
    @Test
    public void testFoodSpawn() {
        GameField field = new GameField(10);
        assertFalse(field.getFoods().isEmpty());
    }
}
