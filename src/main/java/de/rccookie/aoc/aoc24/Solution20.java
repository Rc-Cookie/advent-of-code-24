package de.rccookie.aoc.aoc24;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.math.int2;

public class Solution20 extends FastSolution {

    @Override
    public Object task1() {
//        int width = eol(0), width1 = width + 1, height = bytes.length / width1;
//
//        int start = indexOf((byte) 'S', width1);
//        int end = indexOf((byte) 'E', width1);
//        bytes[start] = bytes[end] = '.';
//
//        int[] distances = new int[bytes.length];
//        short length = 0;
//
//        int prev = start, cur = start;
//
//        while(cur != end) {
//            distances[cur] = length++;
//            int m;
//            if(bytes[m = cur-1] == '.' && m != prev) {
//                prev = cur;
//                cur = m;
//            }
//            else if(bytes[m = cur+1] == '.' && m != prev) {
//                prev = cur;
//                cur = m;
//            }
//            else if(bytes[m = cur-width1] == '.' && m != prev) {
//                prev = cur;
//                cur = m;
//            }
//            else {
//                prev = cur;
//                cur += width1;
//            }
//        }
//        distances[end] = length;
//
//        int contentEnd = bytes.length - width1;
//        int count = 0;
//        for(int p=width; p<contentEnd; ++p) {
//            if(bytes[p] != '.')
//                continue;
//
////            pV.set(31, 31);
//
////            grid.clearMarked();
////            grid.mark(pV);
//
//            int dist = distances[p];
//
//            int pX = p % width1, pY = p / width1;
//            int lowX = Math.min(pX, maxLen);
//            int lowY = Math.min(pY, maxLen);
//            int highX = Math.min(width - pX - 1, maxLen);
//            int highY = Math.min(height - pY - 1, maxLen);
//            int q;
//
//            if(lowX != maxLen || highX != maxLen || lowY != maxLen || highY != maxLen) {
//
//                int q0 = p - width1;
//                for(int dy=2; dy<=lowY; dy++) { // top
//                    q0 -= width1;
//                    int stop1 = Math.min(maxLen - dy, lowX);
//                    for(int dx=1; dx<=stop1; dx++) // left
//                        if(bytes[q = q0 - dx] == '.' && distances[q] - dist - dx - dy >= minCut) ++count;
//                    int stop2 = Math.min(maxLen - dy, highX);
//                    for(int dx=1; dx<=stop2; dx++) // right
//                        if(bytes[q = q0 + dx] == '.' && distances[q] - dist - dx - dy >= minCut) ++count;
//                }
//                q0 = p + width1;
//                for(int dy=2; dy<=highY; dy++) { // bottom
//                    q0 += width1;
//                    int stop1 = Math.min(maxLen - dy, lowX);
//                    for(int dx=1; dx<=stop1; dx++) // left
//                        if(bytes[q = q0 - dx] == '.' && distances[q] - dist - dx - dy >= minCut) ++count;
//                    int stop2 = Math.min(maxLen - dy, highX);
//                    for(int dx=1; dx<=stop2; dx++) // right
//                        if(bytes[q = q0 + dx] == '.' && distances[q] - dist - dx - dy >= minCut) ++count;
//                }
//
//                if(lowY != 0) { // top
//                    q0 = p - width1;
//                    for(int dx=2; dx<lowX; dx++) // left
//                        if(bytes[q = q0-dx] == '.' && distances[q] - dist - dx - 1 >= minCut) ++count;
//                    for(int dx=2; dx<highX; dx++) // right
//                        if(bytes[q = q0+dx] == '.' && distances[q] - dist - dx - 1 >= minCut) ++count;
//                }
//                if(highY != 0) { // bottom
//                    q0 = p + width1;
//                    for(int dx=2; dx<lowX; dx++) // left
//                        if(bytes[q = q0-dx] == '.' && distances[q] - dist - dx - 1 >= minCut) ++count;
//                    for(int dx=2; dx<highX; dx++) // right
//                        if(bytes[q = q0+dx] == '.' && distances[q] - dist - dx - 1 >= minCut) ++count;
//                }
//
//                for(int dx=2; dx<=lowX; dx++) // left
//                    if(bytes[q = p-dx] == '.' && distances[q] - dist - dx >= minCut) ++count;
//                for(int dx=2; dx<=highX; dx++) // right
//                    if(bytes[q = p+dx] == '.' && distances[q] - dist - dx >= minCut) ++count;
//
//                q = p - width1;
//                for(int dy=2; dy<=lowY; dy++) // top
//                    if(bytes[q -= width1] == '.' && distances[q] - dist - dy >= minCut) ++count;
//                q = p + width1;
//                for(int dy=2; dy<=highY; dy++) // bottom
//                    if(bytes[q += width1] == '.' && distances[q] - dist - dy >= minCut) ++count;
//            }
//            else {
//                int q0 = p - width1, q1 = p + width1;
//                for(int dy=2; dy<=maxLen; dy++) {
//                    q0 -= width1;
//                    q1 += width1;
//                    for(int dx=1; dx<=maxLen - dy; dx++) {
//                        if(bytes[q = q0 - dx] == '.' && distances[q] - dist - dx - dy >= minCut) ++count;
//                        if(bytes[q = q1 - dx] == '.' && distances[q] - dist - dx - dy >= minCut) ++count;
//                        if(bytes[q = q0 + dx] == '.' && distances[q] - dist - dx - dy >= minCut) ++count;
//                        if(bytes[q = q1 + dx] == '.' && distances[q] - dist - dx - dy >= minCut) ++count;
//                    }
//                }
//                q0 = p - width1;
//                q1 = p + width1;
//                for(int dx=2; dx<maxLen; dx++) {
//                    if(bytes[q = q0 - dx] == '.' && distances[q] - dist - dx - 1 >= minCut) ++count;
//                    if(bytes[q = q0 + dx] == '.' && distances[q] - dist - dx - 1 >= minCut) ++count;
//                    if(bytes[q = q1 - dx] == '.' && distances[q] - dist - dx - 1 >= minCut) ++count;
//                    if(bytes[q = q1 + dx] == '.' && distances[q] - dist - dx - 1 >= minCut) ++count;
//                }
//                for(int d=2; d<=maxLen; d++) {
//                    if(bytes[q = p-d] == '.' && distances[q] - dist - d >= minCut) ++count;
//                    if(bytes[q = p+d] == '.' && distances[q] - dist - d >= minCut) ++count;
//                    if(bytes[q0 -= width1] == '.' && distances[q0] - dist - d >= minCut) ++count;
//                    if(bytes[q1 += width1] == '.' && distances[q1] - dist - d >= minCut) ++count;
//                }
//            }
//
////            System.out.println(grid);
//        }
//        return count;
        return countCuts(2, 100);
    }

