package com.lorenzhoerb.boolexpr.expr.ops;

import com.lorenzhoerb.boolexpr.expr.IExpr;

public abstract class UnaryOperator implements IExpr {

    private final IExpr a;

    public UnaryOperator(IExpr a) {
        this.a = a;
    }

    public IExpr getA() {
        return a;
    }
}
