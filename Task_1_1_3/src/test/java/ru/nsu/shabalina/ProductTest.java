package ru.nsu.shabalina;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ProductTest {
    private final MathExpression product = new Product(new Constant(42), new VariableExpression("y"));

    @Test
    void testCalculate() {
        assertEquals(294, product.calculate("y=7"));
    }

    @Test
    void testDifferentiate() {
        MathExpression derivative = product.differentiate("y");
        assertEquals("((0*y)+(1*42))", derivative.toExpressionString());
    }

    @Test
    void testToString() {
        assertEquals("(42*y)", product.toExpressionString());
    }
}

