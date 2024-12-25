package de.rccookie.aoc.aoc24.clean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.util.Console;

public class Solution24 extends FastSolution {

    private Map<String, Boolean> results;
    private Map<String, Operation> operations;

    @Override
    public Object task1() {
        results = new HashMap<>();
        operations = new HashMap<>();

        String[] parts = input.split("\n\n");
        parts[0].lines().forEach(l -> results.put(l.substring(0,3), l.endsWith("1")));
        parts[1].lines().forEach(l -> {
            String[] p = l.split(" ");
            operations.put(p[4], new Operation(p[0], p[2], Operator.valueOf(p[1])));
        });

        return result("z");
    }

    private boolean result(String key) {
        Boolean res = results.get(key);
        if(res != null)
            return res;

        Operation op = operations.get(key);
        res = switch(op.op) {
            case AND -> result(op.a) && result(op.b);
            case OR ->  result(op.a) || result(op.b);
            case XOR -> result(op.a) ^  result(op.b);
        };
        Console.log(key, "=", op, "=", res);
        results.put(key, res);
        return res;
    }

    @Override
    public Object task2() {
        // Algorithm taken from https://github.com/maneatingape/advent-of-code-rust/blob/main/src/year2024/day24.rs -
        // I can't be bothered to write a generic solution to this myself

        String[] parts = input.split("\n\n");
        results = new HashMap<>();
        operations = new HashMap<>();

        Map<String, Set<Operator>> nextOps = new HashMap<>();
        Set<String> swapped = new HashSet<>();

        parts[1].lines().forEach(l -> {
            String[] p = l.split(" ");
            Operator op = Operator.valueOf(p[1]);
            nextOps.computeIfAbsent(p[0], $ -> new HashSet<>()).add(op);
            nextOps.computeIfAbsent(p[2], $ -> new HashSet<>()).add(op);
        });

        parts[1].lines().forEach(l -> {
            String[] p = l.split(" ");
            String a = p[0], b = p[2], r = p[4];
            if(!switch(Operator.valueOf(p[1])) {
                case AND -> "x00".equals(a) || "x00".equals(b) || nextOps.getOrDefault(r, Set.of()).contains(Operator.OR);
                case OR -> !r.startsWith("z") || r.equals("z45");
                case XOR -> {
                    if(a.startsWith("x") || b.startsWith("x"))
                        yield a.equals("x00") || b.equals("x00") || nextOps.getOrDefault(r, Set.of()).contains(Operator.XOR);
                    else yield r.startsWith("z");
                }
            }) {
                Console.log(l);
                swapped.add(r);
            }
        });

        return swapped.stream().sorted().collect(Collectors.joining(","));
    }

    private record Operation(String a, String b, Operator op) {
        @Override
        public String toString() {
            return a + " " + op + " " + b;
        }
    }

    private enum Operator { AND, OR, XOR }
}
