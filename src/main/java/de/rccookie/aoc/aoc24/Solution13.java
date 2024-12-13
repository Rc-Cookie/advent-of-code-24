package de.rccookie.aoc.aoc24;

import de.rccookie.aoc.aoc24.util.FastSolution;

public class Solution13 extends FastSolution {
    @Override
    public Object task1() {
        return countTokens(0);
    }

    @Override
    public Object task2() {
        return countTokens(10000000000000L);
    }

    @SuppressWarnings("DuplicatedCode")
    private Object countTokens(long offset) {
        final char[] chars = this.chars;
        final int len = chars.length;

        int pos = 12;
        char c;
        int ax, ay, bx, by, det;
        long x, y, a, b;

        long total = 0;
        while(pos < len) {
            ax = chars[pos] - '0';
            while((c = chars[++pos]) >= '0' && c <= '9')
                ax = 10 * ax + c - '0';
            pos += 4;

            ay = chars[pos] - '0';
            while((c = chars[++pos]) >= '0' && c <= '9')
                ay = 10 * ay + c - '0';
            pos += 13;

            bx = chars[pos] - '0';
            while((c = chars[++pos]) >= '0' && c <= '9')
                bx = 10 * bx + c - '0';
            pos += 4;

            by = chars[pos] - '0';
            while((c = chars[++pos]) >= '0' && c <= '9')
                by = 10 * by + c - '0';
            pos += 10;

            x = chars[pos] - '0';
            while((c = chars[++pos]) >= '0' && c <= '9')
                x = 10L * x + c - '0';
            x += offset;
            pos += 4;

            y = chars[pos] - '0';
            while((c = chars[++pos]) >= '0' && c <= '9')
                y = 10L * y + c - '0';
            y += offset;
            pos += 14;


            det = ax * by - ay * bx;
            a = x * by / det - y * bx / det;
            b = y * ax / det - x * ay / det;
            if(a * ax + b * bx != x || a * ay + b * by != y)
                continue;
            total += 3L * a + b;
        }

        return total;
    }
}
