package ru.nsu.shabalina;

import java.util.Map;
import java.util.Objects;

public final class VariableExpression extends MathExpression {
    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public int calculate(String variables) {
        Map<String, Integer> vars = ExpressionParser.parseVariables(variables);
        if (vars == null || !vars.containsKey(name)) {
            return 0;
        }
        return vars.get(name);
    }

    @Override
    public MathExpression differentiate(String var) {
        return new Constant(var.equals(name) ? 1 : 0);
    }

    @Override
    public String toExpressionString() {
        return name;
    }

    @Override
    public MathExpression reduce() {
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        VariableExpression other = (VariableExpression) obj;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
