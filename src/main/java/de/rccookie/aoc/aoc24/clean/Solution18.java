package de.rccookie.aoc.aoc24.clean;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import de.rccookie.aoc.Grid;
import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.graph.Graph;
import de.rccookie.graph.Graphs;
import de.rccookie.math.Mathf;
import de.rccookie.math.int2;
import de.rccookie.util.ListStream;

public class Solution18 extends FastSolution {

    private static final int SIZE = 71;

    @Override
    public Object task1() {
        Graph<int2,Integer> g = Graphs.grid2d(SIZE, SIZE);
        obstacles().limit(1024).forEach(g::remove);
        return Mathf.round(Graphs.shortestPath(g, int2.zero, new int2(SIZE-1), Integer::doubleValue).distance());
    }

    @Override
    public Object task2() {

        List<int2> obstacles = obstacles();

        grid = new Grid(SIZE, SIZE, '.');
        for(int2 o : obstacles)
            grid.set(o, '#');

        Queue<int2> todo = new ArrayDeque<>();
        todo.add(int2.zero());
        grid.mark(int2.zero);

        while(!todo.isEmpty()) {
            int2 p = todo.remove();
            for(int2 a : grid.adjPos4(p))
                if(grid.charAtRaw(a) == '.' && grid.mark(a))
                    todo.add(a);
        }

        for(int2 o : obstacles.reversed()) {
            grid.set(o, '.');
            if(Arrays.stream(grid.adjPos4(o)).noneMatch(grid::marked))
                continue;

            todo.add(o);
            grid.mark(o);

            while(!todo.isEmpty()) {
                int2 p = todo.remove();
                for(int2 a : grid.adjPos4(p))
                    if(grid.charAtRaw(a) == '.' && grid.mark(a))
                        todo.add(a);
            }

            if(grid.marked(new int2(SIZE-1)))
                return o.x()+","+o.y();
        }

        throw new AssertionError("No path found on empty grid");
    }

    private ListStream<int2> obstacles() {
        return lines.map(FastSolution::parseInts).map(int2::fromArray);
    }
}
