package ru.nsu.shabalina;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class VariableTest {
    private final MathExpression variable = new VariableExpression("dragon");

    @Test
    void testCalculate() {
        assertEquals(10, variable.calculate("dragon=10"));
    }

    @Test
    void testDifferentiate() {
        assertEquals(new Constant(1), variable.differentiate("dragon"));
        assertEquals(new Constant(0), variable.differentiate("x"));
    }

    @Test
    void testToString() {
        assertEquals("dragon", variable.toExpressionString());
    }
}