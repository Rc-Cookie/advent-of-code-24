package de.rccookie.aoc.aoc24;

import de.rccookie.aoc.aoc24.util.FastSolution;

public class Solution20 extends FastSolution {

    @Override
    public Object task1() {
        int width = eol(0), width1 = width + 1, width1x2 = width1 + width1;

        // Allocate here so it can go onto the stack
        int[] distances = new int[bytes.length];
        tracePath(distances, width1);

        int endMWidth1 = bytes.length - width1;
        int endMWidth1x2 = endMWidth1 - width1;
        int count = 0;
        for(int p=width1; p<endMWidth1; ++p) {
            if(bytes[p] != '.')
                continue;

            int dist = distances[p];
            if(distances[p - 2] - dist - 2 >= 100) ++count;
            if(distances[p + 2] - dist - 2 >= 100) ++count;
            if(p >= width1x2 && distances[p - width1x2] - dist - 2 >= 100) ++count;
            if(p < endMWidth1x2 && distances[p + width1x2] - dist - 2 >= 100) ++count;
        }
        return count;
    }

    @Override
    public Object task2() {
        return countCuts(20, 100);
    }

    private void tracePath(int[] distances, int width1) {

        int start = indexOf((byte) 'S', width1);
        int end = indexOf((byte) 'E', width1);
        bytes[start] = bytes[end] = '.';

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
    }

    public long countCuts(int maxLen, int minCut) {
        int width = eol(0), width1 = width + 1, height = bytes.length / width1;

        // Allocate here so it can go onto the stack
        int[] distances = new int[bytes.length];
        tracePath(distances, width1);

        int contentEnd = bytes.length - width1;
        int count = 0;
        for(int p=width; p<contentEnd; ++p) {
            if(bytes[p] != '.')
                continue;

            int dist = distances[p];

            int pX = p % width1, pY = p / width1;
            int lowX = Math.min(pX, maxLen);
            int lowY = Math.min(pY, maxLen);
            int highX = Math.min(width - pX - 1, maxLen);
            int highY = Math.min(height - pY - 1, maxLen);
            int q;

            if(lowX < maxLen || highX < maxLen || lowY < maxLen || highY < maxLen) {

                // We are near a boundary - separate loops for every direction

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

                // No edge cases - share loops for multiple directions

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
        }
        return count;
    }
}
