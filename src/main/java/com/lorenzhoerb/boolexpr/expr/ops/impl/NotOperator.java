package com.lorenzhoerb.boolexpr.expr.ops.impl;

import com.lorenzhoerb.boolexpr.expr.AtomExpr;
import com.lorenzhoerb.boolexpr.expr.IExpr;
import com.lorenzhoerb.boolexpr.expr.ops.UnaryOperator;

public class NotOperator extends UnaryOperator {
    public NotOperator(IExpr a) {
        super(a);
    }

    @Override
    public boolean isCnf() {
        return getA() instanceof AtomExpr;
    }

    @Override
    public IExpr getCnf() {

        IExpr aCnf = getA().getCnf();

        return switch (aCnf) {
            case AtomExpr expr -> new NotOperator(expr);
            case NotOperator expr -> expr.getA();
            case OrOperator expr ->
                    new AndOperator(new NotOperator(expr.getA().getCnf()), new NotOperator(expr.getB().getCnf()));
            case AndOperator expr ->
                    new OrOperator(new NotOperator(expr.getA().getCnf()), new NotOperator(expr.getB().getCnf()));
            default -> throw new IllegalStateException("Unexpected value: " + aCnf);
        };
    }

    @Override
    public String toString() {
        return "-" + getA().toString();
    }
}
