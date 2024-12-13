package de.rccookie.aoc.aoc24.clean;

import de.rccookie.aoc.aoc24.FastSolution;

public class Solution13 extends FastSolution {
    @Override
    public Object task1() {
        return countTokens(0, 100);
    }

    @Override
    public Object task2() {
        return countTokens(10000000000000L, Long.MAX_VALUE);
    }

    private Object countTokens(long offset, long max) {
        return split("\n\n").mapToLong(s -> {
            int[] vals = parseInts(s);
            int ax = vals[0], ay = vals[1], bx = vals[2], by = vals[3];
            long x = vals[4] + offset, y = vals[5] + offset;

            int det = ax * by - ay * bx;
            if(det == 0)
                return 0;

            long a = x * by / det + y * -bx / det;
            long b = x * -ay / det + y * ax / det;
            if(a * ax + b * bx != x || a * ay + b * by != y)
                return 0;
            if(a < 0 || b < 0 || a > max || b > max)
                return 0;
            return 3 * a + b;
        }).sum();
    }
}
