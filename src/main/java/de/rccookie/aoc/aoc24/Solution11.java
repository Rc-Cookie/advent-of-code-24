package de.rccookie.aoc.aoc24;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.util.Utils;

public class Solution11 extends FastSolution {

    private static final long[][] PRECOMPUTED;
    private static final int PRECOMPUTED_ITERATIONS;
    private static final int PRECOMPUTED_COUNT;

    static {
        //noinspection DataFlowIssue
        try(BufferedReader r = new BufferedReader(new InputStreamReader(Solution11.class.getResourceAsStream("/24/9/precomputed.txt")))) {

            List<long[]> precomputed = new ArrayList<>();
            String l;
            while((l = r.readLine()) != null)
                precomputed.add(parseLongs(l));
            PRECOMPUTED = precomputed.toArray(new long[0][]);
            PRECOMPUTED_ITERATIONS = PRECOMPUTED.length - 1;
            PRECOMPUTED_COUNT = PRECOMPUTED[PRECOMPUTED_ITERATIONS].length;

        } catch(Exception e) {
            throw Utils.rethrow(e);
        }
    }

    @Override
    public Object task1() {
        return simulate(25);
    }

    @Override
    public Object task2() {
        return simulate(75);
    }

    @SuppressWarnings("unchecked")
    private long simulate(int iterations) {
        HashMap<Long,Long>[] counts = new HashMap[iterations];
        Arrays.setAll(counts, i -> new HashMap<>());

        long total = 0;
        for(int x : parseInts(input))
            total += simulate(x, iterations, counts);

        return total;
    }

    private Long simulate(long x, int iterations, Map<Long, Long>[] counts) {
        if(iterations == 0)
            return 1L;

        int iterationsM1 = iterations - 1;

        if(x < PRECOMPUTED_COUNT)
            return PRECOMPUTED[iterationsM1][(int) x];

        Long count;
        if((count = counts[iterationsM1].get(x)) != null)
            return count;

        if(x == 0)
            count = simulate(1, iterationsM1, counts);
        else {

            long t = 1, ht = 1;
            int tw = 1;
            for(; t <= x; tw++) {
                t *= 10;
                if((tw & 1) != 0)
                    ht *= 10;
            }

            if((tw & 1) == 0)
                count = simulate(x * 2024, iterationsM1, counts);
            else {
                Long a = simulate(x % ht, iterationsM1, counts);

                if((count = counts[iterationsM1].get(x)) != null)
                    return count;

                count = a + simulate(x / ht, iterationsM1, counts);
            }
        }

        counts[iterationsM1].put(x, count);
        return count;
    }
}
