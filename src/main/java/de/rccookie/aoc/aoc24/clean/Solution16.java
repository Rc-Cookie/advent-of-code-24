package de.rccookie.aoc.aoc24.clean;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.graph.BinaryHeap;
import de.rccookie.graph.Graph;
import de.rccookie.graph.Graphs;
import de.rccookie.graph.HashGraph;
import de.rccookie.graph.Heap;
import de.rccookie.graph.SimpleGraph;
import de.rccookie.graph.SimpleHashGraph;
import de.rccookie.math.Mathf;
import de.rccookie.math.int2;
import de.rccookie.math.int3;

public class Solution16 extends FastSolution {

    private int2 start, end;
    private Graph<int3, Integer> maze;

    @Override
    public void load() {
        start = grid.findAndSet('S', '.');
        end = grid.findAndSet('E', '.');

        maze = new HashGraph<>(false);
        for(int2 p : size) {
            if(grid.charAt(p) != '.') continue;
            maze.connect(new int3(p, 0), new int3(p, 1), 1000);

            int2 right = p.added(1,0), down = p.added(0,1);
            if(grid.charAt(right) == '.')
                maze.connect(new int3(p,0), new int3(right,0), 1);
            if(grid.charAt(down) == '.')
                maze.connect(new int3(p,1), new int3(down,1), 1);
        }
        maze.connect(new int3(end,0), new int3(end,1), 0);
    }

    @Override
    public Object task1() {
        return Mathf.round(Graphs.shortestPath(maze, new int3(start,0), new int3(end,0), i->i).distance());
    }

    @Override
    public Object task2() {
        return shortestPaths(start.xy0(), end.xy0(), maze);
    }

    private static int shortestPaths(int3 start, int3 end, Graph<int3, Integer> maze) {

        Map<int3, List<int3>> p = new HashMap<>();
        Map<int3, Integer> d = new HashMap<>();

        for(int3 n : maze) {
            d.put(n, Integer.MAX_VALUE);
            p.put(n, new ArrayList<>());
        }
        d.put(start, 0);

        Heap<int3> q = new BinaryHeap<>(Comparator.comparing(n -> d.get(n) + n.dist2(end)));
        q.enqueue(start);

        while(!q.isEmpty()) {
            int3 n = q.dequeue();
            if(n.equals(end)) {
                SimpleGraph<int3> paths = new SimpleHashGraph<>();
                p.forEach((m,ps) -> paths.connectToAll(m,ps,1));
                return (int) Graphs.traverseBreathFirst(paths, end).map(int3::xy).distinct().count();
            }

            int nd = d.get(n);
            maze.adj(n).forEach((m,e) -> {
                int dist = nd + e;
                if(dist < d.get(m)) {
                    d.put(m,dist);
                    List<int3> prev = p.get(m);
                    prev.clear();
                    prev.add(n);
                    if(!q.updateDecreased(m))
                        q.enqueue(m);
                }
                else if(dist == d.get(m))
                    p.get(m).add(n);
            });
        }

        throw new AssertionError("No path found");
    }
}
