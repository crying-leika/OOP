package ru.nsu.shabalina;

import java.util.Objects;

public final class Product extends MathExpression {
    private final MathExpression left;
    private final MathExpression right;

    public Product(MathExpression left, MathExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int calculate(String variables) {
        return left.calculate(variables) * right.calculate(variables);
    }

    @Override
    public MathExpression differentiate(String var) {
        return new Sum(
                new Product(left.differentiate(var), right),
                new Product(right.differentiate(var), left)
        );
    }

    @Override
    public String toExpressionString() {
        return "(" + left.toExpressionString() + "*" + right.toExpressionString() + ")";
    }

    @Override
    public MathExpression reduce() {
        MathExpression simpleLeft = left.reduce();
        MathExpression simpleRight = right.reduce();

        if (simpleLeft instanceof Constant leftConst && simpleRight instanceof Constant rightConst) {
            return new Constant(leftConst.calculate("") * rightConst.calculate(""));
        }

        if (simpleLeft instanceof Constant leftConst) {
            int leftValue = leftConst.calculate("");
            if (leftValue == 0) return new Constant(0);
            if (leftValue == 1) return simpleRight;
        }

        if (simpleRight instanceof Constant rightConst) {
            int rightValue = rightConst.calculate("");
            if (rightValue == 0) return new Constant(0);
            if (rightValue == 1) return simpleLeft;
        }

        return new Product(simpleLeft, simpleRight);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product other = (Product) obj;
        return left.equals(other.left) && right.equals(other.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}

