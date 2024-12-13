package de.rccookie.aoc.aoc24;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.aoc.aoc24.util.IntArrayList;

public class Solution8 extends FastSolution {

    private int vec(int x, int y) {
        return x + (size.x()+1) * y;
    }

    private int x(int vec) {
        return vec % (size.x() + 1);
    }

    private int y(int vec) {
        return vec / (size.x() + 1);
    }

    private long[] antiNodes;
    private int count;

    @Override
    public Object task1() {

        count = 0;
        antiNodes = new long[(chars.length + 63) / 64];
        for(IntArrayList locations : antennas()) {
            if(locations == null) continue;
            locations.forEachInt(a -> locations.forEachInt(b -> {
                if(a != b) {
                    int ax = x(a), ay = y(a), bx = x(b), by = y(b);
                    int abx = bx - ax, aby = by - ay;
                    addIfInBounds(ax - abx, ay - aby);
                    addIfInBounds(bx + abx, by + aby);
                }
            }));
        }

        return count;
    }

    @Override
    public Object task2() {

        count = 0;
        antiNodes = new long[(chars.length + 63) / 64];
        for(IntArrayList locations : antennas()) {
            if(locations == null) continue;
            locations.forEachInt(a -> locations.forEachInt(b -> {
                if(a != b) {
                    int ax = x(a), ay = y(a), bx = x(b), by = y(b);
                    int abx = bx - ax, aby = by - ay;
                    addAllIfInBounds(ax, ay, -abx, -aby);
                    addAllIfInBounds(bx, by, abx, aby);
                }
            }));
        }

        return count;
    }

    private IntArrayList[] antennas() {
        IntArrayList[] antennas = new IntArrayList[128];
        for(int i=0; i<chars.length; i++) {
            if(chars[i] != '.' && chars[i] != '\n') {
                if(antennas[chars[i]] == null)
                    antennas[chars[i]] = new IntArrayList();
                antennas[chars[i]].add(i);
            }
        }
        return antennas;
    }

    private void addIfInBounds(int x, int y) {
        if(x >= 0 && y >= 0 && x < size.x() && y < size.y()) {
            int v = vec(x,y);
            if(!gbi(antiNodes, v)) {
                sbi(antiNodes, v);
                count++;
            }
        }
    }

    private void addAllIfInBounds(int x, int y, int dx, int dy) {
        for(; x >= 0 && y >= 0 && x < size.x() && y < size.y(); x += dx, y += dy) {
            int v = vec(x,y);
            if(!gbi(antiNodes, v)) {
                sbi(antiNodes, v);
                count++;
            }
        }
    }
}
