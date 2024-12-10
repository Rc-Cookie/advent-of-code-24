package de.rccookie.aoc.aoc24.clean;

import java.util.HashSet;
import java.util.Set;

import de.rccookie.aoc.aoc24.FastSolution;
import de.rccookie.math.int2;

public class Solution10 extends FastSolution {

    @Override
    public Object task1() {
        int count = 0;
        for(int2 p : size)
            count += searchRecursive(p, '0', new HashSet<>());
        return count;
    }

    @Override
    public Object task2() {
        int count = 0;
        for(int2 p : size)
            count += searchRecursive(p, '0', null);
        return count;
    }


    private int searchRecursive(int2 pos, char expected, Set<int2> dejavu) {
        if(!(pos.geq(int2.zero) && pos.less(size)) || charAt(pos) != expected || (dejavu != null && !dejavu.add(pos)))
            return 0;
        if(expected == '9')
            return 1;
        return searchRecursive(pos.added(0, 1), (char) (expected + 1), dejavu) +
                searchRecursive(pos.added(0, -1), (char) (expected + 1), dejavu) +
                searchRecursive(pos.added(1, 0), (char) (expected + 1), dejavu) +
                searchRecursive(pos.added(-1, 0), (char) (expected + 1), dejavu);
    }
}
