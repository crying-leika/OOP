package ru.nsu.shabalina;

import java.util.Objects;

public final class Quotient extends MathExpression {
    private final MathExpression numerator;
    private final MathExpression denominator;

    public Quotient(MathExpression numerator, MathExpression denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public int calculate(String variables) {
        int denom = denominator.calculate(variables);
        if (denom == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return numerator.calculate(variables) / denom;
    }

    @Override
    public MathExpression differentiate(String var) {
        return new Quotient(
                new Difference(
                        new Product(numerator.differentiate(var), denominator),
                        new Product(denominator.differentiate(var), numerator)
                ),
                new Product(denominator, denominator)
        );
    }

    @Override
    public String toExpressionString() {
        return "(" + numerator.toExpressionString() + "/" + denominator.toExpressionString() + ")";
    }

    @Override
    public MathExpression reduce() {
        MathExpression simpleNum = numerator.reduce();
        MathExpression simpleDenom = denominator.reduce();

        if (simpleNum instanceof Constant numConst && simpleDenom instanceof Constant denomConst) {
            return new Constant(numConst.calculate("") / denomConst.calculate(""));
        }

        return new Quotient(simpleNum, simpleDenom);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Quotient other = (Quotient) obj;
        return numerator.equals(other.numerator) && denominator.equals(other.denominator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }
}

