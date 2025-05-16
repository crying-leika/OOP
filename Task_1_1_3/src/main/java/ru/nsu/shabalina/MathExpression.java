package ru.nsu.shabalina;

public abstract class MathExpression {
    public abstract int calculate(String variables);
    public abstract MathExpression differentiate(String var);
    public abstract String toExpressionString();
    public abstract MathExpression reduce();
}

