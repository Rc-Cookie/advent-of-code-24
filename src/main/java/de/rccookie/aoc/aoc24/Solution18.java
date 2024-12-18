package de.rccookie.aoc.aoc24;

import java.util.Arrays;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.aoc.aoc24.util.IntArrayDeque;
import de.rccookie.aoc.aoc24.util.LookupIntHeap;

public class Solution18 extends FastSolution {

    private static final int SIZE = 71;
    private static final int SIZE1 = SIZE + 1;

    @Override
    public Object task1() {

        int cellsWithNL = SIZE * SIZE1;
        int cellsWithPadding = SIZE1 * (SIZE+2);
        int end = cellsWithNL + SIZE1 - 2;

        long[] obstacles = new long[(cellsWithPadding + 62) / 64];
        LookupIntHeap todo = new LookupIntHeap(cellsWithPadding - 1);
        int[] distances = todo.cost;

        load1024Obstacles(obstacles);
        Arrays.fill(distances, Integer.MAX_VALUE);

        todo.enqueue(SIZE1);
        distances[SIZE1] = 0;

        int a;
        while(!todo.isEmpty()) {

            int p = todo.dequeue();
            int dist = distances[p] + 1;
            if(p == end)
                return dist - 1;

            if(!gbi(obstacles, a = p-1) && distances[a] > dist) {
                distances[a] = dist;
                if(todo.contains(a))
                    todo.updateDecreased(a);
                else todo.enqueue(a);
            }
            if(!gbi(obstacles, a = p+1) && distances[a] > dist) {
                distances[a] = dist;
                if(todo.contains(a))
                    todo.updateDecreased(a);
                else todo.enqueue(a);
            }
            if(!gbi(obstacles, a = p-SIZE1) && distances[a] > dist) {
                distances[a] = dist;
                if(todo.contains(a))
                    todo.updateDecreased(a);
                else todo.enqueue(a);
            }
            if(!gbi(obstacles, a = p+SIZE1) && distances[a] > dist) {
                distances[a] = dist;
                if(todo.contains(a))
                    todo.updateDecreased(a);
                else todo.enqueue(a);
            }
        }

        throw new AssertionError();
    }

    @Override
    public Object task2() {

        int cellsWithNL = SIZE * SIZE1;
        int cellsWithPadding = SIZE1 * (SIZE+2);
        int end = cellsWithNL + SIZE1 - 2;

        long[] obstacles = new long[(cellsWithPadding + 62) / 64];
        long[] visited = new long[obstacles.length];

        loadObstacles(obstacles);

        IntArrayDeque todo = new IntArrayDeque();

        search(todo, obstacles, visited, SIZE1);

        int pos = chars.length - 2;
        char c;
        while(true) {
            int pow = 1;
            int o = chars[--pos] - '0' + 1;
            while((c = chars[--pos]) != ',')
                o += (c - '0') * (pow *= 10);

            o *= SIZE1;

            pow = 1;
            o += chars[--pos] - '0';
            while((c = chars[--pos]) != '\n')
                o += (c - '0') * (pow *= 10);

            cbi(obstacles, o);

            if(
                (!gbi(visited, o-1)) &&
                (!gbi(visited, o+1)) &&
                (!gbi(visited, o-SIZE1)) &&
                (!gbi(visited, o+SIZE1))
            ) continue;

            search(todo, obstacles, visited, o);

            if(gbi(visited, end))
                return (o % SIZE1) + "," + (o / SIZE1 - 1);
        }
    }

    private void loadObstacles(long[] obstacles) {
        final int cellsWithPadding = SIZE1 * (SIZE+2);

        for(int i=0, j=SIZE; i<SIZE1; i++, j+=SIZE1) {
            sbi(obstacles, i);
            sbi(obstacles, i + cellsWithPadding - SIZE1);
            sbi(obstacles, j);
        }

        int pos = 0, len = chars.length;
        char c;
        while(pos < len) {
            int x = chars[pos] - '0';
            while((c = chars[++pos]) != ',')
                x = 10 * x + c - '0';

            int y = chars[++pos] - '0';
            while((c = chars[++pos]) != '\n')
                y = 10 * y + c - '0';

            sbi(obstacles, x + SIZE1 * (y+1));
            ++pos;
        }
    }

    private void load1024Obstacles(long[] obstacles) {
        final int cellsWithPadding = SIZE1 * (SIZE+2);

        for(int i=0, j=SIZE; i<SIZE1; i++, j+=SIZE1) {
            sbi(obstacles, i);
            sbi(obstacles, i + cellsWithPadding - SIZE1);
            sbi(obstacles, j);
        }

        int pos = 0;
        char c;
        for(int i=0; i<1024; i++) {
            int x = chars[pos] - '0';
            while((c = chars[++pos]) != ',')
                x = 10 * x + c - '0';

            int y = chars[++pos] - '0';
            while((c = chars[++pos]) != '\n')
                y = 10 * y + c - '0';

            sbi(obstacles, x + SIZE1 * (y+1));
            ++pos;
        }
    }

    private static void search(IntArrayDeque todo, long[] obstacles, long[] visited, int start) {
        todo.enqueue(start);
        sbi(visited, start);

        int a,p,a0;
        long m;
        while(todo.head != todo.tail) {
            p = todo.dequeue();

            if((obstacles[a0 = (a = p-1) >>> 6] & (m = 1L << (a & 63))) == 0 && visited[a0] != (visited[a0] |= m))
                todo.enqueue(a);
            if((obstacles[a0 = (a = p+1) >>> 6] & (m = m << 2 | m >>> 62)) == 0 && visited[a0] != (visited[a0] |= m))
                todo.enqueue(a);
            if((obstacles[a0 = (a = p-SIZE1) >>> 6] & (m = 1L << (a & 63))) == 0 && visited[a0] != (visited[a0] |= m))
                todo.enqueue(a);
            if((obstacles[a0 = (a = p+SIZE1) >>> 6] & (m = 1L << (a & 63))) == 0 && visited[a0] != (visited[a0] |= m))
                todo.enqueue(a);
        }
    }
}
