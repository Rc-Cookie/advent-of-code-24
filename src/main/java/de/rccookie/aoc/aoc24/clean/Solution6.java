package de.rccookie.aoc.aoc24.clean;

import de.rccookie.aoc.aoc24.FastSolution;
import de.rccookie.math.constInt2;
import de.rccookie.math.int2;

public class Solution6 extends FastSolution {

    @Override
    public Object task1() {
        char[][] area = charTable('b');
        int2 pos = findStart(area);

        int2 dir = new int2(0,-1);
        area[pos.y()][pos.x()] = 'X';
        while(true) {
            pos.add(dir);
            if(area[pos.y()][pos.x()] == 'b')
                break;
            if(area[pos.y()][pos.x()] == '#') {
                pos.sub(dir);
                dir.rotate90(-1);
            }
            else area[pos.y()][pos.x()] = 'X';
        }

        int count = 0;
        for(char[] cs : area) for(char c : cs)
            if(c == 'X') count++;
        return count;
    }


    @Override
    public Object task2() {
        char[][] area = charTable('b');
        constInt2 start = findStart(area);

        int count = 0;
        for(int2 o : new int2(area[0].length - 2, area.length - 2)) {
            o.add(1);
            if(area[o.y()][o.x()] != '.') continue;
            area[o.y()][o.x()] = '#';

            int2 pos = start.clone();
            int dir = 0;
            boolean[][][] dejavu = new boolean[area.length][area[0].length][4];
            while(true) {
                if(dejavu[pos.y()][pos.x()][dir]) {
                    count++;
                    break;
                }
                dejavu[pos.y()][pos.x()][dir] = true;

                pos.add(new int2(0, -1).rotate90(dir));
                if(area[pos.y()][pos.x()] == 'b')
                    break;
                if(area[pos.y()][pos.x()] == '#') {
                    pos.sub(new int2(0, -1).rotate90(dir));
                    dir = (dir - 1) & 3;
                }
            }

            area[o.y()][o.x()] = '.';
        }
        return count;
    }


    private int2 findStart(char[][] area) {
        int2 pos = null;
        for(int2 p : new int2(area[0].length, area.length)) {
            if(area[p.y()][p.x()] == '^') {
                pos = p;
                break;
            }
        }
        assert pos != null;
        return pos;
    }
}
