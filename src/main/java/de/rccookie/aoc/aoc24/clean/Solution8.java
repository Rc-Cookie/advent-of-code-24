package de.rccookie.aoc.aoc24.clean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import de.rccookie.aoc.aoc24.FastSolution;
import de.rccookie.math.int2;

public class Solution8 extends FastSolution {
    @Override
    public Object task1() {
        return countAntiNodes(this::markInDirection);
    }

    @Override
    public Object task2() {
        return countAntiNodes(this::markAllInDirection);
    }

    private int countAntiNodes(BiConsumer<int2, int2> marker) {
        Map<Character, List<int2>> antennas = new HashMap<>();
        for(int2 pos : size)
            if(charAt(pos) != '.')
                antennas.computeIfAbsent(charAt(pos), $ -> new ArrayList<>()).add(pos);

        for(List<int2> locations : antennas.values()) {
            for(int2 a : locations) for(int2 b : locations) {
                if(a != b) {
                    int2 ab = int2.between(a,b);
                    marker.accept(a, ab.negated());
                    marker.accept(b, ab);
                }
            }
        }

        return grid.countMarked();
    }

    private void markInDirection(int2 pos, int2 offset) {
        pos = pos.added(offset);
        if(grid.inside(pos))
            grid.mark(pos);
    }

    private void markAllInDirection(int2 pos, int2 offset) {
        for(; grid.inside(pos); pos = pos.added(offset))
            grid.mark(pos);
    }
}
