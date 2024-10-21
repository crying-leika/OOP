package ru.nsu.shabalina;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DifferenceTest {
    private final MathExpression difference = new Difference(new Constant(140), new VariableExpression("xp"));

    @Test
    void testCalculate() {
        assertEquals(-3, difference.calculate("xp=143"));
    }

    @Test
    void testDifferentiate() {
        MathExpression derivative = difference.differentiate("xp");
        assertEquals("(0-1)", derivative.toExpressionString());
    }

    @Test
    void testToString() {
        assertEquals("(140-xp)", difference.toExpressionString());
    }
}

