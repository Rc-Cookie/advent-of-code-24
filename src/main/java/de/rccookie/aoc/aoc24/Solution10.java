package de.rccookie.aoc.aoc24;

import java.util.Arrays;

import de.rccookie.aoc.aoc24.util.FastSolution;

public class Solution10 extends FastSolution {

    @Override
    public Object task1() {
        int w = size.x(), h = size.y(), w1 = w+1;
        long[] dejavu = new long[(chars.length+63)/64];
        int count = 0;
        for(int y=0; y<h; y++) for(int x=0; x<w; x++) {
            Arrays.fill(dejavu, 0);
            count += searchRecursive(x, y, x + w1 * y, w, h, w1, '0', dejavu);
        }
        return count;
    }

    @Override
    public Object task2() {
        int w = size.x(), h = size.y(), w1 = w+1;
        int count = 0;
        for(int y=0; y<h; y++) for(int x=0; x<w; x++)
            count += searchRecursive(x, y, x + w1 * y, w, h, w1, '0');
        return count;
    }


    private int searchRecursive(int x, int y, int i, int w, int h, int w1, int expected, long[] dejavu) {
        if(chars[i] != expected || (dejavu != null && gbi(dejavu, i)))
            return 0;
        if(dejavu != null)
            sbi(dejavu, i);
        if(expected == '9')
            return 1;
        return (x < w-1 ? searchRecursive(x+1, y, i+1, w, h, w1, expected + 1, dejavu) : 0) +
                (x > 0 ? searchRecursive(x-1, y, i-1, w, h, w1, expected + 1, dejavu) : 0) +
                (y < h-1 ? searchRecursive(x, y+1, i+w1, w, h, w1, expected + 1, dejavu) : 0) +
                (y > 0 ? searchRecursive(x, y-1, i-w1, w, h, w1, expected + 1, dejavu) : 0);
    }

    private int searchRecursive(int x, int y, int i, int w, int h, int w1, int expected) {
        if(chars[i] != expected)
            return 0;
        if(expected == '9')
            return 1;
        return (x < w-1 ? searchRecursive(x+1, y, i+1, w, h, w1, expected + 1) : 0) +
                (x > 0 ? searchRecursive(x-1, y, i-1, w, h, w1, expected + 1) : 0) +
                (y < h-1 ? searchRecursive(x, y+1, i+w1, w, h, w1, expected + 1) : 0) +
                (y > 0 ? searchRecursive(x, y-1, i-w1, w, h, w1, expected + 1) : 0);
    }
}
