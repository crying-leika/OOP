package ru.nsu.shabalina;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

class ParserTest {
    @Test
    void testRPNConversion() {
        List<String> rpn = ExpressionParser.convertToRPN("12345+(5678-3)*var-45/3");
        assertArrayEquals(
                new String[]{"12345", "5678", "3", "-", "var", "*", "+", "45", "3", "/", "-"},
                rpn.toArray()
        );
    }

    @Test
    void testExpressionCreation() {
        MathExpression expr = ExpressionParser.parseExpression("567+7*8-10/xy");
        MathExpression expected = new Difference(
                new Sum(new Constant(567), new Product(new Constant(7), new Constant(8))),
                new Quotient(new Constant(10), new VariableExpression("xy"))
        );
        assertEquals(expected.toExpressionString(), expr.toExpressionString());
    }
}

