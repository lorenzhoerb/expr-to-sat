package com.lorenzhoerb.boolexpr.expr;

public class AtomExpr implements IExpr {
    private final String a;

    public AtomExpr(String a) {
        this.a = a;
    }

    public String getA() {
        return a;
    }

    @Override
    public boolean isCnf() {
        return true;
    }

    @Override
    public IExpr getCnf() {
        return new AtomExpr(a);
    }

    @Override
    public String toString() {
        return a;
    }
}
