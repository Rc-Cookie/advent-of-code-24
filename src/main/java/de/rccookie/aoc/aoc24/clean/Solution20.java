package de.rccookie.aoc.aoc24.clean;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.graph.BinaryHeap;
import de.rccookie.graph.Heap;
import de.rccookie.math.int2;

public class Solution20 extends FastSolution {

    @Override
    public Object task1() {
        return countCuts(2, 100);
    }

    @Override
    public Object task2() {
        return countCuts(20, 100);
    }

    public long countCuts(int maxLen, int minCut) {
        int2 start = grid.findAndSet('S', '.');
        int2 end = grid.findAndSet('E', '.');

        Map<int2, Integer> distances = new HashMap<>();

        for(int2 n : size)
            if(grid.charAt(n) == '.')
                distances.put(n, Integer.MAX_VALUE);
        distances.put(start, 0);

        Heap<int2> q = new BinaryHeap<>(Comparator.comparing(distances::get));
        q.enqueue(start);

        while(!q.isEmpty()) {
            int2 n = q.dequeue();
            if(n.equals(end)) break;

            int dist = distances.get(n);
            for(int2 m : grid.adjPos4(n)) {
                if(grid.charAt(m) != '.' || dist + 1 >= distances.get(m))
                    continue;
                distances.put(m, dist + 1);
                if(!q.updateDecreased(m))
                    q.enqueue(m);
            }
        }

        return grid.sum(p -> {
            if(grid.charAt(p) != '.')
                return 0;

            int dist = distances.get(p);
            int count = 0;
            for(int i=2; i<=maxLen; i++) {
                for(int j=0; j<i; j++) {
                    for(int r=0; r<4; r++) {
                        int2 n = new int2(j, i - j).rotate90(r).add(p);
                        if(grid.charAt(n) == '.' && distances.get(n) - dist - i >= minCut)
                            count++;
                    }
                }
            }
            return count;
        });
    }
}
