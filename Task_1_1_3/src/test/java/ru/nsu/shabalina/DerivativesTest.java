package ru.nsu.shabalina;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DerivativesTest {
    @Test
    void testSimpleDerivative() {
        MathExpression expr = ExpressionParser.parseExpression("4+(3*z)");
        MathExpression derivative = expr.differentiate("z");
        assertEquals("(0+((0*z)+(1*3)))", derivative.toExpressionString());
    }

    @Test
    void testMultipleVariables() {
        MathExpression expr = ExpressionParser.parseExpression("y*4+(x-2)");
        MathExpression derivative = expr.differentiate("x");
        assertEquals("(((0*4)+(0*y))+(1-0))", derivative.toExpressionString());
    }

    @Test
    void testComplexExpression() {
        MathExpression expr = ExpressionParser.parseExpression("(y*4/x)-6");
        MathExpression derivative = expr.differentiate("x");
        assertEquals("((((((0*4)+(0*y))*x)-(1*(y*4)))/(x*x))-0)", derivative.toExpressionString());
    }
}

