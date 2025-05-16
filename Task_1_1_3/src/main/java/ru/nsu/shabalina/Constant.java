package ru.nsu.shabalina;

import java.util.Objects;

public final class Constant extends MathExpression {
    private final int value;

    public Constant(int value) {
        this.value = value;
    }

    @Override
    public int calculate(String variables) {
        return value;
    }

    @Override
    public MathExpression differentiate(String var) {
        return new Constant(0);
    }

    @Override
    public String toExpressionString() {
        return Integer.toString(value);
    }

    @Override
    public MathExpression reduce() {
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Constant other = (Constant) obj;
        return value == other.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