    @Override
    public Object task2() {
        return countCuts(20, 100);
    }

    public long countCuts(int maxLen, int minCut) {
        int width = eol(0), width1 = width + 1, height = bytes.length / width1;

        int start = indexOf((byte) 'S', width1);
        int end = indexOf((byte) 'E', width1);
        bytes[start] = bytes[end] = '.';

        int[] distances = new int[bytes.length];

        int length = 0;
        int prev = start, cur = start;

        while(cur != end) {
            distances[cur] = length++;
            int m;
            if(bytes[m = cur-1] == '.' && m != prev) {
                prev = cur;
                cur = m;
            }
            else if(bytes[m = cur+1] == '.' && m != prev) {
                prev = cur;
                cur = m;
            }
            else if(bytes[m = cur-width1] == '.' && m != prev) {
                prev = cur;
                cur = m;
            }
            else {
                prev = cur;
                cur += width1;
            }
        }
        distances[end] = length;

        int contentEnd = bytes.length - width1;
        int count = 0;
        for(int p=width; p<contentEnd; ++p) {
            if(bytes[p] != '.')
                continue;

//            pV.set(31, 31);

//            grid.clearMarked();
//            grid.mark(pV);

            int dist = distances[p];

            int pX = p % width1, pY = p / width1;
            int lowX = Math.min(pX, maxLen);
            int lowY = Math.min(pY, maxLen);
            int highX = Math.min(width - pX - 1, maxLen);
            int highY = Math.min(height - pY - 1, maxLen);
            int q;

            if(lowX < maxLen || highX < maxLen || lowY < maxLen || highY < maxLen) {

                int q0 = p - width1;
                for(int dy=2; dy<=lowY; dy++) { // top
                    q0 -= width1;
                    int stop1 = Math.min(maxLen - dy, lowX);
                    for(int dx=1; dx<=stop1; dx++) // left
                        if(distances[q0 - dx] - dist - dx - dy >= minCut) ++count;
                    int stop2 = Math.min(maxLen - dy, highX);
                    for(int dx=1; dx<=stop2; dx++) // right
                        if(distances[q0 + dx] - dist - dx - dy >= minCut) ++count;
                }
                q0 = p + width1;
                for(int dy=2; dy<=highY; dy++) { // bottom
                    q0 += width1;
                    int stop1 = Math.min(maxLen - dy, lowX);
                    for(int dx=1; dx<=stop1; dx++) // left
                        if(distances[q0 - dx] - dist - dx - dy >= minCut) ++count;
                    int stop2 = Math.min(maxLen - dy, highX);
                    for(int dx=1; dx<=stop2; dx++) // right
                        if(distances[q0 + dx] - dist - dx - dy >= minCut) ++count;
                }

                if(lowY != 0) { // top
                    q0 = p - width1;
                    for(int dx=2; dx<lowX; dx++) // left
                        if(distances[q0 - dx] - dist - dx - 1 >= minCut) ++count;
                    for(int dx=2; dx<highX; dx++) // right
                        if(distances[q0 + dx] - dist - dx - 1 >= minCut) ++count;
                }
                if(highY != 0) { // bottom
                    q0 = p + width1;
                    for(int dx=2; dx<lowX; dx++) // left
                        if(distances[q0 - dx] - dist - dx - 1 >= minCut) ++count;
                    for(int dx=2; dx<highX; dx++) // right
                        if(distances[q0 + dx] - dist - dx - 1 >= minCut) ++count;
                }

                for(int dx=2; dx<=lowX; dx++) // left
                    if(distances[p - dx] - dist - dx >= minCut) ++count;
                for(int dx=2; dx<=highX; dx++) // right
                    if(distances[p + dx] - dist - dx >= minCut) ++count;

                q = p - width1;
                for(int dy=2; dy<=lowY; dy++) // top
                    if(distances[q -= width1] - dist - dy >= minCut) ++count;
                q = p + width1;
                for(int dy=2; dy<=highY; dy++) // bottom
                    if(distances[q += width1] - dist - dy >= minCut) ++count;
            }
            else {
                int q0 = p - width1, q1 = p + width1;
                for(int dy=2; dy<=maxLen; dy++) {
                    q0 -= width1;
                    q1 += width1;
                    for(int dx=1; dx<=maxLen - dy; dx++) {
                        if(distances[q0 - dx] - dist - dx - dy >= minCut) ++count;
                        if(distances[q1 - dx] - dist - dx - dy >= minCut) ++count;
                        if(distances[q0 + dx] - dist - dx - dy >= minCut) ++count;
                        if(distances[q1 + dx] - dist - dx - dy >= minCut) ++count;
                    }
                }
                q0 = p - width1;
                q1 = p + width1;
                for(int dx=2; dx<maxLen; dx++) {
                    if(distances[q0 - dx] - dist - dx - 1 >= minCut) ++count;
                    if(distances[q0 + dx] - dist - dx - 1 >= minCut) ++count;
                    if(distances[q1 - dx] - dist - dx - 1 >= minCut) ++count;
                    if(distances[q1 + dx] - dist - dx - 1 >= minCut) ++count;
                }
                for(int d=2; d<=maxLen; d++) {
                    if(distances[p - d] - dist - d >= minCut) ++count;
                    if(distances[p + d] - dist - d >= minCut) ++count;
                    if(distances[q0 -= width1] - dist - d >= minCut) ++count;
                    if(distances[q1 += width1] - dist - d >= minCut) ++count;
                }
            }

//            System.out.println(grid);
        }
        return count;
    }

