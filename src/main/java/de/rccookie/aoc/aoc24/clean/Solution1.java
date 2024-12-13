package de.rccookie.aoc.aoc24.clean;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.rccookie.aoc.aoc24.util.FastSolution;
import de.rccookie.math.intN;

public class Solution1 extends FastSolution {

    @Override
    public Object task1() {
        int[] col1 = column1();
        int[] col2 = column2();

        Arrays.sort(col1);
        Arrays.sort(col2);

        return new intN(col1).sub(new intN(col2)).abs().sum();
    }

    @Override
    public Object task2() {
        Map<Integer, Integer> counts = new HashMap<>();
        for(int i : column2())
            counts.merge(i, 1, Integer::sum);

        long sum = 0;
        for(int i : column1())
            sum += (long) i * counts.getOrDefault(i, 0);
        return sum;
    }

    private int[] column1() {
        return lines.mapToInt(l -> Integer.parseInt(l.substring(0, l.indexOf(" ")))).toArray();
    }

    private int[] column2() {
        return lines.mapToInt(l -> Integer.parseInt(l.substring(l.lastIndexOf(" ") + 1))).toArray();
    }
}
