package ru.nsu.shabalina;

import java.util.Objects;

public final class Difference extends MathExpression {
    private final MathExpression left;
    private final MathExpression right;

    public Difference(MathExpression left, MathExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int calculate(String variables) {
        return left.calculate(variables) - right.calculate(variables);
    }

    @Override
    public MathExpression differentiate(String var) {
        return new Difference(left.differentiate(var), right.differentiate(var));
    }

    @Override
    public String toExpressionString() {
        return "(" + left.toExpressionString() + "-" + right.toExpressionString() + ")";
    }

    @Override
    public MathExpression reduce() {
        MathExpression simpleLeft = left.reduce();
        MathExpression simpleRight = right.reduce();

        if (simpleLeft instanceof Constant leftConst && simpleRight instanceof Constant rightConst) {
            return new Constant(leftConst.calculate("") - rightConst.calculate(""));
        }

        if (simpleLeft.equals(simpleRight)) {
            return new Constant(0);
        }

        return new Difference(simpleLeft, simpleRight);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Difference other = (Difference) obj;
        return left.equals(other.left) && right.equals(other.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}

