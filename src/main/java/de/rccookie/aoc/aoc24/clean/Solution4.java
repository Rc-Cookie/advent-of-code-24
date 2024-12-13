package de.rccookie.aoc.aoc24.clean;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.math.constInt2;

public class Solution4 extends FastSolution {

    @Override
    public Object task1() {
        return grid.sum(pos -> count(grid.adjOffsets8(pos), step -> check("XMAS", pos, step)));
    }

    @Override
    public Object task2() {
        return grid.count(pos -> count(DIAGONALS, dir -> check("MAS", pos.subed(dir), dir)) == 2);
    }

    private boolean check(String word, constInt2 start, constInt2 step) {
        for(int i=0; i<word.length(); i++)
            if(grid.charAt(start.added(step.scaled(i))) != word.charAt(i))
                return false;
        return true;
    }
}
