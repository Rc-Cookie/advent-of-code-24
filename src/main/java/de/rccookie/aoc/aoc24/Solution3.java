package de.rccookie.aoc.aoc24;

public class Solution3 extends FastSolution {

    @Override
    public Object task1() {

        long total = 0;
        int i = 0;
        while(true) {
            i = input.indexOf("mul(", i) + 4;
            if(i < 4 || i >= chars.length || chars[i] < '0' || chars[i] > '9')
                return total;

            int a = 0;
            do {
                a = 10 * a + chars[i++] - '0';
                if(i == chars.length)
                    return total;
            } while(chars[i] >= '0' && chars[i] <= '9');
            if(chars[i] != ',' || chars[++i] < '0' || chars[i] > '9')
                continue;

            int b = 0;
            do {
                b = 10 * b + chars[i++] - '0';
                if(i == chars.length)
                    return total;
            } while(chars[i] >= '0' && chars[i] <= '9');
            if(chars[i++] != ')') continue;

            total += (long) a * b;
        }
    }

    @Override
    public Object task2() {

        long total = 0;
        int i = 0, m, d;
        while(true) {
            m = input.indexOf("mul(", i) + 4;
            if(m < 4 || m >= chars.length || chars[m] < '0' || chars[m] > '9')
                return total;

            d = input.indexOf("don't()", i, m);
            if(d >= 0) {
                i = input.indexOf("do()", d + 7) + 4;
                if(i < 4)
                    return total;
                continue;
            }

            i = m;
            int a = 0;
            do {
                a = 10 * a + chars[i++] - '0';
                if(i == chars.length)
                    return total;
            } while(chars[i] >= '0' && chars[i] <= '9');
            if(chars[i] != ',' || chars[++i] < '0' || chars[i] > '9')
                continue;

            int b = 0;
            do {
                b = 10 * b + chars[i++] - '0';
                if(i == chars.length)
                    return total;
            } while(chars[i] >= '0' && chars[i] <= '9');
            if(chars[i++] != ')') continue;

            total += (long) a * b;
        }
    }
}
