package de.rccookie.aoc.aoc24;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.math.int2;

public class Solution14 extends FastSolution {

    public static final int WIDTH = 101, HEIGHT = 103;

    @SuppressWarnings("AssignmentUsedAsCondition")
    @Override
    public Object task1() {
        int len = chars.length - 1;

        int hw = WIDTH / 2, hh = HEIGHT / 2;
        int tl = 0, tr = 0, bl = 0, br = 0;

        int pos = 2;
        char c;
        boolean neg;

        while(pos < len) {
            int x0 = chars[pos] - '0';
            while((c = chars[++pos]) >= '0' && c <= '9')
                x0 = 10 * x0 + c - '0';

            int y0 = chars[++pos] - '0';
            while((c = chars[++pos]) >= '0' && c <= '9')
                y0 = 10 * y0 + c - '0';

            pos += 3;

            if(neg = (chars[pos] == '-'))
                ++pos;
            int dx = chars[pos] - '0';
            while((c = chars[++pos]) >= '0' && c <= '9')
                dx = 10 * dx + c - '0';
            if(neg)
                dx = WIDTH - dx;

            if(neg = (chars[++pos] == '-'))
                ++pos;
            int dy = chars[pos] - '0';
            while((c = chars[++pos]) >= '0' && c <= '9')
                dy = 10 * dy + c - '0';
            if(neg)
                dy = HEIGHT - dy;
            pos += 3;

            int x = (x0 + 100 * dx) % WIDTH;
            int y = (y0 + 100 * dy) % HEIGHT;

            if(x < hw) {
                if(y < hh) tl++;
                else if(y > hh) bl++;
            }
            else if(x > hw) {
                if(y < hh) tr++;
                else if(y > hh) br++;
            }
        }

        return tl * tr * bl * br;
    }

    @SuppressWarnings("AssignmentUsedAsCondition")
    @Override
    public Object task2() {

        int[] x0s = new int[(chars.length+11) / 12];
        int[] y0s = new int[(chars.length+11) / 12];
        int[] dxs = new int[(chars.length+11) / 12];
        int[] dys = new int[(chars.length+11) / 12];

        int pos = 2;
        char c;
        boolean neg;

        int count = 0;

        for(int len=chars.length-1; pos < len; count++) {
            int x0 = chars[pos] - '0';
            while((c = chars[++pos]) >= '0' && c <= '9')
                x0 = 10 * x0 + c - '0';
            x0s[count] = x0;

            int y0 = chars[++pos] - '0';
            while((c = chars[++pos]) >= '0' && c <= '9')
                y0 = 10 * y0 + c - '0';
            y0s[count] = y0;

            pos += 3;

            if(neg = (chars[pos] == '-'))
                ++pos;
            int dx = chars[pos] - '0';
            while((c = chars[++pos]) >= '0' && c <= '9')
                dx = 10 * dx + c - '0';
            dxs[count] = neg ? WIDTH - dx : dx;

            if(neg = (chars[++pos] == '-'))
                ++pos;
            int dy = chars[pos] - '0';
            while((c = chars[++pos]) >= '0' && c <= '9')
                dy = 10 * dy + c - '0';
            dys[count] = neg ? HEIGHT - dy: dy;

            pos += 3;
        }

        int minX = 0, minY = 0;
        float minVarX = Float.POSITIVE_INFINITY, minVarY = Float.POSITIVE_INFINITY;

        for(int i=0, stop=Math.max(WIDTH, HEIGHT); i<stop; i++) {
            int avgX = 0, avgY = 0, avgX2 = 0, avgY2 = 0;

            for(int j=0; j<count; j++) {
                int x = (x0s[j] + i * dxs[j]) % WIDTH;
                int y = (y0s[j] + i * dys[j]) % HEIGHT;
                avgX += x;
                avgY += y;
                avgX2 += x*x;
                avgY2 += y*y;
            }

            float avgXF = (float) avgX / count;
            float avgYF = (float) avgY / count;
            float varX = (float) avgX2 / count - avgXF*avgXF;
            if(varX < 0) varX = -varX;
            float varY = (float) avgY2 / count - avgYF*avgYF;
            if(varY < 0) varY = -varY;

            if(varX < minVarX) {
                minX = i;
                minVarX = varX;
            }
            if(varY < minVarY) {
                minY = i;
                minVarY = varY;
            }
        }

        int2 mn = extendedEuclideanAlgorithm(HEIGHT, WIDTH);
        return Math.floorMod(mn.x() * HEIGHT * minX + mn.y() * WIDTH * minY, WIDTH * HEIGHT);
    }

    public static int2 extendedEuclideanAlgorithm(int a, int b) {
        int oldR = a, r = b;
        int oldS = 1, s = 0;
        int oldT = 0, t = 1;

        while(r != 0) {
            int q = oldR / r;

            int tmp = r;
            r = oldR - q * r;
            oldR = tmp;

            tmp = s;
            s = oldS - q * s;
            oldS = tmp;

            tmp = t;
            t = oldT - q * t;
            oldT = tmp;
        }

        return new int2(oldS,oldT);
    }
}
