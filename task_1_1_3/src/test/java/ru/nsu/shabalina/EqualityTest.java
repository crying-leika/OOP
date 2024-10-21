package ru.nsu.shabalina;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class EqualityTest {
    @Test
    void testProductEquality() {
        MathExpression expr1 = new Product(new Constant(42), new VariableExpression("y"));
        MathExpression expr2 = new Product(new Constant(42), new VariableExpression("y"));
        MathExpression expr3 = new Difference(new Constant(15), new Constant(0));
        assertTrue(expr1.equals(expr2));
        assertFalse(expr1.equals(expr3));
    }

    @Test
    void testQuotientEquality() {
        MathExpression expr1 = new Quotient(new Constant(200), new Constant(10));
        MathExpression expr2 = new Quotient(new Constant(200), new Constant(10));
        MathExpression expr3 = new Difference(new Constant(15), new Constant(0));
        assertTrue(expr1.equals(expr2));
        assertFalse(expr1.equals(expr3));
    }

    @Test
    void testSumEquality() {
        MathExpression expr1 = new Sum(new Constant(42), new VariableExpression("y"));
        MathExpression expr2 = new Sum(new Constant(42), new VariableExpression("y"));
        MathExpression expr3 = new Difference(new Constant(15), new Constant(0));
        assertTrue(expr1.equals(expr2));
        assertFalse(expr1.equals(expr3));
    }

    @Test
    void testDifferenceEquality() {
        MathExpression expr1 = new Difference(new Constant(42), new VariableExpression("y"));
        MathExpression expr2 = new Difference(new Constant(42), new VariableExpression("y"));
        MathExpression expr3 = new Quotient(new Constant(15), new Constant(0));
        assertTrue(expr1.equals(expr2));
        assertFalse(expr1.equals(expr3));
    }

    @Test
    void testConstantEquality() {
        MathExpression expr1 = new Constant(345);
        MathExpression expr2 = new Constant(345);
        MathExpression expr3 = new Difference(new Constant(15), new Constant(0));
        assertTrue(expr1.equals(expr2));
        assertFalse(expr1.equals(expr3));
    }

    @Test
    void testVariableEquality() {
        MathExpression expr1 = new VariableExpression("dinosaur");
        MathExpression expr2 = new VariableExpression("dinosaur");
        MathExpression expr3 = new Difference(new Constant(15), new Constant(0));
        assertTrue(expr1.equals(expr2));
        assertFalse(expr1.equals(expr3));
    }
}

