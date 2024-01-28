package com.lorenzhoerb.boolexpr.expr;

public interface IExpr {

    boolean isCnf();

    IExpr getCnf();

    String toString();
}
