package de.rccookie.aoc.aoc24.clean;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.rccookie.aoc.aoc24.FastSolution;

public class Solution11 extends FastSolution {

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
        HashMap<Long,Long>[] counts = new HashMap[iterations + 1];
        Arrays.setAll(counts, i -> new HashMap<>());

        long total = 0;
        for(long x : parseLongs(input))
            total += simulate(x, iterations, counts);
        return total;
    }

    private Long simulate(long x, int iterations, Map<Long, Long>[] counts) {
        if(iterations == 0)
            return 1L;

        Long count;
        if((count = counts[iterations].get(x)) != null)
            return count;

        if(x == 0)
            count = simulate(1, iterations - 1, counts);
        else {

            long t = 1, ht = 1;
            int tw = 1;
            for(; t <= x; tw++) {
                t *= 10;
                if((tw & 1) != 0)
                    ht *= 10;
            }

            if((tw & 1) == 0)
                count = simulate(x * 2024, iterations - 1, counts);
            else {
                Long a = simulate(x % ht, iterations - 1, counts);

                if((count = counts[iterations].get(x)) != null)
                    return count;

                count = a + simulate(x / ht, iterations - 1, counts);
            }
        }

        counts[iterations].put(x, count);
        return count;
    }
}
