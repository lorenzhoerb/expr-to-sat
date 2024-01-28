package com.lorenzhoerb.boolexpr;

import com.lorenzhoerb.boolexpr.expr.AtomExpr;
import com.lorenzhoerb.boolexpr.expr.ExprUtils;
import com.lorenzhoerb.boolexpr.expr.IExpr;
import com.lorenzhoerb.boolexpr.expr.ops.impl.AndOperator;
import com.lorenzhoerb.boolexpr.expr.ops.impl.ImpliesOperator;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws ContradictionException, TimeoutException {
        Map<String, Integer> varNameIntMap = Map.of(
                "a", 1,
                "b", 2,
                "c", 3,
                "d", 4
        );
        Map<Integer, String> reversedMap = reverseMap(varNameIntMap);

        IExpr a = new AndOperator(new ImpliesOperator(new AtomExpr("a"), new AtomExpr("b")), new AtomExpr("d"));

        ISolver solver = ExprUtils.getSolverFromExpression(a, varNameIntMap);
        printSatResult(solver, reversedMap);
    }

    private static void printSatResult(ISolver solver, Map<Integer, String> reversedMap) throws TimeoutException {
        boolean isSatisfiable = solver.isSatisfiable();
        System.out.println("Is satisfiable: " + isSatisfiable);

        if (isSatisfiable) {
            int[] model = solver.model();
            System.out.println("Variable assignments:");

            for (int varId : model) {
                String varName = reversedMap.get(Math.abs(varId));
                boolean isTrue = varId > 0;
                System.out.println(varName + ": " + isTrue);
            }
        }
    }

    private static <K, V> Map<V, K> reverseMap(Map<K, V> originalMap) {
        Map<V, K> reversedMap = new HashMap<>();
        for (Map.Entry<K, V> entry : originalMap.entrySet()) {
            reversedMap.put(entry.getValue(), entry.getKey());
        }
        return reversedMap;
    }
}