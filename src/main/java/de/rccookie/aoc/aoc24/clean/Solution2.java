package de.rccookie.aoc.aoc24.clean;

import java.util.Arrays;
import java.util.stream.IntStream;

import de.rccookie.aoc.aoc24.FastSolution;

public class Solution2 extends FastSolution {

    @Override
    public Object task1() {
        return sum(l -> safe(parseInts(l)) ? 1 : 0);
    }

    @Override
    public Object task2() {
        return sum(l -> {
            int[] report = parseInts(l);
            return IntStream.range(-1, report.length).anyMatch(i -> safe(without(report, i))) ? 1 : 0;
        });
    }

    private int[] without(int[] arr, int index) {
        if(index < 0 || index >= arr.length)
            return arr;
        int[] copy = Arrays.copyOf(arr, arr.length - 1);
        System.arraycopy(arr, index + 1, copy, index, arr.length - index - 1);
        return copy;
    }

    private boolean safe(int[] report) {
        if(report[0] == report[1])
            return false;

        int min, max;
        if(report[0] > report[1]) {
            min = -3;
            max = -1;
        }
        else {
            min = 1;
            max = 3;
        }

        for(int i=1; i<report.length; i++)
            if(report[i] - report[i-1] < min || report[i] - report[i-1] > max)
                return false;
        return true;
    }
}
