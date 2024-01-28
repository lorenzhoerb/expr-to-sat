package com.lorenzhoerb.boolexpr.expr.ops;

import com.lorenzhoerb.boolexpr.expr.IExpr;

public abstract class BinaryOperator implements IExpr {

    private final IExpr a;
    private final IExpr b;

    protected BinaryOperator(IExpr a, IExpr b) {
        this.a = a;
        this.b = b;
    }

    public IExpr getA() {
        return a;
    }

    public IExpr getB() {
        return b;
    }
}
