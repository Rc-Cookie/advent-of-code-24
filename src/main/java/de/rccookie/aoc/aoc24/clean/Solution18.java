package de.rccookie.aoc.aoc24.clean;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.graph.Graph;
import de.rccookie.graph.Graphs;
import de.rccookie.math.Mathf;
import de.rccookie.math.int2;
import de.rccookie.util.Console;

public class Solution18 extends FastSolution {

    private static final int SIZE = 71;

    @Override
    public Object task1() {
        Graph<int2,Integer> g = Graphs.grid2d(SIZE, SIZE);
        lines.limit(1024).map(FastSolution::parseInts).map(int2::fromArray).forEach(g::remove);
        return Mathf.round(Graphs.shortestPath(g, int2.zero(), new int2(SIZE-1), Integer::doubleValue).distance());
    }

    @Override
    public Object task2() {
        Graph<int2,Integer> g = Graphs.grid2d(SIZE, SIZE);
        int i = 0;
        for(int2 p : lines.map(FastSolution::parseInts).map(int2::fromArray)) {
            Console.log(++i);
            g.remove(p);
            if(Graphs.shortestPath(g, int2.zero(), new int2(SIZE-1), Integer::doubleValue) == null)
                return p.x()+","+p.y();
        }
        throw new AssertionError("Ends still connect");
    }
}
