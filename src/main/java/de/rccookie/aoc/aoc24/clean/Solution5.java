package de.rccookie.aoc.aoc24.clean;

import java.util.ArrayList;
import java.util.List;

import de.rccookie.aoc.aoc24.FastSolution;
import de.rccookie.graph.Graph;
import de.rccookie.graph.HashGraph;
import de.rccookie.util.ListStream;

public class Solution5 extends FastSolution {

    private Graph<Integer,String> rules;

    @Override
    public Object task1() {
        loadRules();
        return orders().filter(this::isInOrder).mapToLong(o -> o[o.length/2]).sum();
    }

    @Override
    public Object task2() {

        loadRules();
        return orders().filter(o -> !isInOrder(o)).mapToLong(o -> {
            List<Integer> order = new ArrayList<>();
            outer: for(int p : o) {
                if(order.isEmpty() || isInOrder(order.getLast(), p))
                    order.add(p);
                else if(isInOrder(p, order.getFirst()))
                    order.add(0, p);
                else {
                    for(int i=1; i<order.size(); i++) {
                        if(isInOrder(order.get(i-1), p) && isInOrder(p, order.get(i))) {
                            order.add(i, p);
                            continue outer;
                        }
                    }
                    throw new RuntimeException("Greedy sort failed");
                }
            }
            return order.get(order.size() / 2);
        }).sum();
    }

    private void loadRules() {
        rules = new HashGraph<>();
        int lineNr = 1;
        for(String line : linesArr) {
            if(line.isBlank()) break;
            int[] ints = parseInts(line);
            rules.connect(ints[0], ints[1], "Line " + (lineNr++) + " ("+line+")");
        }
    }

    private ListStream<int[]> orders() {
        return lines.dropWhile(l -> !l.isBlank()).skip(1).map(FastSolution::parseInts);
    }

    private boolean isInOrder(int[] order) {
        for(int i=1; i<order.length; i++)
            if(!isInOrder(order[i-1], order[i]))
                return false;
        return true;
    }

    private boolean isInOrder(int a, int b) {
        return !rules.connected(b,a);
    }
}
