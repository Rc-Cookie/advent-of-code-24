package de.rccookie.aoc.aoc24.clean;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.math.int2;

public class Solution6 extends FastSolution {

    @Override
    public Object task1() {
        markPath();
        return grid.countMarked();
    }


    @Override
    public Object task2() {

        markPath();
        int2 start = grid.find('^');

        return grid.count(o -> {
            if(!grid.marked(o)) return false;
            grid.set(o, '#');

            int2 pos = start.clone();
            int dir = 0;
            boolean[][][] dejavu = new boolean[grid.height][grid.width][4];
            while(true) {
                if(dejavu[pos.y()][pos.x()][dir]) {
                    grid.set(o, '.');
                    return true;
                }
                dejavu[pos.y()][pos.x()][dir] = true;

                pos.add(new int2(0, -1).rotate90(dir));
                if(grid.outside(pos)) {
                    grid.set(o, '.');
                    return false;
                }
                if(grid.charAt(pos) == '#') {
                    pos.sub(new int2(0, -1).rotate90(dir));
                    dir = (dir - 1) & 3;
                }
            }

        });
    }

    private void markPath() {
        grid.clearMarked();
        for(int2 pos = grid.find('^'), dir = new int2(0,-1); grid.inside(pos); pos.add(dir)) {
            if(grid.charAtRaw(pos) == '#') {
                pos.sub(dir);
                dir.rotate90(-1);
            }
            else grid.mark(pos);
        }
    }
}
