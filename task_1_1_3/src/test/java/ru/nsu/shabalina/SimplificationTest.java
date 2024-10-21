package ru.nsu.shabalina;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SimplificationTest {
    @Test
    void testConstantSimplification() {
        MathExpression expr = ExpressionParser.parseExpression("4+(3*6)");
        assertEquals("22", expr.reduce().toExpressionString());
    }

    @Test
    void testMultiplyByZero() {
        MathExpression expr = ExpressionParser.parseExpression("(5+245-6/y)*0");
        assertEquals("0", expr.reduce().toExpressionString());
    }

    @Test
    void testMultiplyByOne() {
        MathExpression expr = ExpressionParser.parseExpression("(35-32*y)*1");
        assertEquals("(35-(32*y))", expr.reduce().toExpressionString());
    }

    @Test
    void testSubtractEqualExpressions() {
        MathExpression expr = ExpressionParser.parseExpression("(245*22+3)-(245*22+3)");
        assertEquals("0", expr.reduce().toExpressionString());
    }
}
