package com.lorenzhoerb.boolexpr.expr.ops.impl;

import com.lorenzhoerb.boolexpr.expr.IExpr;
import com.lorenzhoerb.boolexpr.expr.ops.BinaryOperator;

public class ImpliesOperator extends BinaryOperator {
    public ImpliesOperator(IExpr a, IExpr b) {
        super(a, b);
    }

    @Override
    public boolean isCnf() {
        return false;
    }

    @Override
    public IExpr getCnf() {
        return new OrOperator(new NotOperator(getA()), getB()).getCnf();
    }

    @Override
    public String toString() {
        return "(" + getA() + "->" + getB() + ")";
    }
}