    private int check(int2 pV, int dx, int dy, int cutLen, int pDist, int[] distances, int minCut) {
        int2 q = pV.added(dx, dy);
//        if(grid.inside(q))
//            grid.mark(q);
        return grid.charAt(q) == '.' && distances[q.x() + (size.x()+1) * q.y()] - pDist - cutLen >= minCut ? 1 : 0;
    }

    private int checkUnsafe(int p, int dx, int dy, int cutLen, int width1, int pDist, int[] distances, int minCut) {
        int q = p + dx + width1 * dy;
//        int2 q = pV.added(dx, dy);
////        grid.mark(q);
        return bytes[q] == '.' && distances[q] - pDist - cutLen >= minCut ? 1 : 0;
//        return grid.charAtRaw(q) == '.' && distances[q.x() + (size.x()+1) * q.y()] - pDist - cutLen >= minCut ? 1 : 0;
    }

    private int checkUnsafe(int q, int cutLen, int pDist, int[] distances, int minCut) {
////        grid.mark(q);
        return bytes[q] == '.' && distances[q] - pDist - cutLen >= minCut ? 1 : 0;
//        return grid.charAtRaw(q) == '.' && distances[q.x() + (size.x()+1) * q.y()] - pDist - cutLen >= minCut ? 1 : 0;
    }
}
