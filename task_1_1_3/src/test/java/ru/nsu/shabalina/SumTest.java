package ru.nsu.shabalina;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SumTest {
    private final MathExpression sum = new Sum(new Constant(5), new VariableExpression("x"));

    @Test
    void testCalculate() {
        assertEquals(10, sum.calculate("x=5"));
    }

    @Test
    void testDifferentiate() {
        MathExpression derivative = sum.differentiate("x");
        assertEquals(new Sum(new Constant(0), new Constant(1)), derivative);
    }

    @Test
    void testToString() {
        assertEquals("(5+x)", sum.toExpressionString());
    }

    @Test
    void testReduce() {
        assertEquals(sum, sum.reduce());
    }
}

