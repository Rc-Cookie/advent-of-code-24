package de.rccookie.aoc.aoc24.clean;

import de.rccookie.aoc.aoc24.FastSolution;
import de.rccookie.math.int2;

public class Solution10 extends FastSolution {

    @Override
    public Object task1() {
        return grid.sum(p -> {
            grid.clearMarked();
            return searchRecursive(p, '0', true);
        });
    }

    @Override
    public Object task2() {
        return grid.sum(p -> searchRecursive(p, '0', false));
    }


    private int searchRecursive(int2 pos, char expected, boolean distinct) {
        if(grid.charAt(pos) != expected || (distinct && !grid.mark(pos)))
            return 0;
        if(expected == '9')
            return 1;
        return searchRecursive(pos.added(0, 1), (char) (expected + 1), distinct) +
                searchRecursive(pos.added(0, -1), (char) (expected + 1), distinct) +
                searchRecursive(pos.added(1, 0), (char) (expected + 1), distinct) +
                searchRecursive(pos.added(-1, 0), (char) (expected + 1), distinct);
    }
}
