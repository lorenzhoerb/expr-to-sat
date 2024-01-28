package com.lorenzhoerb.boolexpr.expr.ops.impl;

import com.lorenzhoerb.boolexpr.expr.AtomExpr;
import com.lorenzhoerb.boolexpr.expr.IExpr;
import com.lorenzhoerb.boolexpr.expr.ops.BinaryOperator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class OrOperator extends BinaryOperator {
    public OrOperator(IExpr a, IExpr b) {
        super(a, b);
    }

    @Override
    public boolean isCnf() {
        Predicate<Object> checkValidClasses = aClass -> Set.of(AtomExpr.class, NotOperator.class,
                OrOperator.class).contains(aClass.getClass());
        if (!checkValidClasses.test(getA()) || !checkValidClasses.test(getB())) {
            return false;
        }

        return getA().isCnf() && getB().isCnf();
    }

    @Override
    public IExpr getCnf() {

        IExpr aCnf = getA().getCnf();
        IExpr bCnf = getB().getCnf();


        List<IExpr> clausesA = getAllClausesFromCNFFormula(aCnf);
        List<IExpr> clausesB = getAllClausesFromCNFFormula(bCnf);

        var allOrClauses = new ArrayList<OrOperator>();
        for (var clauseA : clausesA) {
            for (var clauseB : clausesB) {
                allOrClauses.add(new OrOperator(clauseA, clauseB));
            }
        }

        return allOrClauses.size() >= 2 ? constructAndOperator(allOrClauses) : allOrClauses.get(0);
    }

    private AndOperator constructAndOperator(List<OrOperator> allClauses) {
        var result = new AndOperator(allClauses.get(0), allClauses.get(1));
        for (var i = 2; i < allClauses.size(); i++) {
            result = new AndOperator(allClauses.get(i), result);
        }
        return result;
    }

    private List<IExpr> getAllClausesFromCNFFormula(IExpr formula) {
        var clauses = new ArrayList<IExpr>();

        var formulaStack = new LinkedList<IExpr>();
        formulaStack.add(formula);

        while (!formulaStack.isEmpty()) {
            var currentFormula = formulaStack.pop();
            if (currentFormula instanceof AtomExpr || currentFormula instanceof NotOperator
                    || currentFormula instanceof OrOperator) {
                clauses.add(currentFormula);
            }

            if (currentFormula instanceof AndOperator) {
                formulaStack.add(((AndOperator) currentFormula).getA());
                formulaStack.add(((AndOperator) currentFormula).getB());
            }
        }

        return clauses;
    }

    @Override
    public String toString() {
        return "(" + getA() + "|" + getB() + ")";
    }
}
