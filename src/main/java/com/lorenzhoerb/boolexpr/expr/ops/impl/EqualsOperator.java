package com.lorenzhoerb.boolexpr.expr.ops.impl;

import com.lorenzhoerb.boolexpr.expr.IExpr;
import com.lorenzhoerb.boolexpr.expr.ops.BinaryOperator;

public class EqualsOperator extends BinaryOperator {
    protected EqualsOperator(IExpr a, IExpr b) {
        super(a, b);
    }

    @Override
    public boolean isCnf() {
        return false;
    }

    @Override
    public IExpr getCnf() {
        return new OrOperator(
                new AndOperator(getA(), getB()),
                new AndOperator(new NotOperator(getA()), new NotOperator(getB()))
        ).getCnf();
    }

    @Override
    public String toString() {
        return "(" + getA() + "=" + getB() + ")";
    }
}
