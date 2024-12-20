package de.rccookie.aoc.aoc24;

import java.util.Arrays;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.aoc.aoc24.util.IntArrayDeque;
import de.rccookie.aoc.aoc24.util.LookupIntHeap;

public class Solution16 extends FastSolution {

    @Override
    public Object task1() {

        int realWidth1 = eol(0) + 1;
        int realHeight = (chars.length+1) / realWidth1;

        assert realWidth1 % 2 == 0;
        int width1 = realWidth1 >>> 1;
        int len = width1 * realHeight;

        int start = ((realHeight-2) * realWidth1 + 1) >>> 1;
        int end = (2 * realWidth1 - 3) >>> 1;

        LookupIntHeap q = new LookupIntHeap(len);

        int[] distances = q.cost;
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;

        q.enqueue(start);

        int n, n0, realN, m, dist, mDist;
        while(q.size != 0) {
            n = q.dequeue();
            mDist = (dist = distances[n]) + 2;

            if(n % realWidth1 >= width1) {
                if(n == end)
                    return dist;

                realN = (n << 1) + 1;

                if(chars[realN-1] != '#' && mDist < distances[m = n-1])
                    updateDist(q, m, mDist);
                if(chars[realN+1] != '#' && mDist < distances[m = n+1])
                    updateDist(q, m, mDist);
                if((chars[realN-realWidth1] != '#' || chars[realN+realWidth1] != '#') && (mDist = dist+1000) < distances[m = n-width1])
                    updateDist(q, m, mDist);
            }
            else {
                n0 = n + width1;
                if(n0 == end)
                    return dist;

                realN = (n0 << 1) + 1;

                if(chars[realN-realWidth1] != '#' && mDist < distances[m = n-realWidth1])
                    updateDist(q, m, mDist);
                if(chars[realN+realWidth1] != '#' && mDist < distances[m = n+realWidth1])
                    updateDist(q, m, mDist);
                if((chars[realN-1] != '#' || chars[realN+1] != '#') && (mDist = dist+1000) < distances[m = n0])
                    updateDist(q, m, mDist);
            }
        }

        throw new AssertionError("No path found");
    }

    @Override
    public Object task2() {

        int realWidth1 = eol(0) + 1;
        int realHeight = (chars.length+1) / realWidth1;

        assert realWidth1 % 2 == 0;
        int width1 = realWidth1 >>> 1;
        int len = width1 * realHeight;

        int start = ((realHeight-2) * realWidth1 + 1) >>> 1;
        int end = (2 * realWidth1 - 3) >>> 1;

        LookupIntHeap q = new LookupIntHeap(len);

        int[] distances = q.cost;
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;

        int[] previous = new int[len * 3];
        previous[3 * start] = previous[3 * end] = previous[3 * (end+width1)] = -1;

        q.enqueue(start);

        int n, n0, realN, dist;
        while(q.size != 0) {
            n = q.dequeue();

            if((dist = distances[n]) > distances[end])
                return distinctCellsOnPaths(previous, end, width1, realWidth1);

            if(n % realWidth1 >= width1) {

                realN = (n << 1) + 1;

                if(chars[realN-1] != '#')
                    cmpAndUpdateDistAndPrev(n, n - 1, dist + 2, q, previous);
                if(chars[realN+1] != '#')
                    cmpAndUpdateDistAndPrev(n, n + 1, dist + 2, q, previous);
                if((chars[realN-realWidth1] != '#' || chars[realN+realWidth1] != '#'))
                    cmpAndUpdateDistAndPrev(n, n - width1, dist + 1000, q, previous);
            }
            else {
                n0 = n + width1;
                realN = (n0 << 1) + 1;

                if(chars[realN-realWidth1] != '#')
                    cmpAndUpdateDistAndPrev(n, n - realWidth1, dist + 2, q, previous);
                if(chars[realN+realWidth1] != '#')
                    cmpAndUpdateDistAndPrev(n, n + realWidth1, dist + 2, q, previous);
                if(chars[realN-1] != '#' || chars[realN+1] != '#')
                    cmpAndUpdateDistAndPrev(n, n0, dist + 1000, q, previous);
            }
        }

        if(distances[end] == Integer.MAX_VALUE && distances[end - width1] == Integer.MAX_VALUE)
            throw new AssertionError("No path found");

        return distinctCellsOnPaths(previous, end, width1, realWidth1);
    }

    private static void updateDist(LookupIntHeap q, int m, int dist) {
        q.cost[m] = dist;
        if(q.contains(m))
            q.updateDecreased(m);
        else q.enqueue(m);
    }

    private static void cmpAndUpdateDistAndPrev(int n, int m, int mDist, LookupIntHeap q, int[] previous) {
        int d;
        if(mDist < (d = q.cost[m])) {
            int p = 3 * m;
            previous[p] = n;
            previous[p+1] = -1;
            updateDist(q, m, mDist);
        }
        else if(d == mDist) {
            int p = 3 * m;
            if(previous[p+1] < 0) {
                previous[p+1] = n;
                previous[p+2] = -1;
            }
            else previous[p+2] = n;
        }
    }

    private static int distinctCellsOnPaths(int[] previous, int end, int width1, int realWidth1) {
        IntArrayDeque queue = new IntArrayDeque();
        long[] visited = new long[(previous.length+63)/64];
        long[] distinctVisited = new long[visited.length];
        int distinctCount = 0;
        int branchCount = -1;

        queue.enqueue(end);
        queue.enqueue(end - width1);
        sbi(visited, end);

        while(!queue.isEmpty()) {
            int n = queue.dequeue();
            if(!gsbi(distinctVisited, n % realWidth1 < width1 ? n + width1 : n))
                ++distinctCount;

            int ps = 3 * n;
            int p;
            if((p = previous[ps]) == -1)
                continue;
            if(!gsbi(visited, p))
                queue.enqueue(p);

            if((p = previous[ps+1]) == -1)
                continue;
            if(!gsbi(visited, p))
                queue.enqueue(p);

            if((p = previous[ps+2]) == -1) {
                ++branchCount;
                continue;
            }

            if(!gsbi(visited, p))
                queue.enqueue(p);
            branchCount += 2;
        }
        return distinctCount * 2 + branchCount;
    }
}
