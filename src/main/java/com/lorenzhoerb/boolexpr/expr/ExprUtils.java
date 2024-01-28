package com.lorenzhoerb.boolexpr.expr;

import com.lorenzhoerb.boolexpr.expr.ops.impl.AndOperator;
import com.lorenzhoerb.boolexpr.expr.ops.impl.NotOperator;
import com.lorenzhoerb.boolexpr.expr.ops.impl.OrOperator;
import org.sat4j.core.VecInt;
import org.sat4j.maxsat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExprUtils {

    public static List<List<String>> convertCnfToClauses(IExpr expr) {
        List<List<String>> clauses = new ArrayList<>();
        process(expr, clauses);
        return clauses;
    }

    private static void process(IExpr expr, List<List<String>> clauses) {
        List<String> firstClause = new ArrayList<>();
        process(expr, clauses, firstClause);
        if (!firstClause.isEmpty()) clauses.add(firstClause);
    }

    private static void process(IExpr expr, List<List<String>> clauses, List<String> clause) {
        switch (expr) {
            case AndOperator andOperator -> {
                List<String> c1 = new ArrayList<>();
                List<String> c2 = new ArrayList<>();
                process(andOperator.getA(), clauses, c1);
                process(andOperator.getB(), clauses, c2);
                if (!c1.isEmpty()) clauses.add(c1);
                if (!c2.isEmpty()) clauses.add(c2);
            }
            case OrOperator orOperator -> {
                process(orOperator.getA(), clauses, clause);
                process(orOperator.getB(), clauses, clause);
            }
            case AtomExpr atomExpr -> clause.add(expr.toString());
            case NotOperator notOperator -> clause.add(expr.toString());
            case null, default -> throw new RuntimeException("expr is not in CNF");
        }
    }

    public static List<int[]> convertCnfToSatClauses(IExpr booleanExpression, Map<String, Integer> varNameIntMap) {
        List<List<String>> cnfClauses = ExprUtils.convertCnfToClauses(booleanExpression.getCnf());
        List<int[]> satClauses = new ArrayList<>();

        for (List<String> clause : cnfClauses) {
            int[] satClause = clause.stream()
                    .map(varName -> {
                        int satId = varNameIntMap.get(varName.startsWith("-") ? varName.substring(1) : varName);
                        return varName.startsWith("-") ? -satId : satId;
                    })
                    .mapToInt(Integer::intValue)
                    .toArray();

            satClauses.add(satClause);
        }

        return satClauses;
    }

    public static ISolver getSolverFromExpression(IExpr expr, Map<String, Integer> varNameIntMap) throws ContradictionException {
        List<int[]> satClauses = convertCnfToSatClauses(expr, varNameIntMap);
        ISolver solver = SolverFactory.newDefault();

        for (int[] satClause : satClauses) {
            solver.addClause(new VecInt(satClause));
        }

        return solver;
    }

}
