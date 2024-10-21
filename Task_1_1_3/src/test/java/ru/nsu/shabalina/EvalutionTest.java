package ru.nsu.shabalina;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class EvaluationTest {
    @Test
    void testSimpleEvaluation() {
        MathExpression expr = ExpressionParser.parseExpression("5+(3*y)");
        assertEquals(14, expr.calculate("y=3"));
    }

    @Test
    void testMultiVariableEvaluation() {
        MathExpression expr = ExpressionParser.parseExpression("(monster+is)*awesome-200/n");
        assertEquals(41, expr.calculate("monster=40;is=7;awesome=3;n=2"));
    }

    @Test
    void testComplexEvaluation() {
        MathExpression expr = ExpressionParser.parseExpression("((6*(2+5))*4)/((53-43)*3)");
        assertEquals(5, expr.calculate(""));
    }

    @Test
    void testSingleVariable() {
        MathExpression expr = ExpressionParser.parseExpression("you");
        assertEquals(45, expr.calculate("you=45"));
    }
}