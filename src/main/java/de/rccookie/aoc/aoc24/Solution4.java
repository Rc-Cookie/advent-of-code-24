package de.rccookie.aoc.aoc24;

import de.rccookie.aoc.aoc24.util.FastSolution;

public class Solution4 extends FastSolution {

    private static final int MXS = 'M' ^ 'S';

    private int width, height, tl, tr, bl, br, r, b;

    private void init() {
        width = indexOf('\n', 0);
        height = (chars.length + 1) / (width + 1);
        tl = -width - 2;
        tr = -width;
        bl = width;
        br = width + 2;
        r = 1;
        b = width + 1;
    }

    @Override
    public Object task1() {
        init();

        int count = 0, x, y, p;
        for(x=0; x<width-3; x++) for(y=0; y<3; y++) {
            p = x + (width + 1) * y;
            if(chars[p] == 'X') {
                if(checkMAS(p, r)) count++;
                if(checkMAS(p, br)) count++;
                if(checkMAS(p, b)) count++;
            } else if(chars[p] == 'S') {
                if(checkAMX(p, r)) count++;
                if(checkAMX(p, br)) count++;
                if(checkAMX(p, b)) count++;
            }
        }

        for(x=0; x<width-3; x++) for(y=3; y<height-3; y++) {
            p = x + (width + 1) * y;
            if(chars[p] == 'X') {
                if(checkMAS(p, r)) count++;
                if(checkMAS(p, br)) count++;
                if(checkMAS(p, b)) count++;
                if(checkMAS(p, tr)) count++;
            } else if(chars[p] == 'S') {
                if(checkAMX(p, r)) count++;
                if(checkAMX(p, br)) count++;
                if(checkAMX(p, b)) count++;
                if(checkAMX(p, tr)) count++;
            }
        }

        for(x=0; x<width-3; x++) for(y=height-3; y<height; y++) {
            p = x + (width + 1) * y;
            if(chars[p] == 'X') {
                if(checkMAS(p, r)) count++;
                if(checkMAS(p, tr)) count++;
            } else if(chars[p] == 'S') {
                if(checkAMX(p, r)) count++;
                if(checkAMX(p, tr)) count++;
            }
        }

        for(x=width-3; x<width; x++) for(y=0; y<height-3; y++) {
            p = x + (width + 1) * y;
            if(chars[p] == 'X') {
                if(checkMAS(p, b)) count++;
            } else if(chars[p] == 'S') {
                if(checkAMX(p, b)) count++;
            }
        }

        return count;
    }

    @Override
    public Object task2() {
        init();

        int count = 0;
        for(int x=1; x<width-1; x++) for(int y=1; y<height-1; y++) {
            int p = x + (width+1) * y;
            if(chars[p] == 'A' && (chars[p+tl] ^ chars[p+br]) == MXS && (chars[p+tr] ^ chars[p+bl]) == MXS) count++;
        }
        return count;
    }

    private boolean checkMAS(int p, int step) {
        return chars[p+step] == 'M' && chars[p+2*step] == 'A' && chars[p+3*step] == 'S';
    }

    private boolean checkAMX(int p, int step) {
        return chars[p+step] == 'A' && chars[p+2*step] == 'M' && chars[p+3*step] == 'X';
    }
}
