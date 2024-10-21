package ru.nsu.shabalina;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ConstantTest {
    private final MathExpression constant = new Constant(12345);

    @Test
    void testCalculate() {
        assertEquals(12345, constant.calculate("x=10;y=20"));
    }

    @Test
    void testDifferentiate() {
        assertEquals(new Constant(0), constant.differentiate("x"));
    }

    @Test
    void testToString() {
        assertEquals("12345", constant.toExpressionString());
    }

    @Test
    void testReduce() {
        assertEquals(constant, constant.reduce());
    }
}
