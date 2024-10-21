package ru.nsu.shabalina;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class QuotientTest {
    private final MathExpression quotient = new Quotient(new Constant(200), new VariableExpression("p"));

    @Test
    void testCalculate() {
        assertEquals(10, quotient.calculate("p=20"));
    }

    @Test
    void testDifferentiate() {
        MathExpression derivative = quotient.differentiate("p");
        assertEquals("(((0*p)-(1*200))/(p*p))", derivative.toExpressionString());
    }

    @Test
    void testToString() {
        assertEquals("(200/p)", quotient.toExpressionString());
    }
}
