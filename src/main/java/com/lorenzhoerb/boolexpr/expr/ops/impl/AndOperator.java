package com.lorenzhoerb.boolexpr.expr.ops.impl;

import com.lorenzhoerb.boolexpr.expr.IExpr;
import com.lorenzhoerb.boolexpr.expr.ops.BinaryOperator;

public class AndOperator extends BinaryOperator {

    public AndOperator(IExpr a, IExpr b) {
        super(a, b);
    }

    @Override
    public boolean isCnf() {
        return getA().isCnf() && getB().isCnf();
    }

    @Override
    public IExpr getCnf() {
        IExpr aCnf = getA().getCnf();
        IExpr bCnf = getB().getCnf();
        return new AndOperator(aCnf, bCnf);
    }

    @Override
    public String toString() {
        return "(" + getA() + "&" + getB() + ")";
    }
}
